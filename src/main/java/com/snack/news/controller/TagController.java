package com.snack.news.controller;

import com.snack.news.dto.TagDto;
import com.snack.news.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/tag")
public class TagController {

	private final TagService tagService;

	@PostMapping
	public ResponseEntity<?> createTag(@Valid @RequestBody TagDto tagDto) {
		return ResponseEntity.ok(tagService.createTag(tagDto));
	}

	@GetMapping
	public ResponseEntity<?> getTopicList() {
		return ResponseEntity.ok(tagService.getAllTagList());
	}

	@PutMapping
	public ResponseEntity<?> updateTopic(@RequestBody TagDto tagDto) {
		return ResponseEntity.ok(tagService.updateTag(tagDto));
	}
}