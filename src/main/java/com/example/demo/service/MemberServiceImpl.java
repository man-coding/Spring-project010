package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberRepository repository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Page<MemberDTO> getList(int pageNumber) { // 페이지 번호 받기

		int pageIndex = (pageNumber == 0) ? 0 : pageNumber - 1;

		Sort sort = Sort.by("regDate").descending();

		// 페이지번호,데이터건수,정렬방법을 입력하여 페이징 조건 생성
		Pageable pageable = PageRequest.of(pageIndex, 10, sort);

		Page<Member> entityPage = repository.findAll(pageable);

		// 엔티티 타입의 페이지를 DTO 타입으로 변환
		Page<MemberDTO> dtoPage = entityPage.map(entity -> entityToDto(entity));

		return dtoPage;
	}

	@Override
	public boolean register(MemberDTO dto) {

		Optional<Member> result = repository.findById(dto.getId());
		if (result.isPresent()) {
			System.out.println("사용중인 아이디입니다.");
			return false;
		} else {
			Member entity = dtoToEntity(dto);
			//시큐리티 추가
			//패스워드 인코더로 패스워드 암호화하기
			String enPw = passwordEncoder.encode(entity.getPassword());

			entity.setPassword(enPw);
			
			repository.save(entity);
			
			return true;
		}
//		String id = dto.getId();
//		MemberDTO getDto = read(id);
//
//		if (getDto != null) {
//
//			System.out.println("사용중인 아이디입니다.");
//
//			return false;
//		} else {
//			Member entity = dtoToEntity(dto);
//			repository.save(entity);
//			return true;
//		}

	}

	@Override
	public MemberDTO read(String id) {

		Optional<Member> result = repository.findById(id);
		if (result.isPresent()) {
			Member member = result.get();

			return entityToDto(member);
		} else
			return null;
	}

}
