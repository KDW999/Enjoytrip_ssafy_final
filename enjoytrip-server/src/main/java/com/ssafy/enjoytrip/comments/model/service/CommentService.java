package com.ssafy.enjoytrip.comments.model.service;

import java.util.List;

import com.ssafy.enjoytrip.comments.model.CommentDto;

public interface CommentService {

	boolean registComment(CommentDto commentDto);

	List<CommentDto> list(int isbn);

	boolean modify(CommentDto commentDto);

	boolean delete(int commentNo);

}
