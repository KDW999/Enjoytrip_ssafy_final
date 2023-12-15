package com.ssafy.enjoytrip.comments.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.ssafy.enjoytrip.comments.model.CommentDto;
import com.ssafy.enjoytrip.comments.model.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, maxAge = 6000)
@RestController
@RequestMapping("/comment")
@Api(tags = { "Comment REST Controller" })
@Slf4j
public class CommentRestfulController {

	private CommentService commentService;

	public CommentRestfulController(CommentService commentService) {
		this.commentService = commentService;
	}

	@ApiOperation(value = "댓글 목록", notes = "게시글에 등록된 댓글 목록을 반환한다.")
	@ApiResponses(
			{ 
				@ApiResponse(code = 200, message = "댓글 반환 성공"), 
			}
			      )
	@GetMapping("/{articleNo}")
	public ResponseEntity<?> listComment(@PathVariable("articleNo") int articleNo) {

		log.debug("listComment - 호출");
		
		try {
			List<CommentDto> listComment = commentService.list(articleNo);
			return new ResponseEntity<List<CommentDto>>(listComment, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "댓글 작성", notes = "게시글에 등록할 새로운 댓글을 입력합니다.")
	@ApiResponses(
			{ 
				@ApiResponse(code = 200, message = "댓글 작성 성공"), 
			}
			     )
	@PostMapping("/regist")
	public ResponseEntity<?> registComment(
			@RequestBody @ApiParam(value = "댓글 정보", required = true) CommentDto commentDto) {

		log.info("registComment commentDto - {}", commentDto);
		try {
			boolean isWrite = commentService.registComment(commentDto);

			if (isWrite) {
				return new ResponseEntity<Void>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value ="댓글 수정", notes = "등록된 댓글을 수정할 데이터를 입력합니다.")
	@ApiResponses(
			{
				@ApiResponse(code = 200, message = "댓글 수정 성공"),
			}
			     )
	@PutMapping("/{commentNo}")
	public ResponseEntity<?> modifyComment(@PathVariable("commentNo") int commentNo, @RequestBody CommentDto commentDto){
		
		log.info("modifyComment commentDto - {}", commentDto);
		
		try {
			commentDto.setCommentNo(commentNo);
			boolean isModify = commentService.modify(commentDto);
			
			if(isModify) {
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "댓글 삭제", notes = "등록된 댓글을 삭제한다.")
	@DeleteMapping("/{commentNo}")
	public ResponseEntity<?> deleteComment(@PathVariable("commentNo") int commentNo){
		log.debug("deleteComment - 호출");
		
		try {
			boolean isDelete = commentService.delete(commentNo);
			
			if(isDelete) {
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
