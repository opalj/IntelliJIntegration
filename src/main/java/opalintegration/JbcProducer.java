/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package opalintegration;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import opalintegration.Visitor.Instruction.InstructionVisitorImpl;
import org.jetbrains.annotations.NotNull;
import org.opalj.br.*;
import org.opalj.br.instructions.Instruction;

/** Is responsible for creating and providing the bytecode representation of a method's body */
class JbcProducer extends DecompiledTextProducer {

  private static final Logger LOGGER = Logger.getLogger(JbcProducer.class.getName());

  @Override
  String methodBody(Method method) {
    StringBuilder methodBodyText = new StringBuilder();


    if (method.body().isDefined()) {
      Code methodBody = method.body().get();

      // information about the method
      String methodInformation =
          String.format(
              "\n\t// [size: %d bytes, max stack: %d bytes, max locals: %d]\n",
              methodBody.codeSize(), methodBody.maxStack(), methodBody.maxLocals());
      methodBodyText.append(methodInformation);
      methodBodyText.append(Tables.methodTypeSignature(method));

      // the instructions
      methodBodyText.append(instructions(methodBody));

      // tables, if existent
      if (Tables.hasTables(method)) {
        methodBodyText.append("\n  Tables {");
        methodBodyText.append(Tables.exceptionTable(method.body()));
        methodBodyText.append(Tables.localVariableTable(methodBody));
        methodBodyText.append(Tables.localVariableTypeTable(methodBody));
        methodBodyText.append(Tables.stackMapTable(methodBody));
        methodBodyText.append("  } // Tables\n");
      }
    }

    return methodBodyText.toString();
  }


  /**
   * Creates the string for the instructions in the method body, nicely formatted
   *
   * @param methodBody The method body which contains the instructions
   * @return String of all instructions
   */
  @NotNull
  private String instructions(@NotNull Code methodBody) {
    StringBuilder instructionsText = new StringBuilder();
    InstructionVisitorImpl instructionVisitorImpl = new InstructionVisitorImpl();

    String pcLineInstr = String.format("\t%-6s %-6s %s\n", "PC", "Line", "Instruction");
    instructionsText.append(pcLineInstr);

    Instruction[] instructions = methodBody.instructions();
    // pc is the program counter
    for (int pc = 0; pc < instructions.length; pc++) {
      if (instructions[pc] != null) {
        try {
          String instruction;
          instruction = instructionVisitorImpl.accept(instructions[pc], pc);

          // fix escaping backslashes; this call can be removed when OPAL has fixed this bug
          instruction = opalStringBugFixer(instruction);

          String formattedInstrLine =
                  String.format(
                          "\t%-6d %-6s %s\n",
                          pc,
                          methodBody.lineNumber(pc).isDefined() ? methodBody.lineNumber(pc).get() : 0,
                          instruction);
          instructionsText.append(formattedInstrLine);
        } catch (NoSuchElementException e) {
          LOGGER.log(Level.SEVERE, e.toString(), e);
        }
      }
    }

    return instructionsText.toString();
  }

}
