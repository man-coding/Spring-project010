package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CustomUser;
import com.example.demo.dto.MemberDTO;
import com.example.demo.service.MemberService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	MemberService service;

	@Override // 로그인 처리를 하는 메소드
	public UserDetails loadUserByUsername(String username) {

		System.out.println("login id : " + username);

		MemberDTO dto = service.read(username);  //실제 회원 확인

		if (dto == null) {
			//사용자 정보가 없다면 에러를 발생시킴
			throw new UsernameNotFoundException("");
		} else {
			//dto를 인증객체로 변환하여 반환
			return new CustomUser(dto);
		}

	}
}
