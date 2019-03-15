package opalintegration.Visitor.Instruction;

import org.opalj.br.instructions.*;

public interface InstructionVisitor {
  String visit(ANEWARRAY anewarray);
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
}
