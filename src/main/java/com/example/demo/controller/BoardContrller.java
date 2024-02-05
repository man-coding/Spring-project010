package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.BoardDTO;
import com.example.demo.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardContrller {

	@Autowired
	BoardService service;



	// 목록화면
	@GetMapping("/list")
//	public void list(Model model) {
//		List<BoardDTO> list = service.getList(); // 서비스로 게시물 목록 가져오기
//		model.addAttribute("list", list); // 화면에 게시물 리스트 전달
	// model은 화면단에 전달하는 객체임. page는 화면에서 받아오는 입력 소스임
	public void list(@RequestParam(defaultValue = "0", name = "page") int page, Model model) {

		Page<BoardDTO> list = service.getList(page);
		model.addAttribute("list", list);

		System.out.println("전체 페이지 수: " + list.getTotalPages());
		System.out.println(("전체 게시물 수: " + list.getTotalElements()));
		System.out.println("현재 페이지 번호: " + (list.getNumber() + 1));
		System.out.println("페이지 표시할 게시물 수: " + list.getNumberOfElements());
	}

	// 등록화면
	@GetMapping("/register")
	public void register() {

	}

	// 등록처리
	@PostMapping("/register") // 화면에서 전달받은 게시물 전달자 객체
	public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes) {

		// 게시물 등록하고 새로운 게시물 번호 반환
		int no = service.register(dto);

		// 목록화면에 새로운 게시물 번호 전달 flash
		redirectAttributes.addFlashAttribute("msg", no);

		// 목록화면으로 이동. HTML 경로 아님. url주소를 작성할 것.
		return "redirect:/board/list";
	}

	// 상세화면
	@GetMapping("/read")
	public void read(@RequestParam(name = "no") int no, @RequestParam(defaultValue = "0", name = "page") int page,
			Model model) {

		BoardDTO dto = service.read(no);

		model.addAttribute("dto", dto);
		// 상세화면에 게시물 정보 출력

		// 화며에 페이지번호 전달
		model.addAttribute("page", page);
	}

	@GetMapping("/modify")
	public void modify(@RequestParam(name = "no") int no, Model model) {

		BoardDTO dto = service.read(no);
		model.addAttribute("dto", dto);
	}

	@PostMapping("/modify")
	public String modifyPost(BoardDTO dto, RedirectAttributes redirectAttributes) {

		// 게시물 수정
		service.modify(dto);

		// 리다이렉트 주소에 파라미터 추가(?no=1) 화면이 아니라 링크에 파라미터 추가하는 기능 addattribute
		redirectAttributes.addAttribute("no", dto.getNo());

		// 상세화면으로 리다이렉트
		return "redirect:/board/read"; // 수정처리와 동시에 상세페이지로 돌아감
	}

	// 삭제처리
	@PostMapping("/remove")
	public String removePost(@RequestParam(name = "no") int no) {

		service.remove(no);

		return "redirect:/board/list"; // 삭제처리와 동시에 리스트로 돌아감
	}
}
