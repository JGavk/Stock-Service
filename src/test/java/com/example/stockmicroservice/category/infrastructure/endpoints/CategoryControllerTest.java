package com.example.stockmicroservice.category.infrastructure.endpoints;

import com.example.stockmicroservice.category.application.dto.request.SaveCategoryRequest;
import com.example.stockmicroservice.category.application.dto.response.CategoryResponse;
import com.example.stockmicroservice.category.application.dto.response.SaveCategoryResponse;
import com.example.stockmicroservice.category.application.services.CategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private SaveCategoryRequest saveRequest;
    private SaveCategoryResponse saveResponse;
    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp() {
        saveRequest = new SaveCategoryRequest("Categoría Test", "Descripción Test");

        saveResponse = new SaveCategoryResponse("Categoría creada correctamente", LocalDateTime.now());

        categoryResponse = new CategoryResponse(1L, "Categoría Test", "Descripción Test");
    }

    @Test
    void testSaveCategory() {
        when(categoryService.save(saveRequest)).thenReturn(saveResponse);

        ResponseEntity<SaveCategoryResponse> response = categoryController.save(saveRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().message()).isEqualTo("Categoría creada correctamente");
        assertThat(response.getBody().time()).isNotNull();

        verify(categoryService).save(saveRequest);
    }

    @Test
    void testGetAllCategories() {
        when(categoryService.getAllCategories(0, 10)).thenReturn(Collections.singletonList(categoryResponse));

        ResponseEntity<List<CategoryResponse>> response = categoryController.getAllCategories(0, 10);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(categoryResponse);

        verify(categoryService).getAllCategories(0, 10);
    }
}
