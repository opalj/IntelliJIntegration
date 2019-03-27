package opalintegration.Visitor.StackMap;

import org.opalj.br.*;

interface StackMapVisitor {
  String visit(SameLocals1StackItemFrame frame);

  String visit(SameFrame frame);

  String visit(SameLocals1StackItemFrameExtended frame);

  String visit(ChopFrame frame);

  String visit(SameFrameExtended frame);

  String visit(AppendFrame frame);

  String visit(FullFrame frame);
}
