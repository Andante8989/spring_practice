package com.ict.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/secu/*")
public class SecurityCotroller {
	
	@GetMapping("/all")
	public void doAll() {
		log.info("일반사람 로그인");
	}
}
