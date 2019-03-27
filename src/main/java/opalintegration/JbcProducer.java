package opalintegration;

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
import org.opalj.collection.immutable.RefArray;

/** Is responsible for creating and providing the bytecode representation of a class file */
class JbcProducer extends DecompiledTextProducer {

  private static final Logger LOGGER = Logger.getLogger(JbcProducer.class.getName());

  @Override
  String fields(ClassFile classFile) {
    return byteCodeFieldToString(classFile);
  }

  @Override
  String beginMethodArea() {
    return "Methods {\n\n";
  }

  @Override
  String endMethodArea() {
    return "} // Methods";
  }

  @Override
  String methodBody(Method method) {
    StringBuilder methodBody = new StringBuilder();
    InstructionVisitorImpl instructionVisitorImpl = new InstructionVisitorImpl();

    if (method.body().isDefined()) {
      Code body = method.body().get();
      Instruction[] instructions = body.instructions();
      methodBody
          .append("// [size :")
          .append(body.codeSize())
          .append(" bytes, max stack: ")
          .append(body.maxStack())
          .append(" bytes, max locals: ")
          .append(body.maxLocals())
          .append("] \n");

      String pcLineInstr = String.format("\t%-6s %-6s %s\n", "PC", "Line", "Instruction");
      methodBody.append(pcLineInstr);
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
                    k, body.lineNumber(k).isDefined() ? body.lineNumber(k).get() : 0, instruction);
            methodBody.append(formattedInstrLine);
          } catch (NoSuchElementException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
          }
        }
      } // for(instructions)

      methodBody.append(attributesToJava(method.attributes(), "\n///*", "*/\n"));
      methodBody.append(localVariableTable(body));
      methodBody.append(stackMapTable(body));
    } // if(body.defined)
    methodBody.append("}").append("\n\n\n");

    return methodBody.toString();
  }

  /**
   * creates a bytecode representation of the implemented fields of the passed classfile
   *
   * @param classFile the classfile of which we want the bytecode
   * @return the fields' bytecode representation as a String
   */
  @NotNull
  private String byteCodeFieldToString(@NotNull ClassFile classFile) {
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
   * @param body the method body of which we want the local variable table
   * @return the local variable table of a method as a string, if it exists, empty string otherwise
   */
  @NotNull
  private String localVariableTable(@NotNull Code body) {
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
  private String stackMapTable(@NotNull Code body) {
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

  //  private static String localVariableTypeTable(Code body) {
  //    StringBuilder localVariableTypeTable = new StringBuilder();
  //
  //    Function1 function1 = new Function1<RefArray<LocalVariableTypeTable>, String>() {
  //      @Override
  //      public String apply(RefArray<LocalVariableTypeTable> v1) {
  //        LocalVariableTypeTable[] temp = new LocalVariableTypeTable[v1.size()];
  //        v1.copyToArray(temp);
  //        String result =
  //
  // Arrays.stream(temp).map(LocalVariableTypeTable::toString).collect(Collectors.joining("\n"));
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
