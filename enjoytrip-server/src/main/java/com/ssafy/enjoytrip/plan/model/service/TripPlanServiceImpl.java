package com.ssafy.enjoytrip.plan.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.plan.model.TripPlanDto;
import com.ssafy.enjoytrip.plan.model.mapper.TripPlanMapper;

@Service
public class TripPlanServiceImpl implements TripPlanService {
	private final TripPlanMapper mapper;

	public TripPlanServiceImpl(TripPlanMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public Integer createPlan(String userId, String title) throws SQLException {
		TripPlanDto plan = new TripPlanDto();
		plan.setUserId(userId);
		plan.setTitle(title);

		mapper.createPlan(plan);
		return plan.getPlanNo();
	}

	@Override
	public List<TripPlanDto> listPlans(String userId) throws SQLException {
		return mapper.listPlans(userId);
	}

	@Override
	public TripPlanDto retrievePlan(Integer planNo) throws SQLException {
		return mapper.retrievePlan(planNo);
	}

	@Override
	public int setPlan(TripPlanDto plan) throws SQLException {
		return mapper.setPlan(plan);
	}

	@Override
	public int append(Integer planNo, String attractionId) throws SQLException {
		return mapper.append(planNo, attractionId);
	}

	@Override
	public int insertAt(Integer planNo, Integer at, String attractionId) throws SQLException {
		return mapper.insertAt(planNo, at, attractionId);
	}

	@Override
	public int removeAt(Integer planNo, Integer at) throws SQLException {
		return mapper.removeAt(planNo, at);
	}

	@Override
	public int removePlan(Integer planNo) throws SQLException {
		return mapper.removePlan(planNo);
	}
}
