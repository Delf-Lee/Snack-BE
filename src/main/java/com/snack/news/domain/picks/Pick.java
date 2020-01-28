package com.snack.news.domain.picks;

import com.snack.news.domain.base.BaseTimeEntity;
import com.snack.news.domain.topic.Topic;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Getter
@ToString
@Entity
public class Pick extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String link;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "pick_topic",
			joinColumns = @JoinColumn(name = "pick_id"),
			inverseJoinColumns = @JoinColumn(name = "topic_id"))
	private List<Topic> topics;

	@Column
	private LocalDateTime publishAt;

	@Builder
	public Pick(String link, List<Topic> topics, LocalDateTime publishAt) {
		this.link = link;
		this.topics = topics;
		this.publishAt = Optional.ofNullable(publishAt).orElse(LocalDateTime.now());
	}
}