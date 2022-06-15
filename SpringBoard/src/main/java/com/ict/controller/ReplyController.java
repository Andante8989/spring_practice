package com.ict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.persistent.ReplyVO;
import com.ict.service.ReplyService;

@RestController
@RequestMapping("/replies")
public class ReplyController {
	
	@Autowired
	private ReplyService service;
	
	// consumes는 이 메서드의 파라미터를 넘겨줄 때 어떤 형식으로 넘겨줄지
	// 를 설정하는데, 기본적으로 rest방식에서는 json을 사용합니다.
	// produces는 입력받은 데이터를 토대로 로직을 실행한 다음
	// 사용자에게 결과로 보여줄 데이터의 형식을 나타냅니다
	// jason-databind를 추가해야 정상적으로 작동합니다.
	@PostMapping(value="", consumes="application/json",
							produces= {MediaType.TEXT_PLAIN_VALUE})
	// produces에 TEXT_PLAIN_VALUE를 줬으므로, 결과코드와 문자열을 넘기도록 합니다.
	public ResponseEntity<String> register(
											// rest컨트롤러에서 받는 파라미터 앞에
											// @RequestBody 어노테이션이 붙어야
											// consumes와 연결됩니다
											@RequestBody ReplyVO vo){
		
		// 깡통 Entity를 먼저 생성
		ResponseEntity<String> entity = null;
		
		try {
			// 먼저 글쓰기 로직 실행 후 에러가 없다면...
			service.addReply(vo);
			entity = new ResponseEntity<String>("Success", HttpStatus.OK);
		} catch(Exception e) {
			// catch로 넘어왔다는 것은 글쓰기에 문제가 생겼다는 뜻이므로
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		// 위의 try블럭이나 catch블럭에서 생성된 entity를 리턴
		return entity;
	}
	
	
}
