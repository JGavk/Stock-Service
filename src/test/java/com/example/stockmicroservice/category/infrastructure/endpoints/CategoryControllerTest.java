package com.example.stockmicroservice.category.infrastructure.endpoints;

import com.example.stockmicroservice.category.application.dto.request.SaveCategoryRequest;
import com.example.stockmicroservice.category.application.dto.response.CategoryResponse;
import com.example.stockmicroservice.category.application.dto.response.SaveCategoryResponse;
import com.example.stockmicroservice.category.application.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@Import(CategoryControllerTest.Config.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class Config {
        @Bean
        public CategoryService categoryService() {
            return Mockito.mock(CategoryService.class);
        }
    }
    @Test
    void saveCategory_ReturnsCreatedCategory() throws Exception {
        SaveCategoryRequest request = new SaveCategoryRequest("Electronics", "Electronics objects");
        SaveCategoryResponse response = new SaveCategoryResponse("Electronics objects", LocalDateTime.now());

        Mockito.when(categoryService.save(any(SaveCategoryRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllCategories_ReturnsCategoryList() throws Exception {
        CategoryResponse category1 = new CategoryResponse(1L,"Electronics","Electronics objects");
        CategoryResponse category2 = new CategoryResponse(2L, "Books", "Books in library");

        List<CategoryResponse> categories = Arrays.asList(category1, category2);

        Mockito.when(categoryService.getAllCategories(0, 10)).thenReturn(categories);

        mockMvc.perform(get("/api/v1/category/get")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Electronics"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Books"));
    }
}
