package com.ict.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

// 테스트코드 기본셋팅
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {
	
	// 이 테스트코드 내에서는 BoardMapper의 테스트를 담당합니다
	// 그래서 먼저 선언하고 의존성 주입까지 마쳐야 해당 기능을 이 클래스 내에서 쓸 수 있습니다.
	@Autowired
	private BoardMapper mapper;
	
	@Test
	public void testGetList() {
		log.info(mapper.getList());
	}
}








