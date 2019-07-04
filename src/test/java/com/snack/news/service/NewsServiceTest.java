package com.snack.news.service;

import com.snack.news.domain.News;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsServiceTest extends NewsTestcase {
	private static final String TEST_NEWS_TITLE = "test news title";
	private static final String TEST_NEWS_CONTENT = "test news content";

	@InjectMocks
	private NewsService newsService;

	@Mock
	private NewsRepository newsRepository;

	@Test
	@Transactional
	public void 뉴스를_생성할_수_있다() {
		final long id = 1L;

		NewsDto originNewsDto = NewsDto.builder()
				.title(TEST_NEWS_TITLE)
				.content(TEST_NEWS_CONTENT)
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
		NewsDto newsDto = NewsDto.builder()
				.title(TEST_TITLE)
				.content(TEST_CONTENT)
				.build();

		when(newsRepository.findById(any()))
				.thenReturn(Optional.of(newsDto.toEntity()));

		News result = newsService.getNews(any());

		assertThat(result.getTitle()).isEqualTo(TEST_TITLE);
		assertThat(result.getContent()).isEqualTo(TEST_CONTENT);
	}

	@Test
	@Transactional
	public void NewsDto로_뉴스를_조회할_수_있다() {
		// 이걸 검증할 필요가 있을까? 의미가 있는 검증일까?
	}

	@Test(expected = NewsNotFoundException.class)
	@Transactional
	public void ID가_유효하지않는다면_예외를_반환한다() {
		when(newsRepository.findById(1L))
				.thenReturn(Optional.empty());

		newsService.getNews(any());
	}


}