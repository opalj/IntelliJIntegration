/*
 *  BSD 2-Clause License - see ./LICENSE for details.
 */

package org.opalj.intellijintegration.opalintegration.Visitor;

public interface DefaultVistor<E, R> {
  R visit(E e);
}
