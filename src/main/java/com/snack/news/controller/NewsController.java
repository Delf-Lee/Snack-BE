package com.snack.news.controller;

import com.snack.news.domain.news.News;
import com.snack.news.dto.ListCursorResult;
import com.snack.news.dto.RequestNewsDto;
import com.snack.news.dto.WrappedResponse;
import com.snack.news.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/news")
public class NewsController {
	private final NewsService newsService;

	@GetMapping
	public ResponseEntity<?> getNewsList(@ModelAttribute @Valid RequestNewsDto newsDto) {
		ListCursorResult<News> result = newsService.getNewsList(newsDto);

		if (result.isEmpty()) {
			return WrappedResponse.noContent().build();
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{newsId}")
	public ResponseEntity<?> getNews(@PathVariable Long newsId) {
		return ResponseEntity.ok(newsService.getNews(newsId));
	}
}