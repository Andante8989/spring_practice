package com.ict.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/detail")
	public String getBoardDetail(Long bno, Model model) {
		BoardVO board = service.boardDetail(bno);
		model.addAttribute("board", board);
		return "/board/detail";
	}
	
	// 글 쓰기는 말 그대로 글을 써주는 로직인데
	// 폼으로 연결되는 페이지가 하나 있어야하고
	// 그다음 폼에서 날려주는 로직을 처리해주는 페이지가 하나 더 있어야 합니다.
	
	@GetMapping("/insert")
	public String insertForm() {
		return "/board/insertForm";
	}
	
	// post방식으로 /insert로 들어오는 자료를 받아  콘솔에 찍어주세요
	@PostMapping("/insert")
	public String boardInsert(BoardVO board) {
		service.insert(board);
		// redirect를 사용해야 전체 글 목록을 로딩해온 다음 화면을 열어줍니다
		// 스프링 컨트롤러에서 리다이렉트를 할 때는
		// 목적주소 앞에 redirect: 를 추가로 붙입니다.
		return "redirect:/board/list";
	}
	
	// 글삭제 post방식으로 처리하도록 합니다
	@PostMapping("/delete")
	public String deleteBoard(Long bno) {
		// 삭제 후 리스트로 돌아갈 수 있도록 내부 로직을 만들어주시고
		// 디테일 페이지에 삭제요청을 넣을 수 있는 폼을 만들어주세요
		service.delete(bno);
		return "redirect:/board/list";
	}
	
	// 글 수정 요청도 post로 받습니다
	@PostMapping("/updateForm")
	public String updateBoardForm(Long bno, Model model) {
		// 해당 bno의 글 정보만 뽑아서 저장한 다음
		// 포워딩을 이용해 updateForm.jsp에 보내줍니다.
		BoardVO board = service.boardDetail(bno);
		model.addAttribute("board", board);
		return "/board/updateForm";
	}
	
	@PostMapping("/update")
	public String updateBoard(BoardVO board, Long bno, Model model) {
		service.update(board);
		model.addAttribute("bno", bno);
		return "redirect:/board/detail";
	}
}
