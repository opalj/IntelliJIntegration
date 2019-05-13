/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package opalintegration.Visitor.StackMap;

import org.opalj.br.*;

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
interface StackMapVisitor {
  String visit(SameLocals1StackItemFrame frame);

  String visit(SameFrame frame);

  String visit(SameLocals1StackItemFrameExtended frame);

  String visit(ChopFrame frame);

  String visit(SameFrameExtended frame);

  String visit(AppendFrame frame);

  String visit(FullFrame frame);
}
