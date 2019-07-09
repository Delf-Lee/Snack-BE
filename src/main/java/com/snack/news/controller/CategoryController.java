package com.snack.news.controller;

import com.snack.news.domain.Category;
import com.snack.news.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {
	private final CategoryService categoryService;

	@GetMapping
	public List<Category> getCategoryList() {
		return categoryService.getCategoryList();
	}

}