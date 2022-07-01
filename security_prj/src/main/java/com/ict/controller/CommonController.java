package com.ict.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

// 스프링 시큐리티 내장 디폴트 요소가 아닌 커스터마이징 요소를 쓸 때 사용하는 컨트롤러
@Log4j
@Controller
public class CommonController {
	
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		log.info("접근 거부 : " + auth);
		model.addAttribute("errorMessage", "접근 거부");	
		}

}
