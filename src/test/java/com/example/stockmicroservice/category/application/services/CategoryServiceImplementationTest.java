package com.example.stockmicroservice.category.application.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.stockmicroservice.category.application.dto.request.SaveCategoryRequest;
import com.example.stockmicroservice.category.application.dto.response.CategoryResponse;
import com.example.stockmicroservice.category.application.dto.response.SaveCategoryResponse;
import com.example.stockmicroservice.category.application.mappers.CategoryDtoMapper;
import com.example.stockmicroservice.category.application.services.implementation.CategoryServiceImplementation;
import com.example.stockmicroservice.category.domain.model.CategoryModel;
import com.example.stockmicroservice.category.domain.ports.in.CategoryServicePort;
import com.example.stockmicroservice.category.infrastructure.exceptions.ExceptionConstats;
import com.example.stockmicroservice.commons.configurations.utils.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplementationTest {

    @Mock
    private CategoryServicePort categoryServicePort;

    @Mock
    private CategoryDtoMapper categoryDtoMapper;

    @InjectMocks
    private CategoryServiceImplementation categoryService;

    @Test
    public void testSave_ValidCategoryRequest_ReturnsExpectedResponse() {
        // Given - Arrange
        SaveCategoryRequest request = new SaveCategoryRequest("Electronics", "Electronic devices and accessories");
        CategoryModel mappedCategoryModel = new CategoryModel(null, "Electronics",
                "Electronic devices and accessories");

        // Configure mocks - Note: categoryServicePort.save() is void, so no when()
        // needed
        when(categoryDtoMapper.requestToModel(request)).thenReturn(mappedCategoryModel);
        // No need to mock categoryServicePort.save() since it's void
        doNothing().when(categoryServicePort).save(any(CategoryModel.class));

        // When - Act
        SaveCategoryResponse actualResponse = categoryService.save(request);

        // Then - Assert
        // Verify the response is not null and contains expected message
        assertNotNull(actualResponse, "Response should not be null");
        assertEquals(Constants.SAVE_CATEGORY_RESPONSE_MESSAGE, actualResponse.message(),
                "Response message should match expected constant");
        assertNotNull(actualResponse.time(), "Timestamp should not be null");

        // Verify that the mapper was called with the correct request
        verify(categoryDtoMapper, times(1)).requestToModel(request);

        // Verify that the repository was called with the correct entity
        ArgumentCaptor<CategoryModel> categoryCaptor = ArgumentCaptor.forClass(CategoryModel.class);
        verify(categoryServicePort, times(1)).save(categoryCaptor.capture());

        CategoryModel capturedCategory = categoryCaptor.getValue();
        assertEquals("Electronics", capturedCategory.getName(),
                "Category name should match the request");
        assertEquals("Electronic devices and accessories", capturedCategory.getDescription(),
                "Category description should match the request");

        // Verify no unexpected interactions
        verifyNoMoreInteractions(categoryServicePort, categoryDtoMapper);
    }

    @Test
    public void testSave_ValidCategoryRequest_CallsRepositoryWithCorrectEntity() {
        // Given - Arrange
        SaveCategoryRequest request = new SaveCategoryRequest("Books", "Literature and educational books");
        CategoryModel expectedCategoryModel = new CategoryModel(null, "Books", "Literature and educational books");

        when(categoryDtoMapper.requestToModel(request)).thenReturn(expectedCategoryModel);
        // For void methods, use doNothing() or simply verify the call
        doNothing().when(categoryServicePort).save(expectedCategoryModel);

        // When - Act
        categoryService.save(request);

        // Then - Assert
        // Verify the exact entity passed to the repository
        verify(categoryServicePort, times(1)).save(expectedCategoryModel);

        // Verify the mapping process
        verify(categoryDtoMapper, times(1)).requestToModel(request);
    }

    @Test
    public void testSave_NullRequest_ThrowsException() {
        // Given - Arrange
        SaveCategoryRequest nullRequest = null;

        // When & Then - Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            categoryService.save(nullRequest);
        }, "Should throw IllegalArgumentException for null request");

        // Verify no interactions with mocks when exception is thrown
        verifyNoInteractions(categoryServicePort, categoryDtoMapper);
    }

    @Test
    public void testGetAllCategories() {
        int page = 0;
        int size = 10;
        List<CategoryModel> categoryModels = Arrays.asList(
                new CategoryModel(1L, "House", "House"),
                new CategoryModel(2L, "Apartment", "Apartment thing"));
        List<CategoryResponse> expectedResponses = Arrays.asList(
                new CategoryResponse(1L, "House", "House"),
                new CategoryResponse(2L, "Apartment", "Apartment thing"));

        when(categoryServicePort.getAllCategories(page, size)).thenReturn(categoryModels);
        when(categoryDtoMapper.modelToResponseList(categoryModels)).thenReturn(expectedResponses);

        List<CategoryResponse> actualResponses = categoryService.getAllCategories(page, size);

        assertNotNull(actualResponses);
        assertEquals(expectedResponses.size(), actualResponses.size());
        assertEquals(expectedResponses.get(0).name(), actualResponses.get(0).name());
        assertEquals(expectedResponses.get(1).name(), actualResponses.get(1).name());

        verify(categoryServicePort, times(1)).getAllCategories(page, size);
        verify(categoryDtoMapper, times(1)).modelToResponseList(categoryModels);
    }

    @Test
    public void testGetAllCategories_InvalidSize() {
        int page = 0;
        int size = 0;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.getAllCategories(page, size);
        });

        assertEquals(ExceptionConstats.SIZE_MINIMUM_VALUE, exception.getMessage());

        verify(categoryServicePort, never()).getAllCategories(anyInt(), anyInt());
        verify(categoryDtoMapper, never()).modelToResponseList(anyList());
    }
}