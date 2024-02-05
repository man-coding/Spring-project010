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

public class CommentDTO {

	int commentNo;  //댓글번호   시스템(자동으로 생김)

	int boardNo;  //글번호    시스템(화면에서 받아옴)

	String content; //댓글내용    사용자

	String writer; //작성자     시스템

	LocalDateTime regDate;   //등록일자 시스템

	LocalDateTime modDate;	//수정일자 시스템
}
