package com.ssafy.enjoytrip.plan.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.plan.model.TripPlanDto;

@Mapper
public interface TripPlanMapper {
	int createPlan(TripPlanDto plan) throws SQLException;

	List<TripPlanDto> listAllPlans() throws SQLException;
	List<TripPlanDto> listPlans(String userId) throws SQLException;

	TripPlanDto retrievePlan(Integer planNo) throws SQLException;

	int setPlan(TripPlanDto plan) throws SQLException;

	int append(@Param("planNo") Integer planNo, @Param("contentId") String attractionId) throws SQLException;
	int insertAt(@Param("planNo") Integer planNo, @Param("path") Integer at, @Param("contentId") String attractionId) throws SQLException;
	int removeAt(@Param("planNo") Integer planNo, @Param("path") Integer at) throws SQLException;

	int removePlan(Integer planNo) throws SQLException;
}
