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

        SaveCategoryRequest request = new SaveCategoryRequest("Electronics", "Electronic devices and accessories");
        CategoryModel mappedCategoryModel = new CategoryModel(null, "Electronics",
                "Electronic devices and accessories");
        when(categoryDtoMapper.requestToModel(request)).thenReturn(mappedCategoryModel);
        doNothing().when(categoryServicePort).save(any(CategoryModel.class));
        SaveCategoryResponse actualResponse = categoryService.save(request);
        assertNotNull(actualResponse, "Response should not be null");
        assertEquals(Constants.SAVE_CATEGORY_RESPONSE_MESSAGE, actualResponse.message(),
                "Response message should match expected constant");
        assertNotNull(actualResponse.time(), "Timestamp should not be null");
        verify(categoryDtoMapper, times(1)).requestToModel(request);
        ArgumentCaptor<CategoryModel> categoryCaptor = ArgumentCaptor.forClass(CategoryModel.class);
        verify(categoryServicePort, times(1)).save(categoryCaptor.capture());

        CategoryModel capturedCategory = categoryCaptor.getValue();
        assertEquals("Electronics", capturedCategory.getName(),
                "Category name should match the request");
        assertEquals("Electronic devices and accessories", capturedCategory.getDescription(),
                "Category description should match the request");

        verifyNoMoreInteractions(categoryServicePort, categoryDtoMapper);
    }

    @Test
    public void testSave_ValidCategoryRequest_CallsRepositoryWithCorrectEntity() {
        SaveCategoryRequest request = new SaveCategoryRequest("Books", "Literature and educational books");
        CategoryModel expectedCategoryModel = new CategoryModel(null, "Books", "Literature and educational books");
        when(categoryDtoMapper.requestToModel(request)).thenReturn(expectedCategoryModel);
        doNothing().when(categoryServicePort).save(expectedCategoryModel);

        categoryService.save(request);

        verify(categoryServicePort, times(1)).save(expectedCategoryModel);

        verify(categoryDtoMapper, times(1)).requestToModel(request);
    }

    @Test
    public void testSave_NullRequest_ThrowsException() {

        SaveCategoryRequest nullRequest = null;


        assertThrows(IllegalArgumentException.class, () -> {
            categoryService.save(nullRequest);
        }, "Should throw IllegalArgumentException for null request");

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
    @Test
    void testSave_EmptyName_ThrowsException() {
        SaveCategoryRequest request = new SaveCategoryRequest("", "Valid description");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> categoryService.save(request));
        assertEquals("Category name cannot be empty", ex.getMessage());
    }

    @Test
    void testSave_NameTooLong_ThrowsException() {
        String longName = "A".repeat(101);
        SaveCategoryRequest request = new SaveCategoryRequest(longName, "Valid description");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> categoryService.save(request));
        assertEquals("Category name exceeds 100 characters", ex.getMessage());
    }
}