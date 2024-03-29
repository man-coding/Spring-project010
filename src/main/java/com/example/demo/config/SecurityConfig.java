package com.example.demo.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// 패스워드 인코더를 컨테이너에 빈으로 등록하는 메소드
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// 메뉴별 접근제한 설정 //권한에 언더바 빼는 거 중요!!
		http.authorizeHttpRequests().requestMatchers("/register").permitAll() // 회원가입은 아무나 접근 가능
				.requestMatchers("/assets/*", "/css/*", "/js/*").permitAll() // 리소스는 아무나 접근 가능
				.requestMatchers("/").authenticated() // 메인화면은 로그인한 사용자이면 접근 가능
				.requestMatchers("/board/*").hasAnyRole("ADMIN", "USER") // 게시물 관리는 관리자 또는 사용자이면 접근 가능
				.requestMatchers("/comment/*").hasAnyRole("ADMIN", "USER").requestMatchers("/member/*")
				.hasRole("ADMIN"); // 회원 관리는 관리자이면 접근 가능

		http.formLogin() // 시큐리티가 제공하는 기본 로그인페이지 사용하기
				.loginPage("/customlogin") // 로그인 화면 주소
				.loginProcessingUrl("/login") // 로그인 처리 주소

				.successHandler(new AuthenticationSuccessHandler() {

					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
						response.sendRedirect("/");
					}
				}).permitAll(); // 접근 권한

		http.logout(); // 로그아웃 기능 사용하고 싶을 때

		// csrf 설정 해제
		http.csrf().disable();

		// 일반 사용자로 로그인
		// --게시물 등록, 조회, 수정, 삭제
		// --댓글 등록, 삭제

		// 관리자로 로그인
		// 회원조회
		// 게시물 등록, 조회, 수정, 삭제
		// 댓글 등록, 삭제
		return http.build();
	}
}
