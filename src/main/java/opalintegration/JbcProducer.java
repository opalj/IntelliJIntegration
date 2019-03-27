package opalintegration;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import opalintegration.Visitor.Instruction.InstructionVisitorImpl;
import org.opalj.br.*;
import org.opalj.br.instructions.Instruction;

/** Is responsible for creating and providing the bytecode representation of a class file */
class JbcProducer extends DecompiledTextProducer {

  private static final Logger LOGGER = Logger.getLogger(JbcProducer.class.getName());

  @Override
  String methodBody(Method method) {
    StringBuilder methodBodyText = new StringBuilder();
    InstructionVisitorImpl instructionVisitorImpl = new InstructionVisitorImpl();

    if (method.body().isDefined()) {
      Code methodBody = method.body().get();
      Instruction[] instructions = methodBody.instructions();

      String methodInformation =
          String.format(
              "// [size: %d bytes, max stack: %d bytes, max locals: %d]\n",
              methodBody.codeSize(), methodBody.maxStack(), methodBody.maxLocals());
      methodBodyText.append(methodInformation);
      methodBodyText.append(Tables.methodTypeSignature(method));

      String pcLineInstr = String.format("\t%-6s %-6s %s\n", "PC", "Line", "Instruction");
      methodBodyText.append(pcLineInstr);
      for (int k = 0; k < instructions.length; k++) {
        if (instructions[k] != null) {
          try {
            String instruction;
            instruction = instructionVisitorImpl.accept(instructions[k], k);

            instruction = opalStringBugFixer(instruction);

            String formattedInstrLine =
                String.format(
                    "\t%-6d %-6s %s\n",
                    k,
                    methodBody.lineNumber(k).isDefined() ? methodBody.lineNumber(k).get() : 0,
                    instruction);
            methodBodyText.append(formattedInstrLine);
          } catch (NoSuchElementException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
          }
        }
      } // for(instructions)

      if (Tables.hasTables(method)) {
        methodBodyText.append("\n  Tables {");
        methodBodyText.append(Tables.exceptionTable(method.body()));
        methodBodyText.append(Tables.localVariableTable(methodBody));
        methodBodyText.append(Tables.localVariableTypeTable(methodBody));
        methodBodyText.append(Tables.stackMapTable(methodBody));
        methodBodyText.append("  } // Tables\n");
      }
    } // if(body.defined)

    return methodBodyText.toString();
  }
}
