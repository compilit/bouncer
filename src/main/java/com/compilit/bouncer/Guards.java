package com.compilit.bouncer;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * These functions all transform possible throwing operations into a definite result without throwing any exception.
 */
public final class Guards {

  private Guards() {}

  /**
   * Try to resolve the supplier but return null on any exception
   * @param supplier a potentially throwing supplier
   * @param <T>      the return type
   * @return either the result of the supplier or null
   */
  public static <T> T orNull(Supplier<T> supplier) {
    return orDefault(supplier, null);
  }

  /**
   * Try to resolve the supplier but return null on any checked exception
   * @param throwingSupplier a potentially checked exception throwing supplier
   * @param <T>              the return type
   * @param <E>              the checked exception
   * @return either the wanted value as a String or the given default value
   */
  public static <T, E extends Exception> T orNullThrowing(ThrowingSupplier<T, E> throwingSupplier) {
    return orDefaultThrowing(throwingSupplier, null);
  }

  /**
   * Try to resolve the function but return null on any exception
   * @param function a potentially throwing supplier
   * @param <I>      the input type
   * @param <O>      the return type
   * @param value    the input value
   * @return either the result of the supplier value or null
   */
  public static <I, O> O orNull(Function<I, O> function, I value) {
    return orDefault(function, value, null);
  }

  /**
   * Resolve the value of a throwing function. This can be either a checked- or runtime exception. If the function throws an exception, it will return null.
   * @param function a potentially throwing supplier
   * @param <I>      the input type
   * @param <O>      the return type
   * @param <E>      the exception type
   * @param value the input of the function
   * @return either the result of the supplier value or null
   */
  public static <I, O, E extends Exception> O orNullThrowing(
    ThrowingFunction<I, O, E> function,
    I value
  ) {
    return orDefaultThrowing(function, value, null);
  }


  /**
   * Try to resolve the result of the provided supplier and in case it throws any exception, it will return the provided default value.
   * @param supplier     a potentially throwing supplier
   * @param defaultValue the default value to return in case of an exception
   * @param <T>          the return type
   * @return either the result of the supplier or the given default value
   */
  public static <T> T orDefault(
    Supplier<T> supplier,
    T defaultValue
  ) {
    try {
      return supplier.get();
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Try to resolve the result of the provided supplier and in case it throws any (checked) exception, it will return the provided default value.
   * @param supplier     a potentially throwing supplier
   * @param defaultValue the default value to return in case of an exception
   * @param <T>          the return type
   * @param <E>          the exception type
   * @return either the result of the supplier or the given default value
   */
  public static <T, E extends Exception> T orDefaultThrowing(
    ThrowingSupplier<T, E> supplier,
    T defaultValue
  ) {
    try {
      return supplier.get();
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Try to resolve the result of the provided function and in case it throws any (checked) exception, it will return the provided default value.
   * @param function     a potentially throwing function
   * @param <I>          the input type
   * @param <O>          the return type
   * @param <E>          the exception type
   * @param value the input value for the function
   * @param defaultValue the default value you wish to return in case of an exception.
   * @return either result of the function or the given default value
   */
  public static <I, O, E extends Exception> O orDefaultThrowing(
    ThrowingFunction<I, O, E> function,
    I value,
    O defaultValue
  ) {
    try {
      return function.apply(value);
    } catch (Exception e) {
      return defaultValue;
    }
  }


  /**
   * Apply the function and in case of an exception, return the default value
   *
   * @param function a potentially throwing function
   * @param <I>      the input type
   * @param <O>      the return type
   * @param value the input for the function
   * @param defaultValue the default value you wish to return in case of an exception
   * @return either result of the function or the given default value
   */
  public static <I, O> O orDefault(
    Function<I, O> function,
    I value,
    O defaultValue
  ) {
    try {
      return function.apply(value);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Run the runnable without throwing an exception Wraps around checked exception-throwing functions
   *
   * @param runnable         the potentially throwing runnable
   * @param exceptionHandler the handler to handle the potential exception
   */
  public static void orHandleException(Runnable runnable, Consumer<Exception> exceptionHandler) {

    try {
      runnable.run();
    } catch (Exception e) {
      exceptionHandler.accept(e);
    }

  }

  /**
   * Run the runnable without throwing an exception Wraps around checked exception-throwing functions
   *
   * @param throwingRunnable the potentially throwing runnable
   * @param exceptionHandler the handler to handle the potential exception
   * @param <E>              the specific checked exception type
   */
  public static <E extends Exception> void orHandleException(
    ThrowingRunnable<E> throwingRunnable,
    Consumer<Exception> exceptionHandler
  ) {
    try {
      throwingRunnable.run();
    } catch (Exception e) {
      exceptionHandler.accept(e);
    }
  }

}
