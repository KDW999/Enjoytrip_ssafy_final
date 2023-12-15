package com.ssafy.enjoytrip.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.board.model.BoardDto;

@Mapper
public interface BoardMapper {

	void registerArticle(BoardDto boardDto);

	List<BoardDto> listArticle();

	List<BoardDto> searchListBySubject(String subject);

	void updateHit(int articleno);

	BoardDto viewArticle(int no);

	void modifyArticle(BoardDto boardDto);

	void deleteArticle(int no);

}
