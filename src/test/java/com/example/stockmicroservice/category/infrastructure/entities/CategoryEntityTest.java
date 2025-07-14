package com.example.stockmicroservice.category.infrastructure.entities;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CategoryEntityTest {

    @Test
    void noArgsConstructor_and_SettersGetters() {
        CategoryEntity e = new CategoryEntity();
        e.setId(10L);
        e.setName("Prueba");
        e.setDescription("Descripción de prueba");

        assertThat(e.getId()).isEqualTo(10L);
        assertThat(e.getName()).isEqualTo("Prueba");
        assertThat(e.getDescription()).isEqualTo("Descripción de prueba");
    }

    @Test
    void allArgsConstructor() {
        CategoryEntity e = new CategoryEntity(20L, "Otra", "Otra descripción");
        assertThat(e.getId()).isEqualTo(20L);
        assertThat(e.getName()).isEqualTo("Otra");
        assertThat(e.getDescription()).isEqualTo("Otra descripción");
    }

    @Test
    void equalsAndHashCode() {
        CategoryEntity a = new CategoryEntity(1L, "X", "d1");
        CategoryEntity b = new CategoryEntity(1L, "X", "d1");
        CategoryEntity c = new CategoryEntity(2L, "X", "d1");

        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());
        assertThat(a).isNotEqualTo(c);
    }

    @Test
    void toString_includesAllFields() {
        CategoryEntity e = new CategoryEntity(5L, "Test", "Desc");
        String s = e.toString();
        assertThat(s)
                .contains("CategoryEntity")
                .contains("id=5")
                .contains("name=Test")
                .contains("description=Desc");
    }
}
