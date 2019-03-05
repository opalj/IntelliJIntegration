package opalintegration;

import org.opalj.br.instructions.*;

public interface InstructionVisitor {
  String visit(ANEWARRAY anewarray);

  String visit(GETSTATIC getstatic);

  String visit(GETFIELD getfield);

  String visit(PUTFIELD putfield);

  String visit(PUTSTATIC putstatic);

  String visit(LDC ldc);
}
