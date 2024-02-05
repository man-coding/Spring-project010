package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;

import jakarta.transaction.Transactional;

@Transactional
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	//메소드 이름이 곧 sql이 된다
	List<Comment> findByBoard(Board board);
	
	
	void deleteByBoard(Board board);
	
	
}
