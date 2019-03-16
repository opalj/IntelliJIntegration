package opalintegration.Visitor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A generic visitor which accepts a type of 'E' (e.g. Instruction), and for each one computes a
 * value of type 'R' (e.g. String).
 *
 * <p>It uses a set to store the types of elements it can visit (i.e. traverse through)
 *
 * @param <E> type of element that can be visited
 * @param <R> type of value computed on a visit
 */
public abstract class ElementAcceptor<E, R> implements DefaultVistor<E, R> {
  private Set<Class<? extends E>> Elements;
  protected int[] pc;
  private static final Logger LOGGER = Logger.getLogger(ElementAcceptor.class.getName());

  public ElementAcceptor() {
    Elements = new HashSet<>();
    for (Class<?> inter : this.getClass().getInterfaces()) {
      for (Method method : inter.getMethods()) {
        for (Class<?> parameterType : method.getParameterTypes()) {
          Elements.add((Class<? extends E>) parameterType);
        }
      }
    }
  }

  public ElementAcceptor(Collection<Class<? extends E>> c) {
    Elements = new HashSet<>();
    Elements.addAll(c);
  }

  /**
   * Visits each element type in the set, and if e's type 'E' is contained within it, a value of
   * type 'R' is computed
   *
   * @param e the element to visit, if contained within the set
   * @return a value of type 'R' that is computed for this specific element e
   */
  public R accept(E e, int... pc) {
    this.pc = pc;
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
