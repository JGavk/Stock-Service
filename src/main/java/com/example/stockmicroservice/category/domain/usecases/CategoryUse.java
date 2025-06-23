package com.example.stockmicroservice.category.domain.usecases;

import com.example.stockmicroservice.category.domain.exceptions.CategoryAlreadyExistsException;
import com.example.stockmicroservice.category.domain.model.CategoryModel;
import com.example.stockmicroservice.category.domain.ports.in.CategoryServicePort;
import com.example.stockmicroservice.category.domain.ports.out.CategoryPersistencePort;

import java.util.List;

public class CategoryUse implements CategoryServicePort {
    private final CategoryPersistencePort categoryPersistencePort;

    public CategoryUse(CategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void save(CategoryModel categoryModel) {
        CategoryModel category = categoryPersistencePort.getCategoryByName(categoryModel.getName());

        if (category != null ) {
            throw new CategoryAlreadyExistsException("Category with name '" + categoryModel.getName() + "' already exists");
        }
        categoryPersistencePort.save(categoryModel);
    }

    @Override
    public List<CategoryModel> getAllCategories(Integer page, Integer size) {

        return categoryPersistencePort.getAllCategories(page, size);
    }
}
