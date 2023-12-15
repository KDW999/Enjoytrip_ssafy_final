package com.ssafy.enjoytrip.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import com.ssafy.enjoytrip.board.model.mapper.BoardMapper;
import com.ssafy.enjoytrip.comments.model.mapper.CommentMapper;
import com.ssafy.enjoytrip.member.model.mapper.MemberMapper;
import com.ssafy.enjoytrip.plan.model.mapper.TripPlanMapper;

@Configuration
@MapperScan(basePackageClasses = { BoardMapper.class, MemberMapper.class, TripPlanMapper.class, CommentMapper.class })
public class DBConfiguration {}
