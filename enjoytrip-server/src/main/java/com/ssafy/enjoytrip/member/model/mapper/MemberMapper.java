package com.ssafy.enjoytrip.member.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.member.model.MemberDto;

@Mapper
public interface MemberMapper {

	void registerMember(MemberDto memberDto) throws SQLException;

	MemberDto login(String userId) throws SQLException;

	void modifyMember(MemberDto memberDto) throws SQLException;

	void deleteMember(String userId) throws SQLException;

	List<MemberDto> selectAll() throws SQLException;

	MemberDto select(String userId) throws SQLException;

}
