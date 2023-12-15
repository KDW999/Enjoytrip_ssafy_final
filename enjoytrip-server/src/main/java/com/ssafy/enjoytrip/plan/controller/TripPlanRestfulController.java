package com.ssafy.enjoytrip.plan.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.plan.model.TripPlanDto;
import com.ssafy.enjoytrip.plan.model.service.TripPlanService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/plan")
@Api(tags = { "Trip Plan REST Controller" })
@Slf4j
public class TripPlanRestfulController {
	private final TripPlanService service;

	public TripPlanRestfulController(TripPlanService service) {
		this.service = service;
	}

	@ApiOperation(
		value = "여행계획 생성",
		notes = "ID가 {user-id}인 사용자의 새 여행계획을 생성합니다",
		consumes = "application/x-www-form-urlencoded"
	)
	@ApiResponses(
		{
			@ApiResponse(code = 201, message = "여행계획 생성 완료"),
			@ApiResponse(code = 400, message = "올바르지 않은 사용자 정보 제공"),
			@ApiResponse(code = 500, message = "서버 오류")
		}
	)
	@PostMapping("/user/{user-id}")
	public ResponseEntity<?> createPlan(
		@PathVariable("user-id") @ApiParam("사용자 ID") String id,
		@RequestParam("title") @ApiParam("여행계획 제목") String title
	) throws UnsupportedEncodingException, URISyntaxException, SQLException {
		log.debug("POST /plan/user/{}", id);

		try {
			Integer plan = service.createPlan(id, title);
			return ResponseEntity.created(
				new URI("/plan/" + plan)
			).build();
		}
		catch(DataIntegrityViolationException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@ApiOperation(
		value = "사용자의 여행계획 조회",
		notes = "ID가 {user-id}인 사용자의 여행계획을 조회합니다",
		produces = "application/json"
	)
	@ApiResponses(
		{
			@ApiResponse(code = 200, message = "여행계획 조회 완료"),
			@ApiResponse(code = 500, message = "서버 오류")
		}
	)
	@GetMapping("/user/{user-id}")
	public ResponseEntity<?> listPlans(
		@PathVariable("user-id") @ApiParam("사용자 ID") String id
	) throws SQLException {
		log.debug("GET /plan/user/{}", id);
		return ResponseEntity.ok(service.listPlans(id));
	}

	@ApiOperation(
		value = "여행계획 조회",
		notes = "{plan-id}의 ID를 가진 여행계획을 조회합니다",
		produces = "application/json"
	)
	@ApiResponses(
		{
			@ApiResponse(code = 200, message = "여행계획 조회 완료"),
			@ApiResponse(code = 400, message = "올바르지 않은 요청"),
			@ApiResponse(code = 404, message = "존재하지 않는 데이터"),
			@ApiResponse(code = 500, message = "서버 오류")
		}
	)
	@GetMapping("/{plan-id}")
	public ResponseEntity<?> retrievePlan(
		@PathVariable("plan-id") @ApiParam("여행계획 ID") String id
	) throws SQLException {
		log.debug("GET /plan/{}", id);

		try {
			TripPlanDto plan = service.retrievePlan(Integer.valueOf(id));

			if(plan == null)
				return ResponseEntity.notFound().build();

			return ResponseEntity.ok(plan);
		}
		catch(NumberFormatException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@ApiOperation(
		value = "여행계획 수정",
		notes = "{plan-id}의 ID를 가진 여행계획을 수정합니다",
		consumes = "application/x-www-form-urlencoded"
	)
	@ApiResponses(
		{
			@ApiResponse(code = 204, message = "여행계획 수정 완료"),
			@ApiResponse(code = 400, message = "올바르지 않은 여행계획 ID"),
			@ApiResponse(code = 404, message = "존재하지 않는 여행계획"),
			@ApiResponse(code = 500, message = "서버 오류")
		}
	)
	@PatchMapping("/{plan-id}")
	public ResponseEntity<?> modifyPlan(
		@PathVariable("plan-id") @ApiParam(value = "여행계획 ID") String id,
		@RequestParam(value = "title", required = false) @ApiParam("여행계획 제목") String title,
		@RequestParam(value = "plan", required = false) @ApiParam("여행계획 데이터") String plan
	) throws SQLException {
		log.debug("PATCH /plan/{}", id);

		Integer pid = null;

		try {
			pid = Integer.valueOf(id);
		}
		catch(NumberFormatException e) {
			return ResponseEntity.badRequest().build();
		}

		TripPlanDto mod = new TripPlanDto();
		mod.setPlanNo(pid);
		mod.setTitle(title);
		mod.setPlan(plan);
		try {
			if(service.setPlan(mod) == 0)
				return ResponseEntity.notFound().build();
		}
		catch(DataIntegrityViolationException e) {
			return ResponseEntity.badRequest().build();
		}

		return ResponseEntity.noContent().build();
	}

	@ApiOperation(
		value = "여행계획에 관광지 추가",
		notes = "{plan-id}의 ID를 가진 여행계획에 관광지를 추가합니다",
		consumes = "application/x-www-form-urlencoded"
	)
	@ApiResponses(
		{
			@ApiResponse(code = 204, message = "관광지 추가 완료"),
			@ApiResponse(code = 400, message = "올바르지 않은 요청"),
			@ApiResponse(code = 404, message = "존재하지 않는 여행계획"),
			@ApiResponse(code = 500, message = "서버 오류")
		}
	)
	@PostMapping("/{plan-id}")
	public ResponseEntity<?> appendToPlan(
		@PathVariable("plan-id") @ApiParam("여행계획 ID") String id,
		@RequestParam("attraction-id") @ApiParam("관광지 ID") String attraction
	) throws SQLException {
		log.debug("POST /plan/{}", id);

		Integer pid = null;

		try {
			pid = Integer.valueOf(id);
			Integer.parseInt(attraction);
		}
		catch(NumberFormatException e) {
			return ResponseEntity.badRequest().build();
		}

		if(service.append(pid, attraction) == 0)
			return ResponseEntity.notFound().build();

		return ResponseEntity.noContent().build();
	}

	@ApiOperation(
		value = "여행계획 삭제",
		notes = "{plan-id}의 ID를 가진 여행계획을 삭제합니다"
	)
	@ApiResponses(
		{
			@ApiResponse(code = 204, message = "여행계획 삭제 완료"),
			@ApiResponse(code = 400, message = "올바르지 않은 요청"),
			@ApiResponse(code = 404, message = "존재하지 않는 여행계획"),
			@ApiResponse(code = 500, message = "서버 오류")
		}
	)
	@DeleteMapping("/{plan-id}")
	public ResponseEntity<?> deletePlan(
		@PathVariable("plan-id") @ApiParam("여행계획 ID") String id
	) throws SQLException {
		log.debug("DELETE /plan/{}", id);

		Integer pid = null;

		try {
			pid = Integer.valueOf(id);
		}
		catch(NumberFormatException e) {
			return ResponseEntity.badRequest().build();
		}

		if(service.removePlan(pid) == 0)
			return ResponseEntity.notFound().build();

		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> exception(Exception e) {
		return ResponseEntity.internalServerError().build();
	}
}
