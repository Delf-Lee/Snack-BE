package com.snack.news.controller;

import com.snack.news.domain.topic.TopicSorting;
import com.snack.news.domain.topic.TopicType;
import com.snack.news.dto.TopicDto;
import com.snack.news.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/topic")
public class TopicController {
	private final TopicService topicService;

	@PostMapping
	public ResponseEntity<?> createTopic(@Valid @RequestBody TopicDto topicDto) {
		return ResponseEntity.ok(topicService.createTopic(topicDto));
	}

	@GetMapping("/{type}")
	public ResponseEntity<?> getTopicList(@PathVariable TopicType type, @RequestParam(defaultValue = "NAME") TopicSorting sort) {
		return ResponseEntity.ok(topicService.getTypeTopicList(type, sort));
	}

	@PutMapping
	public ResponseEntity<?> updateTopic(@RequestBody TopicDto topicDto) {
		return ResponseEntity.ok(topicService.updateTopic(topicDto));
	}
}