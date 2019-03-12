package opalintegration;

import com.intellij.openapi.vfs.VirtualFile;
import globalData.GlobalData;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.opalj.bi.AccessFlags;
import org.opalj.bi.AccessFlagsContexts;
import org.opalj.br.*;
import org.opalj.br.instructions.Instruction;
import org.opalj.collection.IntIterator;
import org.opalj.collection.RefIterator;
import org.opalj.collection.immutable.RefArray;

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
        .append(") -- Size: \n");
    // TODO Constant Pool Maybe working with DA.
    byteCodeString.append(byteCodeFieldToString(classFile));
    RefArray<Annotation> annotations = classFile.annotations();
    for (int i = 0; i < annotations.length(); i++) {
      Annotation annotation = annotations.apply(i);
      byteCodeString.append("//").append(annotation.toJava()).append("\n");
    }
    byteCodeString.append(byteCodeMethodsToString(classFile));

    return byteCodeString.toString();
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
    byteCodeString.append("Fields\n");
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
    byteCodeString.append("\n\n\n");
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
    StringVisitor stringVisitor = new StringVisitor();
    RefIterator<Method> methods = classFile.methodsWithBody();
    byteCodeString.append("Methods\n\n");
    while (methods.hasNext()) {
      Method method = methods.next();
      if (method.body().isDefined()) {
        Code body = method.body().get();
        Instruction[] instructions = body.instructions();
        byteCodeString
            .append(method.toString().replaceFirst("\\).*", ")"))
            .append(" { ")
            .append("// [size :")
            .append(body.codeSize())
            .append(" bytes, max Stack: ")
            .append(body.maxStack())
            .append("] \n");

        String pcLineInstr = String.format("\t%-6s %-6s %s\n", "PC", "Line", "Instruction");
        byteCodeString.append(pcLineInstr);
        for (int k = 0; k < instructions.length; k++) {
          if (instructions[k] != null) {
            try {
              String instruction;
              instruction = stringVisitor.accept(instructions[k]);
              // TODO replace \n (and \t...??) and the likes with spaces
              instruction = instruction.replaceAll("\n", " ");
              instruction = instruction.replaceAll("\t", " ");

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

        // append tables, if existent
        byteCodeString.append(localVariableTable(body));
        byteCodeString.append(stackMapTable(body));
      }
      byteCodeString.append("}").append("\n\n\n");
    }
    return byteCodeString.toString();
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
        .append("\n\nLocalVariableTable // [size: ")
        .append(refArrayOption.length())
        .append(" item(s)]\n");
    for (int k = 0; k < refArrayOption.length(); k++) {
      LocalVariable localVariable = refArrayOption.apply(k);
      localVariableTable
          .append("[")
          .append(localVariable.startPC())
          .append(" > ")
          .append(localVariable.length())
          .append(") => ")
          .append(localVariable.fieldType().toJava())
          .append(" ")
          .append(localVariable.name())
          .append("\n");
    }

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
        .append("StackMapTable")
        .append("\n")
        .append("//PC\tName\tframeType\toffsetDelta\n");
    IntIterator pcIterator = stackMapTable.pcs().iterator();
    for (int i = 0; i < stackMapFrameRefArray.length(); i++) {
      StackMapFrame stackMapFrame = stackMapFrameRefArray.apply(i);
      Class<? extends StackMapFrame> frameClass = stackMapFrame.getClass();
      int pc = pcIterator.next();
      stackMapTableString
          .append(pc + " ")
          .append(frameClass.getName() + " ")
          .append(stackMapFrame.frameType() + " ")
          .append(stackMapFrame.offset(0) - 1 + "\n");
    }

    return stackMapTableString.toString();
  }
}
