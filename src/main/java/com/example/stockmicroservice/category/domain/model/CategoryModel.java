package com.example.stockmicroservice.category.domain.model;


import com.example.stockmicroservice.category.domain.exceptions.MaxCharacterException;
import com.example.stockmicroservice.category.domain.exceptions.MaxLengthException;
import com.example.stockmicroservice.category.domain.utils.constants.InnerConstants;


import java.util.Objects;

public class CategoryModel {

    private Long id;
    private String name;
    private String description;

    public CategoryModel(Long id, String name, String description) {
       this.id = id;
       if (name.length() > 50) throw new MaxLengthException();
       this.name = Objects.requireNonNull(name, InnerConstants.FIELD_NAME_NULL_MESSAGE);
       if (description.length() > 90) throw new MaxCharacterException();
       this.description = Objects.requireNonNull(description, InnerConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > 50) throw new MaxLengthException();
        this.name = Objects.requireNonNull(name, InnerConstants.FIELD_NAME_NULL_MESSAGE);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.length() > 90) throw new MaxCharacterException();
        this.description = Objects.requireNonNull(description, InnerConstants.FIELD_DESCRIPTION_EMPTY_MESSAGE);
    }
}
