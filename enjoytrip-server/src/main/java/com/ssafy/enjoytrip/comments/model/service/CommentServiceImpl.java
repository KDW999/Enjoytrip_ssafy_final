package com.ssafy.enjoytrip.comments.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.comments.model.CommentDto;
import com.ssafy.enjoytrip.comments.model.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService{

	private final CommentMapper mapper;
	
	public CommentServiceImpl(CommentMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<CommentDto> list(int articleNo) {
		return mapper.list(articleNo);
	}
	
	@Override
	public boolean registComment(CommentDto commentDto) {
		return mapper.registComment(commentDto) == 1;
	}

	@Override
	public boolean modify(CommentDto commentDto) {
		return mapper.modifyComment(commentDto) == 1;
	}

	@Override
	public boolean delete(int commentNo) {
		return mapper.deleteComment(commentNo) == 1;
	}

}
