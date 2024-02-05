package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.CommentDTO;
import com.example.demo.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentService service;

	@ResponseBody
	@GetMapping("/list")
	public List<CommentDTO> list(@RequestParam(name = "boardNo") int boardNo) {
		List<CommentDTO> commentlist = service.getListByBoardNo(boardNo);

		return commentlist; // String 이 아니기 때문에 데이터 그 자체로 보낸다. 화면단에서 ajax를 쓰기 때문.
	}

	@ResponseBody
	@PostMapping("/register")
	public HashMap<String, Boolean> register(CommentDTO dto) {
		// 맵 객체 생성
		HashMap<String, Boolean> map = new HashMap<>();
		// 임시 아이디
		String id = "user1";
		dto.setWriter(id);
		// 새로운 댓글 등록
		service.register(dto);
		// 처리결과 반환
		map.put("success", true);
		return map;

	}

	@ResponseBody
	@GetMapping("/remove")
	public HashMap<String, Boolean> remove(@RequestParam(name = "commentNo") int commentNo) {

		// 맵 객체 생성
		HashMap<String, Boolean> map = new HashMap<>();
		// 댓글 삭제
		boolean result = service.remove(commentNo);
		// 처리결과 반환
		map.put("success", result);

		return map;
	}

}
