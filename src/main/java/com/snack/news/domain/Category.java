package com.snack.news.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public enum Category {
	IT(1L, "IT"), COMMERCE(2L, "커머스"), NONE(0L, "없음");

	@Id
	private long id;

	@Column(nullable = false)
	@Getter
	private String title;


	Category(Long id, String title) {
		// this.id = id;
		this.title = title;
	}
}
