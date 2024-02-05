package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass // JPA가 해당클래스는 테이블로 생성한다.
@EntityListeners(value = { AuditingEntityListener.class }) // 엔티티의 변화를 감지
@Getter
public abstract class BaseEntity {

	@CreatedDate
	LocalDateTime regDate;

	@LastModifiedDate
	LocalDateTime modDate;
}
