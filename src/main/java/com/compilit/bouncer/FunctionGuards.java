package com.compilit.bouncer;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * These functions all transform possible throwing operations into 'safe' ones which will not throw any exception.
 */
public final class FunctionGuards {

  private FunctionGuards() {}

  /**
   * Try to get the result of the supplier but return null on any exception.
   *
   * @param supplier a potentially throwing supplier
   * @param <T>      the return type
   * @return either the result of the supplier or null
   */
  public static <T> Supplier<T> orNull(Supplier<T> supplier) {
    return orDefault(supplier, null);
  }

  /**
   * Try to get the result of the supplier but return null on any (checked) exception.
   *
   * @param throwingSupplier a potentially checked exception throwing supplier
   * @param <T>              the return type
   * @param <E>              the checked exception type
   * @return either the wanted value as a String or the given default value
   */
  public static <T, E extends Exception> Supplier<T> orNullThrowing(ThrowingSupplier<T, E> throwingSupplier) {
    return orDefaultThrowing(throwingSupplier, null);
  }

  /**
   * Try to get the result of the function but return null on any exception.
   *
   * @param function a potentially throwing supplier
   * @param <I>      the input type
   * @param <O>      the return type
   * @return either the result of the supplier value or null
   */
  public static <I, O> Function<I, O> orNull(Function<I, O> function) {
    return orDefault(function, null);
  }

  /**
   * Try to get the result of the function but return null on any exception.
   *
   * @param function a potentially throwing supplier
   * @param <I>      the input type
   * @param <O>      the return type
   * @param <E>      the checked exception type
   * @return either the result of the supplier value or null
   */
  public static <I, O, E extends Exception> Function<I, O> orNullThrowing(ThrowingFunction<I, O, E> function) {
    return orDefaultThrowing(function, null);
  }


  /**
   * Try to get the result of the supplier but return the default value on any exception.
   *
   * @param supplier     a potentially throwing supplier
   * @param defaultValue the default value to return in case of an exception
   * @param <T>          the return type
   * @return either the result of the supplier or the given default value
   */
  public static <T> Supplier<T> orDefault(
    Supplier<T> supplier,
    T defaultValue
  ) {
    return () -> Guards.orDefault(supplier, defaultValue);
  }

  /**
   * Try to get the result of the supplier but return the default value on any exception.
   *
   * @param supplier     a potentially throwing supplier
   * @param defaultValue the default value to return in case of an exception
   * @param <T>          the return type
   * @param <E>          the exception type
   * @return either the result of the supplier or the given default value
   */
  public static <T, E extends Exception> Supplier<T> orDefaultThrowing(
    ThrowingSupplier<T, E> supplier,
    T defaultValue
  ) {
    return () -> Guards.orDefaultThrowing(supplier, defaultValue);
  }

  /**
   * Try to get the result of the function but return the default value on any exception.
   *
   * @param function a potentially throwing function
   * @param <I>      the input type
   * @param <O>      the return type
   * @param <E>          the exception type
   * @param defaultValue the default value to return in case of an exception
   * @return either result of the function or the given default value
   */
  public static <I, O, E extends Exception> Function<I, O> orDefaultThrowing(
    ThrowingFunction<I, O, E> function,
    O defaultValue
  ) {
    return input -> Guards.orDefaultThrowing(function, input, defaultValue);
  }


  /**
   * Try to get the result of the function but return the default value on any exception.
   *
   * @param function a potentially throwing function
   * @param <I>      the input type
   * @param <O>      the return type
   * @param defaultValue the default value to return in case of an exception
   * @return either result of the function or the given default value
   */
  public static <I, O> Function<I, O> orDefault(
    Function<I, O> function,
    O defaultValue
  ) {
    return input -> Guards.orDefault(function, input, defaultValue);
  }

  /**
   * Take the potentially throwing supplier and in case an exception takes place, transform the exception into a runtime
   * exception.
   *
   * @param throwingSupplier a potentially checked exception throwing supplier
   * @param <T>              the return type
   * @param <E>              the checked exception
   * @return a non-checked-exception-throwing supplier. Runtime exception can still take place
   */
  public static <T, E extends Exception> Supplier<T> orRuntimeException(ThrowingSupplier<T, E> throwingSupplier) {
    return () -> {
      try {
        return throwingSupplier.get();
      } catch (Exception checkedException) {
        throw new RuntimeException(checkedException);
      }
    };
  }

  /**
   * Returns a 'safe' runnable which will always run without throwing an exception. Wraps around checked
   * exception-throwing functions
   *
   * @param runnable         the potentially throwing runnable
   * @param exceptionHandler the handler to handle the potential exception
   * @return a runnable
   */
  public static Runnable orHandleException(Runnable runnable, Consumer<Exception> exceptionHandler) {
    return () -> Guards.orHandleException(runnable, exceptionHandler);
  }

  /**
   * Returns a 'safe' runnable which will always run without throwing an exception. Wraps around checked
   * exception-throwing functions
   *
   * @param throwingRunnable the potentially throwing runnable
   * @param exceptionHandler the handler to handle the potential exception
   * @param <E>              the specific checked exception type
   * @return a runnable
   */
  public static <E extends Exception> Runnable orHandleException(ThrowingRunnable<E> throwingRunnable,
                                                                 Consumer<Exception> exceptionHandler) {
    return () -> Guards.orHandleException(throwingRunnable, exceptionHandler);
  }
}
