package com.compilit.bouncer;

import java.util.function.Function;
import java.util.function.Supplier;

class TestFunctions {

  static final String TEST_VALUE = "test";
  static final String DEFAULT_TEST_VALUE = "default";

  static Supplier<String> testSupplier() {
    return () -> TEST_VALUE;
  }


  static <T> Supplier<T> exceptionThrowingSupplier() {
    return () -> {throw new RuntimeException();};
  }

  static <I, O> Function<I, O> exceptionThrowingFunction() {
    return x -> {throw new RuntimeException();};
  }

  static <E extends Exception> ThrowingSupplier<String, E> exceptionThrowingThrowingSupplier() {
    return () -> {throw new Exception();};
  }

  static <E extends Exception> ThrowingSupplier<String, E> nonThrowingThrowingSupplier() {
    return () -> TEST_VALUE;
  }

  static <I, O, E extends Exception> ThrowingFunction<I, O, E> exceptionThrowingThrowingFunction() {
    return x -> {throw new Exception();};
  }

  static <I, O, E extends Exception> ThrowingFunction<I, O, E> nonThrowingThrowingFunction() {
    return x -> null;
  }

  static <E extends Exception> ThrowingRunnable<E> exceptionThrowingThrowingRunnable() {
    return () -> {throw new Exception();};
  }

  static <E extends Exception> ThrowingRunnable<E> nonThrowingThrowingRunnable() {
    return () -> System.out.println(TEST_VALUE);
  }

  static <I> Function<I, String> testFunction() {
    return Object::toString;
  }

  static Runnable testRunnable() {
    return () -> System.out.println(TEST_VALUE);
  }

  static Runnable exteptionThrowingRunnable() {
    return () -> {throw new RuntimeException();};
  }
}
