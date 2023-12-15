package com.ssafy.enjoytrip.comments.model;

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
public class CommentDto {
	private int commentNo;
	private int articleNo;
	private String userId;
	private String content;
	private String registerTime;
}
