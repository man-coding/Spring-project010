package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

	String Id;
	String password;
	String name;
	String role; //사용자 권한 추가(사용자:ROLE_USER, 관리자: ROLE_ADMIN)
	LocalDateTime regDate;
	LocalDateTime modDate;
}
