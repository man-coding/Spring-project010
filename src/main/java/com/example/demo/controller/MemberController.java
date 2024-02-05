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

import com.example.demo.dto.MemberDTO;
import com.example.demo.service.MemberService;

@Controller
@RequestMapping
public class MemberController {

	@Autowired
	private MemberService service;

	@GetMapping("/member/list")
	public void list(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Page<MemberDTO> list = service.getList(page);
		model.addAttribute("list", list);

		System.out.println("전체 페이지 수: " + list.getTotalPages());
		System.out.println(("전체 게시물 수: " + list.getTotalElements()));
		System.out.println("현재 페이지 번호: " + (list.getNumber() + 1));
		System.out.println("페이지 표시할 게시물 수: " + list.getNumberOfElements());
	}

	@GetMapping("/register")
	public String register() {
		return "member/register";

	}

	@PostMapping("register")
	public String registerPost(MemberDTO dto, RedirectAttributes redirectAttributes) {

		boolean isSuccess = service.register(dto);
		if (isSuccess) {
			//성공시 목록화면으로 이동
			return "redirect:/";

		} else {
			redirectAttributes.addFlashAttribute("msg", "아이디가 중복되어 등록에 실패하였습니다");
			//실패시 회원가입폼으로 이동
			return "redirect:register";
		}

	}

	@GetMapping("/member/read")
	public void read(@RequestParam(name = "id") String id, @RequestParam(defaultValue = "0", name = "page") int page,
			Model model) {// 리턴 타입이 void 이면 화면을 반환하겠다는 뜻.

		MemberDTO dto = service.read(id);
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);

	}

}
