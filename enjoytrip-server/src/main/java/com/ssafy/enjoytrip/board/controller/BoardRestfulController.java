package com.ssafy.enjoytrip.board.controller;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.board.model.BoardDto;
import com.ssafy.enjoytrip.board.model.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

// http://localhost/swagger-ui/index.html
@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/board")
@Api(tags = {"Board REST Controller"})
@Slf4j
public class BoardRestfulController {

	private BoardService boardService;

	public BoardRestfulController(BoardService boardService) {
		this.boardService = boardService;
	}

	@ApiOperation(
			value = "게시글 작성",
			notes = "새로운 게시글 정보를 입력합니다.")
	@ApiResponses(
			{
				@ApiResponse(code = 200, message = "게시글 작성 성공"),
			}
		)
	@PostMapping("/register")
	public ResponseEntity<?> registerArticle(
			@RequestBody @ApiParam(value="게시글 정보", required = true) BoardDto boardDto){

		log.info("registerArticle boardDto - {}", boardDto);
		try {
			boardService.registerArticle(boardDto);

			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(
			value = "게시글 보기",
			notes = "글 번호에 해당하고 is_deleted의 값이 0인  게시글의 정보를 반환하다.",
			response = BoardDto.class)
	@GetMapping("/{articleno}")
	public ResponseEntity<BoardDto> viewArticle(
		@PathVariable("articleno") @ApiParam(value="얻어올 글의 글번호", required = true) int articleno){

		log.info("viewArticle - 호출 : " + articleno);
//		boardService.updateHit(articleno);
		return new ResponseEntity<BoardDto>(boardService.viewArticle(articleno), HttpStatus.OK);
	}

	@ApiOperation(
			value = "게시글 목록",
			notes = "is_deleted의 값이 0인 모든 게시글의 정보를 반환한다.",
			response = List.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "회원목록 OK!"),
		@ApiResponse(code = 404, message = "페이지 없음!"),
		@ApiResponse(code = 500, message = "서버 에러!")})
	@GetMapping("/articlelist")
	public ResponseEntity<?> listArticle(){

		log.info("listArticle map - {}");
		try {
			List<BoardDto> list = boardService.listArticle();
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(list);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(
			value = "게시글 수정",
			notes = "수정할 게시글 정보를 입력한다.",
			response = String.class)
	@PutMapping("/{articleno}")
	public ResponseEntity<String> modifyArticle(
			@PathVariable("articleno")
			@ApiParam(value = "수정할 게시글 번호.", required = true) int articleno,
			@RequestBody @ApiParam(
			value="수정할 게시글 정보.",
			required = true) BoardDto boardDto){

		log.info("modifyArticle - 호출 { }", boardDto);

		boardService.modifyArticle(boardDto);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "게시글 삭제", notes = "글 번호에 해당하는 게시글의 정보를 삭제한다. is_deleted의 값을 1로 변경한다." )
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> deleteArticle(
			@PathVariable("articleno")
			@ApiParam(value = "삭제할 게시글 번호.", required = true) int articleno){

		log.info("deleteArticle - 호출");
		boardService.deleteArticle(articleno);
		return ResponseEntity.ok().build();
	}

	private ResponseEntity<String> exceptionHandling(Exception e){
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@ApiOperation(
			value = "게시글 검색",
			notes = "제목이 {query}인 글 목록을 반환한다.",
			response = List.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "회원목록 OK!"),
		@ApiResponse(code = 404, message = "페이지 없음!"),
		@ApiResponse(code = 500, message = "서버 에러!")})
	@GetMapping("/search/{query}")
	public ResponseEntity<?> listArticle(
		@PathVariable("query")
		@ApiParam(value = "검색어") String query
	){

		log.info("listArticle map - {}");
		try {
			List<BoardDto> list = boardService.listArticle(query);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(list);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
}
