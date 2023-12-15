package com.ssafy.enjoytrip.member.controller;

import java.net.URI;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.member.model.MemberDto;
import com.ssafy.enjoytrip.member.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/member")
@Api(tags = { "Member REST Controller" })
@Slf4j
public class MemberRestfulController {
	private final MemberService service;

	public MemberRestfulController(MemberService service) {
		this.service = service;
	}

	@ApiOperation(
		value = "로그인",
		notes = "로그인을 시도합니다",
		consumes = "application/x-www-form-urlencoded",
		produces = "application/json"
	)
	@ApiResponses(
		{
			@ApiResponse(code = 200, message = "로그인 성공"),
			@ApiResponse(code = 401, message = "로그인 실패")
		}
	)
	@PostMapping("/login")
	public ResponseEntity<?> authenticate(
		@RequestParam("id")
		@ApiParam(value = "아이디", required = true)
		String id,
		@RequestParam("password")
		@ApiParam(value = "비밀번호", required = true)
		String password,
		@ApiIgnore
		HttpSession session
	) throws Exception {

		MemberDto member = service.login(id, password);
		log.debug("POST /member/login", member);
		log.info("login - 호출 { }", member);

		if(member instanceof MemberDto) {
			session.setAttribute("user", member);
			return ResponseEntity.ok(member);
		}
		else
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@ApiOperation(value = "로그아웃", notes = "세션을 종료합니다")
	@ApiResponses({ @ApiResponse(code = 200, message = "로그아웃 완료") })
	@GetMapping("/logout")
	public ResponseEntity<?> terminate(@ApiIgnore HttpSession session) {
		log.debug("GET /member/logout");

		session.invalidate();
		return ResponseEntity.ok().build();
	}

	@ApiOperation(
		value = "회원가입",
		notes = "회원가입을 진행합니다",
		consumes = "application/x-www-form-urlencoded"
	)
	@ApiResponses(
		{
			@ApiResponse(code = 201, message = "회원가입 성공"),
			@ApiResponse(code = 400, message = "올바르지 않은 인자 제공")
		}
	)
	@PostMapping("/register")
	public ResponseEntity<?> register(
		@RequestParam("id")
		@ApiParam(value = "아이디")
		String id,
		@RequestParam(value = "username", required = false)
		@Nullable
		@ApiParam(value = "이름")
		String username,
		@RequestParam(value = "password")
		@ApiParam(value = "비밀번호")
		String password,
		@ApiIgnore
		HttpServletRequest request
	) throws Exception {
		log.debug("POST /member/register");

		MemberDto member = new MemberDto();

		member.setId(id);
		member.setName(username);
		member.setPassword(password);

		try {
			service.registerMember(member);
		}
		catch(DuplicateKeyException e) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.created(
			new URI("/member/user/" + URLEncoder.encode(member.getId(), "utf-8"))
		).build();
	}

	@ApiOperation(
		value = "회원 조회",
		notes = "회원 아이디가 {user-id}인 회원을 조회합니다",
		consumes = "application/x-www-form-urlencoded",
		produces = "application/json"
	)
	@ApiResponses(
		{
			@ApiResponse(code = 200, message = "회원 조회 성공"),
			@ApiResponse(code = 404, message = "회원 조회 실패")
		}
	)
	@GetMapping("/{user-id}")
	public ResponseEntity<?> retrieve(
		@PathVariable("user-id")
		@ApiParam(value = "아이디")
		String id
	) throws Exception {
		log.debug("GET /member/" + id);

		MemberDto member = service.select(id);

		if(member instanceof MemberDto)
			return ResponseEntity.ok(member);
		else
			return ResponseEntity.notFound().build();
	}

	@ApiOperation(
		value = "회원 수정",
		notes = "회원정보를 수정합니다",
		consumes = "application/x-www-form-urlencoded"
	)
	@ApiResponses(
		{
			@ApiResponse(code = 200, message = "회원정보 수정 완료"),
			@ApiResponse(code = 400, message = "존재하지 않는 회원정보 수정 시도")
		}
	)
	@PutMapping("/{user-id}")
	public ResponseEntity<?> modify(
		@PathVariable("user-id")
		@ApiParam(value = "아이디")
		String id,
		@RequestParam(value = "user-name", required = false)
		@Nullable
		@ApiParam(value = "이름")
		String username,
		@RequestParam(value = "user-password", required = false)
		@ApiParam(value = "비밀번호")
		String password
	) throws Exception {
		log.debug("PUT /member/" + id);

		try {
			service.modifyMember(id, username, password);
		}
		catch(IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok().build();
	}

	@ApiOperation(
		value = "회원 삭제",
		notes = "회원 아이디가 {user-id}인 회원을 삭제합니다"
	)
	@ApiResponses(
		{
			@ApiResponse(code = 200, message = "회원 삭제 완료")
		}
	)
	@DeleteMapping("/{user-id}")
	public ResponseEntity<?> delete(
		@PathVariable("user-id")
		@ApiParam(value = "아이디")
		String id
	) throws Exception {
		log.debug("DELETE /member/" + id);

		service.deleteMember(id);
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exception(Exception e) {
		return ResponseEntity.internalServerError().build();
	}
}
