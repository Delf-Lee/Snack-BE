package com.snack.news.dto;

import com.snack.news.domain.Topic;
import com.snack.news.domain.TopicType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import com.snack.news.domain.News;

@Getter
@Setter
@Builder
public class NewsDto {
	private Long id;
	private String title;
	private String content;
	private String link;
	private String type = TopicType.NONE.name();
	private String[] topics;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	public News toEntity() {
		return News.builder()
				.title(title)
				.content(content)
				.link(link)
				.build();
	}

	public News toEntity(List<Topic> topics) {
		return News.builder()
				.title(title)
				.content(content)
				.topics(topics)
				.link(link)
				.build();
	}
}