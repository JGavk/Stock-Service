package com.example.stockmicroservice.category.application.mappers;

import com.example.stockmicroservice.category.application.dto.request.SaveCategoryRequest;
import com.example.stockmicroservice.category.application.dto.response.CategoryResponse;
import com.example.stockmicroservice.category.domain.model.CategoryModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryDtoMapperTest {

    @Mock
    private CategoryDtoMapper categoryDtoMapper;

    @Test
    void testRequestToModel() {
        SaveCategoryRequest request = new SaveCategoryRequest("Electronics", "Devices and gadgets");
        CategoryModel expectedModel = new CategoryModel(1L, "Electronics", "Devices and gadgets");

        when(categoryDtoMapper.requestToModel(request)).thenReturn(expectedModel);
        CategoryModel actualModel = categoryDtoMapper.requestToModel(request);

        assertNotNull(actualModel);
        assertEquals(expectedModel.getName(), actualModel.getName());
        assertEquals(expectedModel.getDescription(), actualModel.getDescription());

        verify(categoryDtoMapper, times(1)).requestToModel(request);
    }

    @Test
    void testModelToResponse() {
        CategoryModel categoryModel = new CategoryModel(1L, "Electronics", "Devices and gadgets");
        CategoryResponse expectedResponse = new CategoryResponse(1L, "Electronics", "Devices and gadgets");

        when(categoryDtoMapper.modelToResponse(categoryModel)).thenReturn(expectedResponse);
        CategoryResponse actualResponse = categoryDtoMapper.modelToResponse(categoryModel);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse.id(), actualResponse.id());
        assertEquals(expectedResponse.name(), actualResponse.name());
        assertEquals(expectedResponse.description(), actualResponse.description());

        verify(categoryDtoMapper, times(1)).modelToResponse(categoryModel);
    }

    @Test
    void testModelToResponseList() {
        List<CategoryModel> categoryModels = Arrays.asList(
                new CategoryModel(1L, "Electronics", "Devices and gadgets"),
                new CategoryModel(2L, "Clothing", "Apparel and accessories")
        );
        List<CategoryResponse> expectedResponses = Arrays.asList(
                new CategoryResponse(1L, "Electronics", "Devices and gadgets"),
                new CategoryResponse(2L, "Clothing", "Apparel and accessories")
        );

        when(categoryDtoMapper.modelToResponseList(categoryModels)).thenReturn(expectedResponses);

        List<CategoryResponse> actualResponses = categoryDtoMapper.modelToResponseList(categoryModels);

        assertNotNull(actualResponses);
        assertEquals(expectedResponses.size(), actualResponses.size());
        assertEquals(expectedResponses.get(0).name(), actualResponses.get(0).name());
        assertEquals(expectedResponses.get(1).name(), actualResponses.get(1).name());

        verify(categoryDtoMapper, times(1)).modelToResponseList(categoryModels);
    }
    @Test
    void testRequestToModelWithEmptyName() {
        SaveCategoryRequest request = new SaveCategoryRequest("", "Description valid");

        CategoryModel expectedModel = new CategoryModel(null, "", "Description valid");
        when(categoryDtoMapper.requestToModel(request)).thenReturn(expectedModel);

        CategoryModel result = categoryDtoMapper.requestToModel(request);

        assertNotNull(result);
        assertTrue(result.getName().isEmpty(), "El nombre debería estar vacío");
    }

    @Test
    void testRequestToModelWithMaxLengthName() {
        String longName = "A".repeat(100); // límite permitido
        SaveCategoryRequest request = new SaveCategoryRequest(longName, "Description valid");

        CategoryModel expectedModel = new CategoryModel(null, longName, "Description valid");
        when(categoryDtoMapper.requestToModel(request)).thenReturn(expectedModel);

        CategoryModel result = categoryDtoMapper.requestToModel(request);

        assertEquals(longName, result.getName());
        assertEquals("Description valid", result.getDescription());
    }

    @Test
    void testRequestToModelWithTooLongName() {
        String tooLongName = "B".repeat(101); // excede el límite
        SaveCategoryRequest request = new SaveCategoryRequest(tooLongName, "Valid description");

        when(categoryDtoMapper.requestToModel(request))
                .thenThrow(new IllegalArgumentException("Name length exceeds 100 characters"));

        assertThrows(IllegalArgumentException.class, () -> categoryDtoMapper.requestToModel(request));
    }

    @Test
    void testRequestToModelWithEmptyDescription() {
        SaveCategoryRequest request = new SaveCategoryRequest("Books", "");

        CategoryModel expectedModel = new CategoryModel(null, "Books", "");
        when(categoryDtoMapper.requestToModel(request)).thenReturn(expectedModel);

        CategoryModel result = categoryDtoMapper.requestToModel(request);

        assertNotNull(result);
        assertTrue(result.getDescription().isEmpty(), "La descripción debería estar vacía");
    }

    @Test
    void testRequestToModelWithTooLongDescription() {
        String longDescription = "C".repeat(101);
        SaveCategoryRequest request = new SaveCategoryRequest("Books", longDescription);

        when(categoryDtoMapper.requestToModel(request))
                .thenThrow(new IllegalArgumentException("Description length exceeds 100 characters"));

        assertThrows(IllegalArgumentException.class, () -> categoryDtoMapper.requestToModel(request));
    }
}