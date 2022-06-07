package com.ict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ict.persistent.BoardVO;
import com.ict.service.BoardService;

// bean container에 넣어보세요
@Controller
// 주소 /board가 붙도록 처리해주세요
@RequestMapping(value="/board")
public class BoardController {

	// 컨트롤러는 ???를 호출합니다. autowired로 주입해주세요.
	@Autowired
	private BoardService service;
	
	// /board/list 주소로 게시물 전체의 목록을 표현하는 컨트롤러를 만들어주세요
	// list.jsp로 연결되면 되고, getList()메서드로 가져온 전체 글 목록을
	// 포워딩해서 화면에 뿌려주면, 글번호, 글제목, 글쓴이, 날짜, 수정날짜를 화면에 출력해줍니다.
	@RequestMapping(value="/list")
	public String getBoardlist(Model model) {
		List<BoardVO> boardList = service.getList();
		model.addAttribute("boardList", boardList);
		return "/board/list";
	}
	
	// 글 번호를 입력받아서(주소창에서 ?bno=번호 형식으로) 해당 글의 디테일 페이지를 보여주는
	// 로직을 완성시켜주세요
	// /board/detail.jsp입니다
	// getBoardList처럼 포워딩해서 화면에 해당 글 하나에 대한 정보만 보여주면 됩니다,
	// service, mapper쪽에도 getDetail로 메서드를 
	
	@RequestMapping(value="/list/{bNum}")
	public String getBoardDetail(@PathVariable Long bNum, Model model) {
		BoardVO boardDetail = service.boardDetail(bNum);
		model.addAttribute("boardDetail", boardDetail);
		return "/board/detail";
	}
}
