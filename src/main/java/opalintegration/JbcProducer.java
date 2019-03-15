package opalintegration;

import com.intellij.openapi.vfs.VirtualFile;
import globalData.GlobalData;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import opalintegration.Visitor.Instruction.InstructionVisitorImpl;
import org.jetbrains.annotations.NotNull;
import org.opalj.bi.AccessFlags;
import org.opalj.bi.AccessFlagsContexts;
import org.opalj.br.*;
import org.opalj.br.instructions.Instruction;
import org.opalj.collection.IntIterator;
import org.opalj.collection.RefIterator;
import org.opalj.collection.immutable.RefArray;
import org.opalj.da.ClassFileReader;
import scala.Function1;
import scala.collection.immutable.List;

/** Is responsible for creating and providing the bytecode representation of a class file */
public class JbcProducer {

  private static VirtualFile virtualFile;
  private static final Logger LOGGER = Logger.getLogger(JbcProducer.class.getName());

  /**
   * @param project the project the file belongs to
   * @param virtualFile the class file of which we want the bytecode representation
   * @return the file containing the bytecode representation of the passed in class file
   */
  public static VirtualFile prepareJBC(
      @NotNull com.intellij.openapi.project.Project project, @NotNull VirtualFile virtualFile) {
    JbcProducer.virtualFile = virtualFile;
    return OpalUtil.prepare(project, GlobalData.DISASSEMBLED_FILE_ENDING_JBC, virtualFile, null);
  }

  /**
   * create the bytecode representation (of OPAL) for a given class file
   *
   * @param classFile the classfile of which we want the bytecode
   * @return the bytecode representation as a String
   */
  static String createBytecodeString(ClassFile classFile) {
    StringBuilder byteCodeString = new StringBuilder();
    byteCodeString
        .append(AccessFlags.classFlagsToJava(classFile.accessFlags()))
        .append(" ")
        .append(classFile.fqn().replace("/", "."));
    //    ByteCodeString.append(AccessFlags.toString(classFile.accessFlags(),
    // AccessFlagsContexts.CLASS())).append( " ").append(classFile.fqn().replace("/", "."));
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
    // TODO Constant Pool Maybe working with DA.
    RefArray<Annotation> annotations = classFile.annotations();
    byteCodeString.append(attributesToJava(classFile.attributes(), "\n///*", "*/\n"));
    byteCodeString.append(annotationsToJava(annotations, "\n///*", "*/\n"));
    //    byteCodeString.append(byteCodeConstantPoolToString(virtualFile.getPath())); // TODO
    byteCodeString.append(byteCodeFieldToString(classFile));
    byteCodeString.append(byteCodeMethodsToString(classFile));

    return byteCodeString.toString();
  }

  // TODO
  private static String byteCodeConstantPoolToString(String classPath) {
    StringBuilder constantPool = new StringBuilder();
    constantPool.append("Constant Pool {\n");

    Path path = Paths.get(classPath);
    File file = path.toFile();

    try (FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis)) {
      // get the class file and construct the HTML string
      org.opalj.da.ClassFile daClassFile =
          (org.opalj.da.ClassFile) ClassFileReader.ClassFile(dis).head();

      org.opalj.da.Constant_Pool_Entry firstEntry = daClassFile.constant_pool()[0];
      if (firstEntry != null) {
        try {
          constantPool.append(firstEntry.toString()).append("\n");
          constantPool.append(firstEntry.toString(daClassFile.constant_pool())).append("\n");
          constantPool.append(firstEntry.asString()).append("\n");
          constantPool.append(firstEntry.Constant_Type_Value()).append("\n");
          constantPool.append(firstEntry.Constant_Type_Value().id()).append("\n");
          constantPool.append(firstEntry.asConstantUTF8()).append("\n");
          constantPool.append(firstEntry.asConstantPackage()).append("\n");
        } catch (Exception e) {

        }
      }
      for (org.opalj.da.Constant_Pool_Entry constantPoolEntry : daClassFile.constant_pool()) {
        if (constantPoolEntry == null) {
          continue;
        }

        //        constantPool.append(constantPoolEntry.toString()).append("\n");
        //
        try {
          //          constantPool.append(constantPoolEntry.asString()).append("\n");
          //
          // constantPool.append(constantPoolEntry.toString(daClassFile.constant_pool())).append("\n");
          constantPool.append(constantPoolEntry.toString()).append("\n");
          constantPool.append(constantPoolEntry.toString(daClassFile.constant_pool())).append("\n");
          constantPool.append(constantPoolEntry.asString()).append("\n");
          constantPool.append(constantPoolEntry.Constant_Type_Value()).append("\n");
          constantPool.append(constantPoolEntry.Constant_Type_Value().id()).append("\n");
          constantPool.append(constantPoolEntry.asConstantUTF8()).append("\n");
        } catch (UnsupportedOperationException uoe) {
          LOGGER.log(Level.FINER, uoe.toString(), uoe);
        }
      }
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.toString(), e);
    }
    constantPool.append("}\n\n\n");

    return constantPool.toString();
  }

  /**
   * creates a bytecode representation of the implemented fields of the passed classfile
   *
   * @param classFile the classfile of which we want the bytecode
   * @return the fields' bytecode representation as a String
   */
  private static String byteCodeFieldToString(ClassFile classFile) {
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
  public static String byteCodeMethodsToString(ClassFile classFile) {
    StringBuilder byteCodeString = new StringBuilder();
    InstructionVisitorImpl instructionVisitorImpl = new InstructionVisitorImpl();
    RefIterator<Method> methods = classFile.methods().iterator();
    byteCodeString.append("Methods {\n\n");
    while (methods.hasNext()) {
      Method method = methods.next();
      if (method.methodTypeSignature().isDefined()) {
        MethodTypeSignature methodTypeSignatureOption = method.methodTypeSignature().get();
        List<ThrowsSignature> throwsSignatureList = methodTypeSignatureOption.throwsSignature();
      }
      byteCodeString.append(method.toString().replaceFirst("\\).*", ")")).append(" { ");
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
              // thus breaking the syntax
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
        // method.exexceptionTable()
        //        if (method.exceptionTable().isDefined()) {
        //          ExceptionTable exceptionTable = method.exceptionTable().get();
        //          byteCodeString.append("ExceptionTable\n");
        //          RefIterator<ObjectType> exceptions = exceptionTable.exceptions().iterator();
        //          while (exceptions.hasNext()) {
        //            ObjectType exception = exceptions.next();
        //
        //            byteCodeString
        //                .append(exception.id())
        //                .append("\t")
        //                .append(exception.toJava())
        //                .append("\n");
        //          }
        //        }
        // end method.exceptionTable
        // append tables, if existent
        // weird ass bug
        byteCodeString.append(exceptionTable(method));
        RefArray<Annotation> annotations = method.annotations();
        byteCodeString.append(attributesToJava(method.attributes(), "\n///*", "*/\n"));
        byteCodeString.append(annotationsToJava(annotations, "\n///*", "*/\n"));

        byteCodeString.append(localVariableTable(body));
        byteCodeString.append(stackMapTable(body));
      }
      byteCodeString.append("}").append("\n\n\n");
    }

    byteCodeString.append("} // Methods");
    return byteCodeString.toString();
  }

  private static String exceptionTable(Method method) {

    if (!method.exceptionTable().isDefined()) {
      return "";
    }

    StringBuilder exceptionTableString = new StringBuilder();
    ExceptionTable exceptionTable = method.exceptionTable().get();
    exceptionTableString.append("\n\n\tExceptionTable {\n");
    RefIterator<ObjectType> exceptions = exceptionTable.exceptions().iterator();
    while (exceptions.hasNext()) {
      ObjectType exception = exceptions.next();

      exceptionTableString
          .append("\t\t")
          .append(exception.id())
          .append("\t")
          .append(exception.toJava())
          .append("\n");
    }
    exceptionTableString.append("\t}\n");

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
        .append("//PC\tName\tframeType\toffsetDelta\n");
    IntIterator pcIterator = stackMapTable.pcs().iterator();
    for (int i = 0; i < stackMapFrameRefArray.length(); i++) {
      StackMapFrame stackMapFrame = stackMapFrameRefArray.apply(i);
      Class<? extends StackMapFrame> frameClass = stackMapFrame.getClass();
      int pc = pcIterator.next();
      stackMapTableString
          .append("\t\t")
          .append(pc)
          .append(" ")
          .append(frameClass.getName())
          .append(" ")
          .append(stackMapFrame.frameType())
          .append(" ")
          .append(stackMapFrame.offset(0) - 1)
          .append("\n");
    }
    stackMapTableString.append("\t}\n");

    return stackMapTableString.toString();
  }

  /** Converts a given list of annotations into a Java-like representation. */
  private static String annotationsToJava(
      RefArray<Annotation> annotations, String before, String after) {
    Function1 function1 = (Function1<Annotation, String>) v1 -> v1.toJava();
    if (!annotations.isEmpty()) {
      return before + annotations._UNSAFE_mapped(function1).mkString("\n") + after;
    } else {
      return "";
    }
  }

  /** Converts a given list of annotations into a Java-like representation. */
  // todo UNDER WORK
  private static String attributesToJava(
      RefArray<Attribute> annotations, String before, String after) {
    Function1 function1 = (Function1<Attribute, String>) v1 -> v1.toString();
    if (!annotations.isEmpty()) {
      return before + annotations._UNSAFE_mapped(function1).mkString(" ") + after;
    } else {
      return "";
    }
  }
}
