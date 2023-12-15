package com.ssafy.enjoytrip.comments.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.comments.model.CommentDto;

@Mapper
public interface CommentMapper {

	int registComment(CommentDto commentDto);

	List<CommentDto> list(int articleNo);

	int modifyComment(CommentDto commentDto);

	int deleteComment(int commentNo);
}
