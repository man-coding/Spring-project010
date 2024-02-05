package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Member;

@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	BoardRepository boardRepository;

	@Test
	public void 회원등록() {

		Member member = Member.builder().id("user1").name("민수").password("password1").build();

		memberRepository.save(member);
	}

	@Test
	public void 회원일괄등록() {
		List<Member> list = new ArrayList<>();

		for (int i = 1; i < 30; i++) { // user에 i를 연결해서 id를 구분해줌.
			list.add(new Member("user" + i, "1234", "둘리", null));

		}
		memberRepository.saveAll(list);
	}

	@Test
	public void 회원목록조회() {

		List<Member> list = memberRepository.findAll();

		for (Member member : list) {
			System.out.println(member);
		}
	}

	@Test
	public void 회원단건조회() {
		Optional<Member> result = memberRepository.findById("user1");

		if (result.isPresent()) {
			Member member = result.get();
			System.out.println(member);
		}

	}

	@Test
	public void 회원수정() {

		Optional<Member> result = memberRepository.findById("user20");
		Member member = result.get();
		member.setName("만코");

		memberRepository.save(member);

	}

	@Test
	public void 회원삭제() {
		memberRepository.deleteById("chch2857");

		// 게시물이 없는 회원은 삭제해도 문제가 없지만
		// 게시물이 있는 경우에는 삭제할 수 없음(참조관계 때문)
	}

	@Test
	public void 연관관계설정후_회원삭제() {
		// 한큐에 게시물을 삭제해 주고 회원을 삭제하는 함수.

		// 1. user1의 객체를 생성한다. 2. 게시물을 지운다. 3. 멤버를 지운다
		Member member = Member.builder().id("user1").build();

		boardRepository.deleteWriter(member);//회원의 게시물 삭제
		memberRepository.deleteById("user1"); //회원 삭제
	}

}
