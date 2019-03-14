package opalintegration;

import org.opalj.br.instructions.*;

public interface Visitor<E, R> {
  R visit(E e);
}
