package com.snack.news.repository;

import com.snack.news.domain.News;
import com.snack.news.domain.Topic;
import com.snack.news.dto.NewsDto;
import com.snack.news.fixture.NewsTestcase;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NewsRepositoryTest extends NewsTestcase {
	@Autowired
	private NewsRepository newsRepository;

	@Test
	public void News를_저장할수있다() {
		long size = newsRepository.count();
		newsRepository.save(mockNews);

		List<News> newsList = newsRepository.findAll();
		assertThat(newsList.size()).isEqualTo(size + 1);
	}

	@Test
	public void News가_해당하는_날짜에_없다면_빈값을_반환한다() {
		newsRepository.save(mockNews);
		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = LocalDateTime.now().plusHours(1);

		List<News> newsList = newsRepository.findByCreateAtBetween(startTime, endTime);

		assertThat(newsList.size()).isEqualTo(0);
	}

	@Test
	@Transactional
	public void News가_해당하는_날짜에_있다면_리스트를_반환한다() {
		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = LocalDateTime.now().plusHours(1);
		newsRepository.save(mockNews);

		List<News> newsList = newsRepository.findByCreateAtBetween(startTime, endTime);

		assertThat(newsList.size()).isEqualTo(1);
		assertThat(newsList.get(0).getCreateAt()).isBefore(endTime).isAfter(startTime);
	}

	@Test
	@Transactional
	public void Topic_별로_뉴스를_조회할_수_있다() {
		final List<Long> testTopicIds = asList(2L, 3L);
		NewsDto newsDto = NewsDto.builder()
				.title(TEST_TITLE)
				.content(TEST_CONTENT)
				.topicIds(testTopicIds).build();

		List<Long> resultNewsIds = newsRepository.findByNewsDto(newsDto)
				.stream()
				.map(News::getId)
				.collect(toList());

		List<Long> expectedResultNewsIds = newsRepository.findAll()
				.stream()
				.filter(topics -> topics.getTopics()
						.stream()
						.map(Topic::getId)
						.anyMatch(testTopicIds::contains))
				.map(News::getId)
				.collect(toList());

		Assertions.assertThat(resultNewsIds).containsOnlyElementsOf(expectedResultNewsIds);
	}

	@Test
	@Transactional
	public void 원하는_기간의_뉴스들을_조회할_수_있다() {

		long totalNewsCount = newsRepository.count();
		NewsDto newsDtoBeforeJune = NewsDto.builder()
				.startDateTime(LocalDateTime.of(2019, 1, 1, 0, 0))
				.endDateTime(LocalDateTime.of(2019, 6, 30, 0, 0))
				.build();

		long newsListCountBeforeJune = newsRepository.findByNewsDto(newsDtoBeforeJune).size();

		NewsDto newsDtoAfterJune = NewsDto.builder()
				.startDateTime(LocalDateTime.of(2019, 7, 1, 0, 0))
				.endDateTime(LocalDateTime.of(2019, 12, 31, 0, 0))
				.build();

		long newsListCountAfterJune = newsRepository.findByNewsDto(newsDtoAfterJune).size();

		Assertions.assertThat(newsListCountBeforeJune + newsListCountAfterJune).isEqualTo(totalNewsCount);
	}
}