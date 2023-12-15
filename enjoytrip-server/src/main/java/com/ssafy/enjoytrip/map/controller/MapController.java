package com.ssafy.enjoytrip.map.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/map")
@Slf4j
public class MapController {
	@GetMapping
	public String map() {
		log.debug("GET /map");

		return "map";
	}
}
