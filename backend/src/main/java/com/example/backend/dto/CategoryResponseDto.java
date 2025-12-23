package com.example.backend.dto;

import com.example.backend.domain.Category;
import lombok.Getter;

@Getter
public class CategoryResponseDto {
    private final Long id;
    private final String name;

    public CategoryResponseDto(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}