package opalintegration;

import com.intellij.openapi.vfs.VirtualFile;
import globalData.GlobalData;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import opalintegration.Visitor.Instruction.InstructionVisitorImpl;
import org.jetbrains.annotations.NotNull;
import org.opalj.bi.AccessFlags;
import org.opalj.bi.AccessFlagsContexts;
import org.opalj.br.*;
import org.opalj.br.instructions.Instruction;
import org.opalj.collection.IntIterator;
import org.opalj.collection.RefIterator;
import org.opalj.collection.immutable.RefArray;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.collection.immutable.List;

/** Is responsible for creating and providing the bytecode representation of a class file */
public class JbcProducer {

  private static final Logger LOGGER = Logger.getLogger(JbcProducer.class.getName());

  /**
   * @param project the project the file belongs to
   * @param virtualFile the class file of which we want the bytecode representation
   * @return the file containing the bytecode representation of the passed in class file
   */
  public static VirtualFile prepareJBC(
      @NotNull com.intellij.openapi.project.Project project, @NotNull VirtualFile virtualFile) {
    return OpalUtil.prepare(project, GlobalData.DISASSEMBLED_FILE_ENDING_JBC, virtualFile, null);
  }

  /**
   * create the bytecode representation (of OPAL) for a given class file
   *
   * @param classFile the classfile of which we want the bytecode
   * @return the bytecode representation as a String
   */
  @NotNull
  static String createBytecodeString(@NotNull ClassFile classFile) {
    StringBuilder byteCodeString = new StringBuilder();

    byteCodeString.append(annotationsToJava(classFile.annotations(), "/*", "*/\n"));
    byteCodeString
        .append(AccessFlags.classFlagsToJava(classFile.accessFlags()))
        .append(" ")
        .append(classFile.fqn().replace("/", "."));
    if (classFile.superclassType().isDefined()) {
      byteCodeString.append(" extends ").append(classFile.superclassType().get().toJava());
    }
    if (classFile.interfaceTypes().length() > 0) {
      byteCodeString.append(" implements ");
      for (int j = 0; j < classFile.interfaceTypes().length(); j++) {
        byteCodeString.append(classFile.interfaceTypes().apply(j).toJava()).append(" ");
      }
    }
    byteCodeString
        .append("\n// Source File: ")
        .append(classFile.sourceFile().isDefined() ? classFile.sourceFile().get() : "")
        .append(" -- Version: (")
        .append(classFile.jdkVersion())
        .append(") -- size: \n");

    byteCodeString.append(attributesToJava(classFile.attributes(), "\n///*", "*/\n"));

    if(classFile.bootstrapMethodTable().isDefined()) {
      System.out.println("bootstrap");
      System.out.println(classFile.bootstrapMethodTable().get().toString());
    }

//    InnerClass[] innerClassesCopy = new InnerClass[1024];
//    classFile.innerClasses().get().copyToArray(innerClassesCopy);
//    if(innerClassesCopy[0] != null) {
//      byteCodeString.append(Arrays.stream(innerClassesCopy)
//              .map(innerClass -> innerClass.outerClassType().get().toJava()
//                      + " { " + AccessFlags.classFlagsToJava(innerClass.innerClassAccessFlags())
//                      + " " + innerClass.innerClassType().toJava() + " }")
//              .collect(Collectors.joining("\n")));
//    }


    byteCodeString.append(byteCodeFieldToString(classFile));
    byteCodeString.append(byteCodeMethodsToString(classFile));

    return byteCodeString.toString();
  }

  /**
   * creates a bytecode representation of the implemented fields of the passed classfile
   *
   * @param classFile the classfile of which we want the bytecode
   * @return the fields' bytecode representation as a String
   */
  @NotNull
  private static String byteCodeFieldToString(@NotNull ClassFile classFile) {
    StringBuilder byteCodeString = new StringBuilder();
    RefArray<Field> fields = classFile.fields();

    // don't show fields if there are none
    if (fields.length() == 0) {
      return "";
    }

    byteCodeString.append("Fields {\n");
    for (int j = 0; j < fields.length(); j++) {
      Field field = fields.apply(j);
      // TODO: if access flags empty don't insert empty space before fieldType()
      String accessFlags = AccessFlags.toString(field.accessFlags(), AccessFlagsContexts.FIELD());
      byteCodeString
          .append(accessFlags)
          .append(accessFlags.isEmpty() ? "" : " ")
          .append(field.fieldType().toJava())
          .append(" ")
          .append(field.name())
          .append("\n");
    }
    byteCodeString.append("}\n\n\n");
    return byteCodeString.toString();
  }

  /**
   * creates a bytecode representation of the implemented methods of the passed classfile
   *
   * @param classFile the classfile of which we want the bytecode
   * @return the methods' bytecode representation as a String
   */
  @NotNull
  private static String byteCodeMethodsToString(@NotNull ClassFile classFile) {
    StringBuilder byteCodeString = new StringBuilder();
    InstructionVisitorImpl instructionVisitorImpl = new InstructionVisitorImpl();
    RefIterator<Method> methods = classFile.methods().iterator();
    byteCodeString.append("Methods {\n\n");
    while (methods.hasNext()) {
      Method method = methods.next();
      if (method.methodTypeSignature().isDefined()) {
        // TODO "diamond operator"
        MethodTypeSignature methodTypeSignatureOption = method.methodTypeSignature().get();
        List<ThrowsSignature> throwsSignatureList = methodTypeSignatureOption.throwsSignature();
      }
      byteCodeString.append(annotationsToJava(method.annotations(), "\n/*", "*/\n"));
      byteCodeString
          .append(method.toString().replaceFirst("\\).*", ")"))
          .append(thrownExceptions(method.exceptionTable()))
          .append(" { ");
      if (method.body().isDefined()) {
        Code body = method.body().get();
        Instruction[] instructions = body.instructions();
        byteCodeString
            .append("// [size :")
            .append(body.codeSize())
            .append(" bytes, max stack: ")
            .append(body.maxStack())
            .append(" bytes, max locals: ")
            .append(body.maxLocals())
            .append("] \n");

        String pcLineInstr = String.format("\t%-6s %-6s %s\n", "PC", "Line", "Instruction");
        byteCodeString.append(pcLineInstr);
        for (int k = 0; k < instructions.length; k++) {
          if (instructions[k] != null) {
            try {
              String instruction;
              instruction = instructionVisitorImpl.accept(instructions[k], k);
              // TODO replace \n (and \t...??) and the likes with spaces
              instruction = instruction.replaceAll("\n", " ");
              instruction = instruction.replaceAll("\t", " ");
              // replaces a \ with a \\ -> needed because e.g. LDC("s\") would escape the last " and
              // thus break the syntax
              instruction = instruction.replaceAll("\\\\", "\\\\\\\\");

              String formattedInstrLine =
                  String.format(
                      "\t%-6d %-6s %s\n",
                      k,
                      body.lineNumber(k).isDefined() ? body.lineNumber(k).get() : 0,
                      instruction);
              byteCodeString.append(formattedInstrLine);
            } catch (NoSuchElementException e) {
              LOGGER.log(Level.SEVERE, e.toString(), e);
            }
          }
        }
        byteCodeString.append(attributesToJava(method.attributes(), "\n///*", "*/\n"));

        byteCodeString.append(localVariableTable(body));
        byteCodeString.append(stackMapTable(body));
      }
      byteCodeString.append("}").append("\n\n\n");
    }

    byteCodeString.append("} // Methods");
    return byteCodeString.toString();
  }

  /**
   * E.g. if the exception table contains two exceptions, say IOException and RuntimeException, then
   * the output will be: throws java.io.IOException, java.lang.RuntimeException
   *
   * @param exceptionTable The table which contains the exceptions that the method throws
   * @return a string which contains a throws clause
   */
  @NotNull
  private static String thrownExceptions(@NotNull Option<ExceptionTable> exceptionTable) {
    if (!exceptionTable.isDefined()) {
      return "";
    }
    StringBuilder exceptionTableString = new StringBuilder();
    exceptionTableString.append(" throws ");
    ObjectType[] exceptions = new ObjectType[exceptionTable.get().exceptions().size()];
    exceptionTable.get().exceptions().copyToArray(exceptions);
    exceptionTableString.append(
        Arrays.stream(exceptions).map(ObjectType::toJava).collect(Collectors.joining(", ")));
    return exceptionTableString.toString();
  }

  /**
   * @param body the method body of which we want the local variable table
   * @return the local variable table of a method as a string, if it exists, empty string otherwise
   */
  @NotNull
  private static String localVariableTable(@NotNull Code body) {
    // if there is no local variable table, return an empty string
    if (!body.localVariableTable().isDefined()) {
      return "";
    }

    StringBuilder localVariableTable = new StringBuilder();

    RefArray<LocalVariable> refArrayOption = body.localVariableTable().get();
    localVariableTable
        .append("\n\n\tLocalVariableTable { // [size: ")
        .append(refArrayOption.length())
        .append(" item(s)]\n");
    for (int k = 0; k < refArrayOption.length(); k++) {
      LocalVariable localVariable = refArrayOption.apply(k);
      localVariableTable
          .append("\t\t[")
          .append(localVariable.startPC())
          .append(" > ")
          .append(localVariable.length())
          .append(") => ")
          .append(localVariable.fieldType().toJava())
          .append(" ")
          .append(localVariable.name())
          .append("\n");
    }
    localVariableTable.append("\t}\n");

    return localVariableTable.toString();
  }

  /**
   * @param body the method body of which we want the stack map table
   * @return the stack map table of a method as a string, if it exists, empty string otherwise
   */
  @NotNull
  private static String stackMapTable(@NotNull Code body) {
    if (!body.stackMapTable().isDefined()) {
      return "";
    }

    StringBuilder stackMapTableString = new StringBuilder();

    StackMapTable stackMapTable = body.stackMapTable().get();
    RefArray<StackMapFrame> stackMapFrameRefArray = stackMapTable.stackMapFrames();
    stackMapTableString
        .append("\n\n\tStackMapTable {")
        .append("\n")
        .append("\t\t//PC\tKind\tFrame Type\tOffset Delta\n");
    IntIterator pcIterator = stackMapTable.pcs().iterator();
    for (int i = 0; i < stackMapFrameRefArray.length(); i++) {
      StackMapFrame stackMapFrame = stackMapFrameRefArray.apply(i);
      Class<? extends StackMapFrame> frameClass = stackMapFrame.getClass();
      int pc = pcIterator.next();
      stackMapTableString
          .append("\t\t")
          .append(pc)
          .append(" ")
          .append(frameClass.getSimpleName())
          .append(" ")
          .append(stackMapFrame.frameType())
          .append(" ")
          .append(stackMapFrame.offset(0) - 1)
          .append("\n");
    }
    stackMapTableString.append("\t}\n");

    return stackMapTableString.toString();
  }

  // TODO: doesn't quite work yet (grammar, etc.)
  /** Converts a given list of annotations into a Java-like representation. */
  @NotNull
  private static String annotationsToJava(
      @NotNull RefArray<Annotation> annotations, String before, String after) {
    if (!annotations.isEmpty()) {
      Annotation[] annotationsCopy = new Annotation[annotations.size()];
      annotations.copyToArray(annotationsCopy);

      String javaStyle = Arrays.stream(annotationsCopy)
              .map(Annotation::toJava)
              .collect(Collectors.joining("\n"));
      return before + javaStyle + after;
    } else {
      return "";
    }
  }

  // TODO: doesn't quite work yet (grammar, etc.)
  /** Converts a given list of attributes into a Java-like representation. */
  @NotNull
  private static String attributesToJava(
          @NotNull RefArray<Attribute> attributes, String before, String after) {
    if (!attributes.isEmpty()) {
      Attribute[] attributesCopy = new Attribute[attributes.size()];
      attributes.copyToArray(attributesCopy);
      String attributeString =
          Arrays.stream(attributesCopy).map(Attribute::toString).collect(Collectors.joining(" "));

      return before + attributeString + after;
    } else {
      return "";
    }
  }

//  private static String localVariableTypeTable(Code body) {
//    StringBuilder localVariableTypeTable = new StringBuilder();
//
//    Function1 function1 = new Function1<RefArray<LocalVariableTypeTable>, String>() {
//      @Override
//      public String apply(RefArray<LocalVariableTypeTable> v1) {
//        LocalVariableTypeTable[] temp = new LocalVariableTypeTable[v1.size()];
//        v1.copyToArray(temp);
//        String result =
//                Arrays.stream(temp).map(LocalVariableTypeTable::toString).collect(Collectors.joining("\n"));
//        localVariableTypeTable.append(result);
//        return result;
//      }
//    };
//
//    body.localVariableTypeTable().foreach(function1);
//
//    return localVariableTypeTable.toString();
//  }
}
