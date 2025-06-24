package com.example.stockmicroservice.category.infrastructure.exceptions;

import com.example.stockmicroservice.category.domain.exceptions.CategoryAlreadyExistsException;
import com.example.stockmicroservice.category.domain.exceptions.MaxCharacterException;
import com.example.stockmicroservice.category.domain.exceptions.MaxLengthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ControllerAdvisorTest {

    @InjectMocks
    private ControllerAdvisor controllerAdvisor;

    private MaxCharacterException maxCharacterException;
    private MaxLengthException maxLengthException;
    private CategoryAlreadyExistsException categoryAlreadyExistsException;

    @BeforeEach
    void setUp() {
        maxCharacterException = mock(MaxCharacterException.class);
        maxLengthException = mock(MaxLengthException.class);
        categoryAlreadyExistsException = mock(CategoryAlreadyExistsException.class);
    }

    @Test
    void handleMaxCharacterException_ShouldReturnBadRequestWithCorrectMessage() {
        // Act
        ResponseEntity<?> response = controllerAdvisor.handleMaxCharacterException(maxCharacterException);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().toString())
            .contains("The description of the category can not exceed 90 characters");
    }

    @Test
    void handleMaxLengthException_ShouldReturnBadRequestWithCorrectMessage() {
        // Act
        ResponseEntity<?> response = controllerAdvisor.handleMaxLengthException(maxLengthException);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().toString())
            .contains("The name of the category can not exceed 50 characters");
    }

    @Test
    void handleCategoryAlreadyExistsException_ShouldReturnBadRequestWithCorrectMessage() {
        // Act
        ResponseEntity<?> response = controllerAdvisor.handleCategoryAlreadyExistsException(categoryAlreadyExistsException);
        
        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().toString())
            .contains("The category already exists");
    }
}