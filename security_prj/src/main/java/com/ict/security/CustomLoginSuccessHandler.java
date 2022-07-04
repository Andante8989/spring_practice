package com.ict.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	    // 로그인 성공시 어떤 권한인지 체크하기 위해 부여받은 권한(들) 불러오기
		// ROLE_ADMIN의 경우는 ROLE_MEMBER가 함께 부여되기 때문에
		// 권한이 한 계정에 여럿 주어질수도 있음.
		log.warn("로그인 성공");
		// 권한 목록을 모두 담을 수 있는 ArrayList 생성
		List<String> roleList = new ArrayList<>();
		
		// 권한 목록을 getAuthorities()로 가져와 하나하나 향상된 for문으로 반복시킵니다
		for (GrantedAuthority role : authentication.getAuthorities()) {
			roleList.add(role.getAuthority());
		}
		// roleList에 담긴 권한 중 가장 높은 권한 페이지로 강제로 이동하도록 처리
		log.warn("부여받은 권한들 : " + roleList);
		if(roleList.contains("ROLE_ADMIN")) {
			response.sendRedirect("/secu/admin");
			return;
		}
		if(roleList.contains("ROLE_MEMBER")) {
			response.sendRedirect("/secu/member");
			return;
		}
		
		response.sendRedirect("/");
	}

}
