package com.ict.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.mapper.MemberMapper;
import com.ict.persistent.MemberVO;

@Service
public class SecurityServiceImpl implements SecurityService {
	
	@Autowired
	private MemberMapper mapper;

	@Override
	public void insertMember(MemberVO vo) {
		mapper.insertMemberTbl(vo);;
		mapper.insertMemberAuth(vo);
		
		
	}

}
