package com.snack.news.controller;

import com.snack.news.dto.CategoryDto;
import com.snack.news.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {
	private final CategoryService categoryService;

	@PostMapping
	public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		return ResponseEntity.ok(categoryService.createCategory(categoryDto));
	}

	@GetMapping
	public ResponseEntity<?> getCategoryList() {
		return ResponseEntity.ok(categoryService.getCategoryList());
	}

	@PutMapping
	public ResponseEntity<?> updateCategory(@RequestBody CategoryDto categoryDto) {
		return ResponseEntity.ok(categoryService.updateCategory(categoryDto));
	}
}