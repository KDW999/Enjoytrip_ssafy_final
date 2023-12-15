package com.ssafy.enjoytrip.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	private final BoardMapper mapper;

	public BoardServiceImpl(BoardMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public void registerArticle(BoardDto boardDto) {
		mapper.registerArticle(boardDto);
	}

	@Override
	public List<BoardDto> listArticle() {
		return mapper.listArticle();
	}

	@Override
	public List<BoardDto> listArticle(String query) {
		return mapper.searchListBySubject(query);
	}

	@Override
	public void updateHit(int articleno) {
		mapper.updateHit(articleno);
	}

	@Override
	public BoardDto viewArticle(int no) {
		return mapper.viewArticle(no);
	}

	@Override
	public void modifyArticle(BoardDto boardDto) {
		mapper.modifyArticle(boardDto);
	}

	@Override
	public void deleteArticle(int no) {
		mapper.deleteArticle(no);
	}
}
