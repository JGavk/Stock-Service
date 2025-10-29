package com.example.stockmicroservice.category.domain.model;

import com.example.stockmicroservice.category.domain.exceptions.MaxCharacterException;
import com.example.stockmicroservice.category.domain.exceptions.MaxLengthException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class CategoryModelTest {

    @Test
    void validCategory_BasicCreation() {
        CategoryModel category = new CategoryModel(1L, "Books", "Educational materials");
        assertEquals("Books", category.getName());
        assertEquals("Educational materials", category.getDescription());
        assertEquals(1L, category.getId());
    }

    @Test
    void settersAndGetters_Test() {
        CategoryModel category = new CategoryModel(2L, "Tech", "Technology items");
        category.setName("Updated Tech");
        category.setDescription("Updated description");

        assertEquals("Updated Tech", category.getName());
        assertEquals("Updated description", category.getDescription());
    }

    @Test
    void nameOrDescriptionTooLong_ExceptionThrown() {
        String tooLongName = "A".repeat(51);
        String tooLongDesc = "D".repeat(91);

        assertThrows(MaxLengthException.class,
                () -> new CategoryModel(1L, tooLongName, "Valid desc"));

        assertThrows(MaxCharacterException.class,
                () -> new CategoryModel(1L, "Valid name", tooLongDesc));
    }

    @Test
    void nullOrBlankFields_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new CategoryModel(1L, null, "Valid"));

        assertThrows(IllegalArgumentException.class,
                () -> new CategoryModel(1L, "Valid", null));

        assertThrows(IllegalArgumentException.class,
                () -> new CategoryModel(1L, "   ", "Valid"));

        assertThrows(IllegalArgumentException.class,
                () -> new CategoryModel(1L, "Valid", "   "));
    }

    @Test
    void nameAndDescriptionAtBoundary_ShouldNotThrow() {
        String validName = "N".repeat(50);
        String validDesc = "D".repeat(90);
        assertDoesNotThrow(() -> new CategoryModel(1L, validName, validDesc));
    }

    @Test
    void setters_ShouldThrowOnInvalidValues() {
        CategoryModel category = new CategoryModel(1L, "Valid", "Valid");

        assertThrows(MaxLengthException.class, () -> category.setName("A".repeat(51)));
        assertThrows(MaxCharacterException.class, () -> category.setDescription("D".repeat(91)));
        assertThrows(NullPointerException.class, () -> category.setName(null));
        assertThrows(NullPointerException.class, () -> category.setDescription(null));
    }
    @Test
    void settersAtLimit_ShouldWorkCorrectly() {
        CategoryModel category = new CategoryModel(1L, "Init", "Init desc");
        String nameLimit = "N".repeat(50);
        String descLimit = "D".repeat(90);

        category.setName(nameLimit);
        category.setDescription(descLimit);

        assertEquals(50, category.getName().length());
        assertEquals(90, category.getDescription().length());
    }

}