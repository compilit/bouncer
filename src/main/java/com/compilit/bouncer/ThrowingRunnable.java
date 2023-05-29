package com.compilit.bouncer;

/**
 * A functional interface that is equal to the Runnable interface, but which throws a checked exception.
 *
 * @param <E> The possible (checked) exception which can be thrown from the Runnable
 */
@FunctionalInterface
public interface ThrowingRunnable<E extends Exception> {

  /**
   * A runnable just like the native Java one, but specifically for handling logic that potentially throws checked
   * exceptions
   *
   * @throws E the potential checked exception
   */
  void run() throws Exception;
}
