package com.telecom.service;

import com.telecom.ApplicationAbstractTest;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.instanceOf;

abstract public class AbstractServiceTest extends ApplicationAbstractTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public static <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        try {
            runnable.run();
            Assert.fail("Expected " + exceptionClass.getName());
        } catch (Exception e) {
            Assert.assertThat(ValidationUtil.getRootCause(e), instanceOf(exceptionClass));
        }
    }
}