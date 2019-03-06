package opalintegration;

import org.opalj.br.instructions.*;

public interface InstructionVisitor {
  String visit(ANEWARRAY anewarray);

  String visit(GETSTATIC getstatic);

  String visit(GETFIELD getfield);

  String visit(PUTFIELD putfield);

  String visit(PUTSTATIC putstatic);

  String visit(LDC ldc);
  // String visit(LoadConstantInstruction ldc);
  String visit(LDC_W ldc_w);

  String visit(LoadString_W lsw);

  String visit(LoadString ls);
}
