package com.ssafy.enjoytrip.member.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.member.model.MemberDto;
import com.ssafy.enjoytrip.member.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	private final MemberMapper mapper;
	private final PasswordEncoder encoder;

	private MemberServiceImpl(MemberMapper mapper, PasswordEncoder encoder) {
		this.mapper = mapper;
		this.encoder = encoder;
	}

	@Override
	public void registerMember(MemberDto memberDto) throws SQLException {
		memberDto.setPassword(encoder.encode(memberDto.getPassword()));
		mapper.registerMember(memberDto);
	}

	@Override
	public MemberDto login(String userId, String userPass) throws SQLException {
		MemberDto member = mapper.login(userId);

		if(member != null && encoder.matches(userPass, member.getPassword())) {
			member.setPassword(null);
			return member;
		}

		return null;
	}

	@Override
	public void modifyMember(String id, String username, String password) throws SQLException {
		MemberDto member = this.select(id);

		if(!(member instanceof MemberDto))
			throw new IllegalArgumentException();

		if(username != null)
			member.setName(username);
		if(password != null)
			member.setPassword(encoder.encode(password));

		mapper.modifyMember(member);
	}

	@Override
	public void deleteMember(String userId) throws SQLException {
		mapper.deleteMember(userId);
	}

	@Override
	public List<MemberDto> list() throws SQLException {
		return mapper.selectAll();
	}

	@Override
	public MemberDto select(String userId) throws SQLException {
		return mapper.select(userId);
	}

	@Override
	public void remove(String id) throws SQLException {
		this.deleteMember(id);
	}
}
