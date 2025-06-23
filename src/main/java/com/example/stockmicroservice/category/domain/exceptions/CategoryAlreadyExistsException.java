package com.example.stockmicroservice.category.domain.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String message) {
        super("Category already exists");
    }
}
