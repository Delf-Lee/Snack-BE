package com.snack.news.controller;

import com.snack.news.domain.news.News;
import com.snack.news.domain.picks.Pick;
import com.snack.news.dto.AdminNewsDto;
import com.snack.news.dto.PickDto;
import com.snack.news.dto.WrappedResponse;
import com.snack.news.dto.Wrapper;
import com.snack.news.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/admin/api")
public class AdminController {

	private final int DEFAULT_PAGE_NUM = 1;

	private final AdminService adminService;

	@PostMapping("/news")
	public WrappedResponse<AdminNewsDto> createNews(@Valid @RequestBody AdminNewsDto newsDto) {
		return WrappedResponse.ok(Wrapper.valueOf(adminService.createNews(newsDto)));
	}

	@GetMapping("/news")
	public WrappedResponse<Page<News>> getNewsList() {
		final long DEFAULT_PAGE_SIZE = 1L;
		return getNewsList(DEFAULT_PAGE_SIZE);
	}

	@GetMapping("/news/{page}")
	public WrappedResponse<Page<News>> getNewsList(@PathVariable long page) {
		Page<News> result = adminService.getNewsList(page);
		if (result.isEmpty()) {
			return WrappedResponse.noContents();
		}
		return WrappedResponse.ok(Wrapper.valueOf(result));
	}

	@PutMapping("/news/{id}")
	public WrappedResponse<AdminNewsDto> updateNews(@PathVariable long id, @Valid @RequestBody AdminNewsDto newsDto) {
		return WrappedResponse.ok(Wrapper.valueOf(adminService.updateNews(id, newsDto)));
	}

	@DeleteMapping("/news/{id}")
	public WrappedResponse<AdminNewsDto> deleteNews(@PathVariable long id) {
		adminService.deleteNews(id);
		return WrappedResponse.okEmpty();
	}

	@PostMapping("/picks")
	public WrappedResponse<List<PickDto>> createPick(@Valid @RequestBody List<PickDto> pickDtoList) {
		return WrappedResponse.ok(Wrapper.valueOf(adminService.createPick(pickDtoList)));
	}

	@GetMapping("/picks")
	public WrappedResponse<Page<Pick>> getPickPage() {
		return getPickPage(DEFAULT_PAGE_NUM);
	}

	@GetMapping("/picks/{page}")
	public WrappedResponse<Page<Pick>> getPickPage(@PathVariable int page) {
		Page<Pick> result = adminService.getPickPage(page);
		if (result.isEmpty()) {
			return WrappedResponse.noContents();
		}
		return WrappedResponse.ok(Wrapper.valueOf(result));
	}

	@DeleteMapping("/picks/{id}")
	public WrappedResponse<AdminNewsDto> deletePicks(@PathVariable long id) {
		adminService.deletePicks(id);
		return WrappedResponse.okEmpty();
	}

	@PutMapping("/picks/{id}")
	public WrappedResponse<PickDto> updateNews(@PathVariable long id, @Valid @RequestBody PickDto pickDto) {
		return WrappedResponse.ok(Wrapper.valueOf(adminService.updatePicks(id, pickDto)));
	}
}