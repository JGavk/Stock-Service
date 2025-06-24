package com.example.stockmicroservice.category.infrastructure.mapper;

import com.example.stockmicroservice.category.domain.model.CategoryModel;
import com.example.stockmicroservice.category.infrastructure.entities.CategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CategoryEntityMapperTest {

    private CategoryEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(CategoryEntityMapper.class);
    }

    @Test
    void testModelToEntity() {
        CategoryModel model = new CategoryModel(1L, "Electronics", "Electronic items");

        CategoryEntity entity = mapper.modelToEntity(model);

        assertNotNull(entity);
        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName(), entity.getName());
        assertEquals(model.getDescription(), entity.getDescription());
    }

    @Test
    void testCategoryEntityToModel() {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(2L);
        entity.setName("Books");
        entity.setDescription("Book category");

        CategoryModel model = mapper.categoryEntityToModel(entity);

        assertNotNull(model);
        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getName(), model.getName());
        assertEquals(entity.getDescription(), model.getDescription());
    }

    @Test
    void testEntityListToModelList() {
        CategoryEntity entity1 = new CategoryEntity();
        entity1.setId(1L);
        entity1.setName("A");
        entity1.setDescription("Desc A");

        CategoryEntity entity2 = new CategoryEntity();
        entity2.setId(2L);
        entity2.setName("B");
        entity2.setDescription("Desc B");

        List<CategoryEntity> entities = Arrays.asList(entity1, entity2);

        List<CategoryModel> models = mapper.entityListToModelList(entities);

        assertNotNull(models);
        assertEquals(2, models.size());
        assertEquals(entity1.getId(), models.get(0).getId());
        assertEquals(entity2.getName(), models.get(1).getName());
    }
}