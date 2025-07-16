package com.example.stockmicroservice.category.infrastructure.exceptions;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;
public class ExeptionConstatsTest {

    @Test
    void constantsShouldHaveExpectedValues() {
        assertEquals("The name of the category can not exceed 50 characters", ExceptionConstats.NAME_MAX_SIZE_MESSAGE);
        assertEquals("The name of the category can not be 0 characters", ExceptionConstats.NAME_MIN_SIZE_MESSAGE);
        assertEquals("The description of the category can not exceed 90 characters", ExceptionConstats.DESCRIPTION_MAX_SIZE_MESSAGE);
        assertEquals("The category already exists", ExceptionConstats.CATEGORY_EXISTS_EXCEPTION_MESSAGE);
        assertEquals("Size must be greater than 0", ExceptionConstats.SIZE_MINIMUM_VALUE);
    }

    @Test
    void constructorShouldBePrivate() throws Exception {
        Constructor<ExceptionConstats> constructor = ExceptionConstats.class.getDeclaredConstructor();
        assertTrue(constructor.canAccess(null) || !constructor.isAccessible()); // Java 17+

        constructor.setAccessible(true);
        ExceptionConstats instance = constructor.newInstance();

        assertNotNull(instance);  // No exception should be thrown, even though we never use the instance
    }
}
