package com.example.stockmicroservice.category.application.mappers;

import com.example.stockmicroservice.category.application.dto.request.SaveCategoryRequest;
import com.example.stockmicroservice.category.application.dto.response.CategoryResponse;
import com.example.stockmicroservice.category.domain.model.CategoryModel;
import org.junit.jupiter.api.Test;
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
}