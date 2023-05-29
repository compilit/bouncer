package com.compilit.bouncer;

/**
 * A functional interface that is equal to the Supplier interface, but which throws a checked exception.
 *
 * @param <T> The type you wish to return with the Supplier
 * @param <E> The possible (checked) exception which can be thrown from the Supplier
 */
@FunctionalInterface
public interface ThrowingSupplier<T, E extends Exception> {

  /**
   * A supplier just like the native Java one, but specifically for handling logic that potentially throws checked
   * exceptions
   *
   * @return the result of the supplier
   * @throws E the potential checked exception
   */
  T get() throws Exception;
}