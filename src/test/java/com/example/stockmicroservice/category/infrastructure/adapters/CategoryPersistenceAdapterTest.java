package com.example.stockmicroservice.category.infrastructure.adapters;

import com.example.stockmicroservice.category.domain.model.CategoryModel;
import com.example.stockmicroservice.category.infrastructure.entities.CategoryEntity;
import com.example.stockmicroservice.category.infrastructure.mapper.CategoryEntityMapper;
import com.example.stockmicroservice.category.infrastructure.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryPersistenceAdapterTest {

    private CategoryRepository categoryRepository;
    private CategoryEntityMapper categoryEntityMapper;
    private CategoryPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryEntityMapper = mock(CategoryEntityMapper.class);
        adapter = new CategoryPersistenceAdapter(categoryRepository, categoryEntityMapper);
    }

    @Test
    void save_ShouldCallRepositoryWithMappedEntity() {
        CategoryModel model = new CategoryModel(1L, "Food", "Food category");
        CategoryEntity entity = new CategoryEntity(1L, "Food", "Food category");

        when(categoryEntityMapper.modelToEntity(model)).thenReturn(entity);

        adapter.save(model);

        verify(categoryRepository, times(1)).save(entity);
    }

    @Test
    void getCategoryByName_ShouldReturnMappedModel() {
        String name = "Books";
        CategoryEntity entity = new CategoryEntity(2L, name, "Books category");
        CategoryModel model = new CategoryModel(2L, name, "Books category");

        when(categoryRepository.findByName(name)).thenReturn(Optional.of(entity));
        when(categoryEntityMapper.categoryEntityToModel(entity)).thenReturn(model);

        CategoryModel result = adapter.getCategoryByName(name);

        assertNotNull(result);
        assertEquals(name, result.getName());
        verify(categoryRepository).findByName(name);
        verify(categoryEntityMapper).categoryEntityToModel(entity);
    }

    @Test
    void getCategoryByName_ShouldReturnNullWhenNotFound() {
        when(categoryRepository.findByName("Nonexistent")).thenReturn(Optional.empty());
        when(categoryEntityMapper.categoryEntityToModel(null)).thenReturn(null);

        CategoryModel result = adapter.getCategoryByName("Nonexistent");

        assertNull(result);
    }

    @Test
    void getAllCategories_ShouldReturnMappedModelList() {
        CategoryEntity entity1 = new CategoryEntity(1L, "One", "Desc1");
        CategoryEntity entity2 = new CategoryEntity(2L, "Two", "Desc2");
        List<CategoryEntity> entities = List.of(entity1, entity2);

        List<CategoryModel> models = List.of(
                new CategoryModel(1L, "One", "Desc1"),
                new CategoryModel(2L, "Two", "Desc2")
        );

        Page<CategoryEntity> page = new PageImpl<>(entities);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(categoryEntityMapper.entityListToModelList(entities)).thenReturn(models);

        List<CategoryModel> result = adapter.getAllCategories(0, 10);

        assertEquals(2, result.size());
        verify(categoryRepository).findAll(any(Pageable.class));
        verify(categoryEntityMapper).entityListToModelList(entities);
    }

}
