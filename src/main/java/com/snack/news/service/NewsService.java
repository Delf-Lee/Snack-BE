package com.snack.news.service;

import com.snack.news.domain.News;
import com.snack.news.domain.Topic;
import com.snack.news.dto.NewsDto;
import com.snack.news.exception.NewsNotFoundException;
import com.snack.news.repository.NewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
public class NewsService {

	private final NewsRepository newsRepository;
	private final TopicService topicService;

	@Transactional
	public NewsDto createNews(NewsDto newsDto) {
		List<Topic> topics = topicService.getTopicList(newsDto.getTopicIds());
		News savedNews = newsRepository.save(newsDto.toEntity(topics));

		return NewsDto.builder().id(savedNews.getId()).build();
	}

	public List<News> getAllNewsList() {
		return newsRepository.findAll();
	}

	public List<News> getNewsList(NewsDto newsDto) {
		return newsRepository.findByNewsDto(newsDto);
	}

	public News getNews(Long newsId) {
		return newsRepository.findById(newsId).orElseThrow(NewsNotFoundException::new);
	}
}