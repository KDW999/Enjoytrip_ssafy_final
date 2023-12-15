package com.ssafy.enjoytrip.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardDto {
	private int articleNo;
	private String userId;
	private String subject;
	private String content;
	private String attractionId; // 관광지 고유번호
	private String registerTime;
	private int isDeleted;
	private String name;
}
