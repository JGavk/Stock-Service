package com.example.stockmicroservice.category.domain.utils.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;


class InnerConstantsTest {

    @Test
    void constructor_ShouldThrowException_WhenCalledReflectively() throws Exception {
        Constructor<InnerConstants> constructor = InnerConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        try {
            constructor.newInstance();
            fail("Expected IllegalStateException");
        } catch (InvocationTargetException ex) {
            Throwable cause = ex.getCause();
            assertTrue(cause instanceof IllegalStateException);
            assertEquals("Utility class", cause.getMessage());
        }
    }
}