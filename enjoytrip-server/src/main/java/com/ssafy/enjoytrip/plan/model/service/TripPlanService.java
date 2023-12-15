package com.ssafy.enjoytrip.plan.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.enjoytrip.plan.model.TripPlanDto;

public interface TripPlanService {
	Integer createPlan(String userId, String title) throws SQLException;

	List<TripPlanDto> listPlans(String userId) throws SQLException;
	TripPlanDto retrievePlan(Integer planNo) throws SQLException;

	int setPlan(TripPlanDto plan) throws SQLException;

	int append(Integer planNo, String attractionId) throws SQLException;
	int insertAt(Integer planNo, Integer at, String attractionId) throws SQLException;
	int removeAt(Integer planNo, Integer at) throws SQLException;

	int removePlan(Integer planNo) throws SQLException;
}
