package com.example.demo.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.example.demo.dto.MemberDTO;

@SpringBootTest
public class MemberServiceTest {

	@Autowired
	MemberService service;

	@Test
	public void 회원목록조회() {
		// 첫번째 페이지 조회
		Page<MemberDTO> page = service.getList(2);
		// 회원목록 꺼내기
		List<MemberDTO> list = page.getContent();
		// 출력
		for (MemberDTO dto : list) {
			System.out.println(dto);
		}
	}

	@Test
	public void 회원등록() {
		MemberDTO dto = MemberDTO.builder().Id("chch3456").password("1234").name("둘리").role("ROLE_ADMIN").build();
		
		
		
		Boolean isReg = service.register(dto);
		if (isReg) {
			System.out.println("회원이 등록되었습니다.");
		} else {
			System.out.println("중복된 회원입니다.");
		}
	}

	@Test
	public void 회원단건조회() {

		MemberDTO dto = service.read("chch2857");
		System.out.println(dto);
	}
}
