package com.snack.news.controller;

import com.snack.news.apiversion.Version;
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
	public ResponseEntity<?> getNewsList(@ModelAttribute @Valid RequestNewsDto newsDto, @RequestParam(defaultValue = "default") Version version) {
		ListCursorResult<News> result = newsService.getNewsList(newsDto);
		if (result.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		if(version == Version.V1) {
			return WrappedResponse.ok(result);
		}

		if(version == Version.DEFAULT) {
			return WrappedResponse.ok(result.getList());
		}

		throw new IllegalArgumentException();
	}

	@GetMapping("/{newsId}")
	public ResponseEntity<News> getNews(@PathVariable Long newsId) {
		return WrappedResponse.ok(newsService.getNews(newsId));
	}
}