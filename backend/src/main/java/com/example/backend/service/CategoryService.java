package com.example.backend.service;

import com.example.backend.domain.Category;
import com.example.backend.dto.CategoryResponseDto;
import com.example.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponseDto createCategory(String name) {
        // 이미 존재하는 카테고리인지 확인
        categoryRepository.findByName(name).ifPresent(c -> {
            throw new IllegalArgumentException("이미 존재하는 카테고리 이름입니다.");
        });

        Category category = new Category();
        category.setName(name);
        Category savedCategory = categoryRepository.save(category);
        return new CategoryResponseDto(savedCategory);
    }

    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }
}