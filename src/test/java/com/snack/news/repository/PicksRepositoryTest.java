package com.snack.news.repository;

import com.snack.news.domain.picks.Pick;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.filter;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class PicksRepositoryTest {

	@Autowired
	private PicksRepository picksRepository;

	@Test
	@DisplayName("클라이언트에 보여질 picks 리스트를 시간 역순으로 정렬하여 가져온다")
	void getPicksListTest() {
		List<Pick> pickList = picksRepository.findAll(Sort.by(Sort.Direction.DESC, "publishAt"));
		assertThat(pickList.stream().map(Pick::getPublishAt)).isSortedAccordingTo(Comparator.reverseOrder());
	}

	@Test
	@DisplayName("마지막으로 본 것 이후의 pick을 차례대로 가져온다")
	void getPicksListTestForInfinityScroll() {
		PageRequest pageRequest = PageRequest.of(0, 2);

		final int lastPage = 3;
		Page<Pick> pickPage = picksRepository.findByIdLessThanOrderByIdDesc(lastPage, pageRequest);

		assertThat(pickPage.stream().map(Pick::getId)).containsExactly(2L, 1L);
	}
}
