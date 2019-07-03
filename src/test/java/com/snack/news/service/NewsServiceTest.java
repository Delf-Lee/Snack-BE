package com.snack.news.service;

import com.snack.news.domain.News;
import com.snack.news.domain.Topic;
import com.snack.news.dto.NewsDto;
import com.snack.news.exception.NewsNotFoundException;
import com.snack.news.fixture.NewsTestcase;
import com.snack.news.repository.NewsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsServiceTest extends NewsTestcase {
	private static final String TEST_NEWS_TITLE = "test news title";
	private static final String TEST_NEWS_CONTENT = "test news content";

	@InjectMocks
	private NewsService newsService;
	@Mock
	private NewsRepository newsRepository;
	@Mock
	private TopicService topicService;

	@Test
	@Transactional
	public void 뉴스를_생성할_수_있다() {
		final long id = 1L;

		NewsDto originNewsDto = NewsDto.builder()
				.title(TEST_NEWS_TITLE)
				.content(TEST_NEWS_CONTENT)
				.topicIds(Arrays.asList(1L, 2L))
				.build();

		NewsDto resultNewsDto = NewsDto.builder()
				.id(id)
				.title(TEST_NEWS_TITLE)
				.content(TEST_NEWS_CONTENT)
				.build();

		when(newsRepository.save(any(News.class)))
				.thenReturn(resultNewsDto.toEntity());

		NewsDto actual = newsService.createNews(originNewsDto);

		assertThat(originNewsDto.getId()).isEqualTo(actual.getId());
	}

	@Test
	@Transactional
	public void ID로_뉴스를_조회할_수_있다() {
		NewsDto newsDto = NewsDto.builder().title(TEST_TITLE).content(TEST_CONTENT).build();
		NewsDto savedNews = newsService.createNews(newsDto);
		Long id = savedNews.getId();

		News result = newsService.getNews(id);

		assertThat(result.getId()).isEqualTo(id);
		assertThat(result.getTitle()).isEqualTo(TEST_TITLE);
		assertThat(result.getContent()).isEqualTo(TEST_CONTENT);
	}

	@Test(expected = NewsNotFoundException.class)
	@Transactional
	public void ID가_유효하지않는다면_예외를_반환한다() {
		Long invalidNewsId = 999L;

		newsService.getNews(invalidNewsId);
	}
}