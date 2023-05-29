package com.compilit.bouncer;

import static com.compilit.bouncer.TestFunctions.DEFAULT_TEST_VALUE;
import static com.compilit.bouncer.TestFunctions.TEST_VALUE;
import static com.compilit.bouncer.TestFunctions.exceptionThrowingFunction;
import static com.compilit.bouncer.TestFunctions.exceptionThrowingSupplier;
import static com.compilit.bouncer.TestFunctions.exceptionThrowingThrowingFunction;
import static com.compilit.bouncer.TestFunctions.exceptionThrowingThrowingRunnable;
import static com.compilit.bouncer.TestFunctions.exceptionThrowingThrowingSupplier;
import static com.compilit.bouncer.TestFunctions.exteptionThrowingRunnable;
import static com.compilit.bouncer.TestFunctions.testFunction;
import static com.compilit.bouncer.TestFunctions.testRunnable;
import static com.compilit.bouncer.TestFunctions.testSupplier;
import static com.compilit.bouncer.Guards.orDefault;
import static com.compilit.bouncer.Guards.orHandleException;
import static com.compilit.bouncer.Guards.orNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

class GuardsTests {

  @Test
  void orNull_noException_shouldReturnValue() {
    assertThat(orNull(testSupplier())).isEqualTo(TEST_VALUE);
  }

  @Test
  void orNull_exception_shouldReturnNull() {
    var actual = orNull(exceptionThrowingSupplier());
    assertThat(actual).isNull();
  }

  @Test
  void orNull_checkedException_shouldReturnNull() {
    assertThat(Guards.orNullThrowing(exceptionThrowingThrowingSupplier())).isNull();
  }

  @Test
  void orDefault_noException_shouldReturnValue() {
    assertThat(orDefault(testSupplier(), DEFAULT_TEST_VALUE)).isEqualTo(TEST_VALUE);
  }

  @Test
  void orDefault_exception_shouldReturnDefaultValue() {
    assertThat(orDefault(exceptionThrowingSupplier(), DEFAULT_TEST_VALUE)).isEqualTo(DEFAULT_TEST_VALUE);
  }

  @Test
  void orNull_nonThrowingFunction_shouldReturnResult() {
    var result = orNull(testFunction(), 1);
    assertThat(result).isEqualTo("1");
  }

  @Test
  void orNull_throwingFunction_shouldReturnNull() {
    var result = orNull(exceptionThrowingFunction(), 1);
    assertThat(result).isNull();
  }

  @Test
  void orNull_throwingSupplier_shouldReturnNull() {
    var result = Guards.orNullThrowing(exceptionThrowingThrowingSupplier());
    assertThat(result).isNull();
  }

  @Test
  void orDefault_nonThrowingFunction_shouldReturnResult() {
    assertThat(orDefault(testFunction(), 1, "-1")).isEqualTo("1");

  }

  @Test
  void orDefault_throwingFunction_shouldReturnDefault() {
    assertThat(Guards.orDefaultThrowing(exceptionThrowingThrowingFunction(), 1, "-1")).isEqualTo("-1");
  }

  @Test
  void orDefault_throwingFunction_shouldReturnNull() {
    var result = Guards.orDefaultThrowing(exceptionThrowingThrowingSupplier(), null);
    assertThat(result).isNull();
  }

  @Test
  void orHandleException_nonThrowingFunction_shouldNotHandleException() {
    AtomicReference<Boolean> exceptionHandled = new AtomicReference<>();
    exceptionHandled.set(false);
    orHandleException(testRunnable(), x -> exceptionHandled.set(true));
    assertThat(exceptionHandled.get()).isFalse();
  }

  @Test
  void orHandleException_throwingFunction_shouldHandleException() {
    AtomicReference<Boolean> exceptionHandled = new AtomicReference<>();
    exceptionHandled.set(false);
    orHandleException(exteptionThrowingRunnable(), x -> exceptionHandled.set(true));
    assertThat(exceptionHandled.get()).isTrue();
  }

  @Test
  void orHandleCheckedException_nonThrowingFunction_shouldNotHandleException() {
    AtomicReference<Boolean> exceptionHandled = new AtomicReference<>();
    exceptionHandled.set(false);
    Guards.orHandleException(testRunnable(), x -> exceptionHandled.set(true));
    assertThat(exceptionHandled.get()).isFalse();
  }

  @Test
  void orHandleCheckedException_throwingFunction_shouldHandleException() {
    AtomicReference<Boolean> exceptionHandled = new AtomicReference<>();
    exceptionHandled.set(false);
    Guards.orHandleException(exceptionThrowingThrowingRunnable(), x -> exceptionHandled.set(true));
    assertThat(exceptionHandled.get()).isTrue();
  }

}
