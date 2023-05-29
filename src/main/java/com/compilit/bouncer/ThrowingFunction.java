package com.compilit.bouncer;

/**
 * A functional interface that is equal to the Function interface, but which throws a checked exception.
 *
 * @param <I> The type that goes into the Function
 * @param <O> The type that comes out of the Function
 * @param <E> The possible (checked) exception which can be thrown from the Function
 */
@FunctionalInterface
public interface ThrowingFunction<I, O, E extends Exception> {

  /**
   * A function just like the native Java one, but specifically for handling logic that potentially throws checked
   * exceptions
   *
   * @param input the input of the logic
   * @return the result of the function
   * @throws E the potential checked exception
   */
  O apply(I input) throws Exception;
}
