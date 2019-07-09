package com.snack.news.service;

import com.snack.news.domain.Category;
import com.snack.news.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<Category> getCategoryList() {
		return categoryRepository.findAll();
	}
}
