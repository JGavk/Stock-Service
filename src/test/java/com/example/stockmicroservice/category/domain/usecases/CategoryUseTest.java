package com.example.stockmicroservice.category.domain.usecases;

import com.example.stockmicroservice.category.domain.exceptions.CategoryAlreadyExistsException;
import com.example.stockmicroservice.category.domain.model.CategoryModel;
import com.example.stockmicroservice.category.domain.ports.out.CategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryUseTest {

    private CategoryPersistencePort categoryPersistencePort;
    private CategoryUse categoryUse;

    @BeforeEach
    void setUp() {
        categoryPersistencePort = mock(CategoryPersistencePort.class);
        categoryUse = new CategoryUse(categoryPersistencePort);
    }

    @Test
    void save_ShouldSaveCategory_WhenCategoryDoesNotExist() {
        // Arrange
        CategoryModel newCategory = new CategoryModel(null, "Electronics","adsdafg");

        when(categoryPersistencePort.getCategoryByName("Electronics")).thenReturn(null);

        // Act
        categoryUse.save(newCategory);

        // Assert
        verify(categoryPersistencePort, times(1)).save(newCategory);
    }

    @Test
    void save_ShouldThrowException_WhenCategoryAlreadyExists() {
        // Arrange
        CategoryModel existingCategory = new CategoryModel(1L, "Books","adsdafg");

        when(categoryPersistencePort.getCategoryByName("Books")).thenReturn(existingCategory);

        // Act & Assert
        CategoryModel newCategory = new CategoryModel(null, "Books","adsdafg");

        assertThrows(CategoryAlreadyExistsException.class, () -> {
            categoryUse.save(newCategory);
        });

        verify(categoryPersistencePort, never()).save(any());
    }

    @Test
    void getAllCategories_ShouldReturnListFromPersistencePort() {
        // Arrange
        List<CategoryModel> expectedCategories = Arrays.asList(
                new CategoryModel(1L, "Books","adsdafg"),
                new CategoryModel(2L, "Clothing","adsdafg")
        );

        when(categoryPersistencePort.getAllCategories(0, 10)).thenReturn(expectedCategories);

        // Act
        List<CategoryModel> result = categoryUse.getAllCategories(0, 10);

        // Assert
        assertEquals(expectedCategories.size(), result.size());
        assertEquals(expectedCategories.get(0).getName(), result.get(0).getName());
        verify(categoryPersistencePort, times(1)).getAllCategories(0, 10);
    }
}