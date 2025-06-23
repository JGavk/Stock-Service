package com.example.stockmicroservice.category.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class CategoryModelTest {

    @Test
    void getId() {
        CategoryModel category = new CategoryModel(10L, "Tech", "Tech-related items");
        assertEquals(10L, category.getId());
    }

    @Test
    void getName() {
        CategoryModel category = new CategoryModel(1L, "Books", "Books category");
        assertEquals("Books", category.getName());
    }

    @Test
    void setName() {
        CategoryModel category = new CategoryModel(2L, "Old Name", "Some description");
        category.setName("New Name");
        assertEquals("New Name", category.getName());
    }

    @Test
    void getDescription() {
        CategoryModel category = new CategoryModel(3L, "Clothes", "All clothing items");
        assertEquals("All clothing items", category.getDescription());
    }

    @Test
    void setDescription() {
        CategoryModel category = new CategoryModel(4L, "Shoes", "Footwear");
        category.setDescription("Updated footwear description");
        assertEquals("Updated footwear description", category.getDescription());
    }

}