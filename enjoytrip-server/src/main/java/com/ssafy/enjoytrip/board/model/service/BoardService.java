package com.ssafy.enjoytrip.board.model.service;

import java.util.List;

import com.ssafy.enjoytrip.board.model.BoardDto;

public interface BoardService {

	void registerArticle(BoardDto boardDto);

	List<BoardDto> listArticle();

	List<BoardDto> listArticle(String query);

	void updateHit(int articleno);

	BoardDto viewArticle(int no);

	void modifyArticle(BoardDto boardDto);

	void deleteArticle(int no);

}
