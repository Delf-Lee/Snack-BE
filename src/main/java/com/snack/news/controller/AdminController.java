package com.snack.news.controller;

import com.snack.news.domain.news.News;
import com.snack.news.dto.AdminNewsDto;
import com.snack.news.dto.Wrapper;
import com.snack.news.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("admin/api")
public class AdminController {

	private final AdminService adminService;

	@PostMapping("/news")
	public ResponseEntity<?> createNews(@Valid @RequestBody AdminNewsDto newsDto) {
		return ResponseEntity.ok(new Wrapper<>(adminService.createNews(newsDto)));
	}

	@GetMapping("/news")
	public ResponseEntity<?> getNewsList() {
		final long DEFAULT_PAGE_SIZE = 1L;
		return getNewsList(DEFAULT_PAGE_SIZE);
	}

	@GetMapping("/news/{page}")
	public ResponseEntity<?> getNewsList(@PathVariable long page) {
		Page<News> result = adminService.getNewsList(page);
		if (result.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(result);
	}

	@PutMapping("/news/{newsId}")
	public ResponseEntity<?> updateNews(@PathVariable long newsId, @Valid @RequestBody AdminNewsDto newsDto) {
		return ResponseEntity.ok(adminService.updateNews(newsId, newsDto));
	}

	@DeleteMapping("/news/{id}")
	public ResponseEntity<AdminNewsDto> deleteNews(@PathVariable long id) {
		adminService.deleteNews(id);
		return ResponseEntity.ok().build();
	}
}
