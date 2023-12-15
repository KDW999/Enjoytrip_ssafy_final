package com.ssafy.enjoytrip.member.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.member.model.MemberDto;

public interface MemberService {

	void registerMember(MemberDto memberDto) throws SQLException;

	MemberDto login(String userId, String userPass) throws SQLException;

	void modifyMember(String id, String username, String password) throws SQLException;

	void deleteMember(String userId) throws SQLException;

	List<MemberDto> list() throws SQLException;

	MemberDto select(String userId) throws SQLException;

	void remove(String id) throws SQLException;

}
