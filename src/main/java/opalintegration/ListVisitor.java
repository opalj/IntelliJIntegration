package opalintegration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opalj.br.instructions.LoadString_W;

public abstract class ListVisitor<E, R> implements Visitor<E, R> {
  private Set<Class<? extends E>> Elements;

  private static final Logger LOGGER = Logger.getLogger(ListVisitor.class.getName());

  public ListVisitor(Collection<Class<? extends E>> c) {
    Elements = new HashSet<>();
    Elements.addAll(c);
  }

  public R accept(E e) {
    Class<?>[] interfaces = this.getClass().getInterfaces();
    if (e instanceof LoadString_W) {
      LoadString_W o = (LoadString_W) e;
    }
    for (Class<? extends E> in : Elements)
      if (in.isInstance(e)) {
        try {
          Method visit = this.getClass().getMethod("visit", new Class[] {in});
          in.cast(e);
          Object invoke = visit.invoke(this, new Object[] {e});
          return (R) invoke;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e1) {
          LOGGER.log(Level.SEVERE, e1.toString(), e1);
        }
      }
    return visit(e);
  }
}
