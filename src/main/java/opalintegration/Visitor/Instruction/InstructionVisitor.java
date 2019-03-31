package opalintegration.Visitor.Instruction;

import org.opalj.br.instructions.*;

/**
 * An interface for the visitor pattern which lists all instructions that should be visited.
 *
 * <p>(Most instructions don't need such treatment as their output is satisfactory as is.)
 *
 * <p>(The methods seem to be grayed out since they are invoked like this: getMethod("visit",...),
 *
 * @see opalintegration.Visitor.ElementAcceptor#accept(Object, int...) )
 */
@SuppressWarnings("unused")
// unused because there is no accepting methods for given element
// unused warning happens because method call looks like this (passed as string):
// this.getClass().getMethod("visit", in);
interface InstructionVisitor {
  String visit(NEW mnemonicNew);

  String visit(ANEWARRAY anewarray);

  String visit(MULTIANEWARRAY anewarray);

  String visit(GETSTATIC getstatic);

  String visit(GETFIELD getfield);

  String visit(PUTFIELD putfield);

  String visit(PUTSTATIC putstatic);

  String visit(LoadString_W lsw);

  String visit(LoadString ls);

  String visit(LoadInt l);

  String visit(LoadInt_W l);

  String visit(LoadDouble l);

  String visit(LoadLong l);

  String visit(LoadFloat l);

  String visit(LoadFloat_W l);

  String visit(LoadClass l);

  String visit(LoadClass_W l);

  String visit(LoadMethodHandle l);

  String visit(LoadMethodHandle_W l);

  String visit(LoadMethodType l);

  String visit(LoadMethodType_W l);

  String visit(SimpleBranchInstruction sbi);

  String visit(LOOKUPSWITCH s);
}
