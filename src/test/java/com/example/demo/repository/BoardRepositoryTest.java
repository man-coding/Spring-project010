package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.demo.entity.Board;
import com.example.demo.entity.Member;

@SpringBootTest
public class BoardRepositoryTest {

	@Autowired
	BoardRepository boardRepository;

//	@Test
//	public void 게시물30개추가() {
//
//		Member member = Member.builder().id("둘리").build();
//
//		for (int i = 1; i <= 30; i++) {
//			Board board = Board.builder().title(i + "번글").writer("둘리").content("안녕하세요").build();
//			repository.save(board);
//		}
//
//	}

	@Test
	public void 페이징처리() {

		// 1페이지에 10개
		Pageable pageable = PageRequest.of(2, 10);

		// 게시물 리스트 + 페이지 정보
		Page<Board> result = boardRepository.findAll(pageable);

		System.out.println(result);

		// 게시물 리스트
		List<Board> list = result.getContent();

		System.out.println(list);

	}

	@Test
	public void 정렬조건추가() {

		// no 필드값을 기준으로 역정렬
		Sort sort = Sort.by("no").descending();

		// 정렬정보 추가
		Pageable pageable = PageRequest.of(0, 10, sort);

		Page<Board> result = boardRepository.findAll(pageable);

		List<Board> list = result.getContent();

		System.out.println(list);

	}

///여기서부터 회원 챕터
	@Test
	public void 없는아이디로게시물등록하기() {
		// 회원 엔티티 생성
		Member member = Member.builder().id("chch2857").build();
		// 회원테이블에 없는 아이디를 사용하면 에러남
		Board board = new Board(0, "4번글", "내용입니다", member);
		boardRepository.save(board);
	}

	@Test
	public void 게시물등록() {
		// 회원 엔티티 생성
		Member member = Member.builder().id("user1").build();
		// 아이디 하나로 여러 게시물 등록
		List<Board> list = new ArrayList<>();
		// 작성자는 현재 존재하는 사용자아이디를 사용해야함
		list.add(new Board(0, "1번글", "내용입니다", member));
		list.add(new Board(0, "2번글", "내용입니다", member));
		boardRepository.saveAll(list);
	}

	@Test
	public void 게시물조회() {
		Optional<Board> optional = boardRepository.findById(34); // 쿼리가 내부적으로 join 처리됨
		Board board = optional.get();
		System.out.println(board); // 회원정보가 함께 출력됨
	}

	@Test
	public void 게시물삭제() {
		boardRepository.deleteAll();
	}

}
