package com.ict.domain;

import java.util.List;

import lombok.Data;

@Data
public class MemberVO {
	
	private String userid;
	private String userpw;
	private String username;
	private boolean enabled;
	
	private List<AuthVO> authList;
}
