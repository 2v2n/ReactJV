package com.example.backend.controller;

import com.example.backend.dto.CategoryRequestDto;
import com.example.backend.dto.CategoryResponseDto;
import com.example.backend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // 프론트엔드 주소로 변경
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto requestDto) {
        CategoryResponseDto createdCategory = categoryService.createCategory(requestDto.getName());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }
}