package com.ict.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
	
	@Select("SELECT SYSDATE FROM dual")
	public String getTime();
	
	// 쿼리문 없이 메서드 선언만(실행문도 없음)
	public String getTime2();
	
}
