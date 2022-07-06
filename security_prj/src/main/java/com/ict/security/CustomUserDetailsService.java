package com.ict.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ict.domain.CustomUser;
import com.ict.domain.MemberVO;
import com.ict.mapper.MemberMapper;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberMapper mapper;
	
	// String username에는 해당 테이블의 username 컬럼의 데이터가 자동으로 매칭됩니다
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.warn("유저이름 : " + username);
		
		// 특정 유저 정보를 가져오기	
		MemberVO vo = mapper.read(username);
		
		log.info("확인된 유저이름으로 얻어온 정보 : " + vo);
		
		return vo == null? null : new CustomUser(vo);
	}

}
