package com.example.demo.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.CommentDTO;

@SpringBootTest
public class CommentServiceTest {

	@Autowired
	CommentService service;

	@Test
	public void 댓글등록() {
		// boardNo 과 writer는 외래키이기 때문에 테이블에 있는 것을 사용해야 한다.
		CommentDTO dto = CommentDTO.builder().boardNo(1).commentNo(3).content("댓글입니당").writer("chch2857").build();
		service.register(dto);

	}

	@Test
	public void 게시물별_댓글목록조회() {
		// 담을 리스트를 생성해 줘야 함.
		List<CommentDTO> list = service.getListByBoardNo(1);

		for (CommentDTO commentDto : list) {
			System.out.println(commentDto);
		}

	}

	@Test
	public void 댓글삭제() {
		service.remove(5);
	}
}
