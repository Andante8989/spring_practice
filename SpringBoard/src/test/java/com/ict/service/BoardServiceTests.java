package com.ict.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ict.persistent.BoardVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {
	
	@Autowired
	private BoardService service;
	
	//@Test
	public void getList() {
		//log.info(service.getList());
	}
	
	// insert 도 테스트 한 번 해주세요.
	//@Test
	public void insertList() {
		BoardVO vo = new BoardVO();
		vo.setTitle("화요일 제목");
		vo.setContent("화요일 내용");
		vo.setWriter("새로운 작성자");
		service.insert(vo);
	}
	
	//@Test
	public void deleteList() {
		service.delete(5L);
	}
	
	//@Test
	public void updateList() {
		BoardVO vo = new BoardVO();
		vo.setTitle("7일수정 제목");
		vo.setContent("7일 수정 내용");
		vo.setWriter("7일 수정하는사람");
		vo.setBno(2L);
		service.update(vo);
	}
	
	//@Test
	public void boardDetail() {
		log.info(service.boardDetail(2L));
	}
	
}
