package com.ssafy.enjoytrip.member.model;

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
public class MemberDto {
	private String id;
	private String name;
	private String password;
	private int ageGroup;
	private String gender;
	private String joinDate;
	private String token;
	private int idDeleted;
	
}
