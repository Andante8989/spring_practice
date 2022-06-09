package com.ict.service;

import java.util.List;

import com.ict.persistent.BoardVO;
import com.ict.persistent.Criteria;

public interface BoardService {
	
	// Service 는 원래 하나의 동작(사용자 기준)을 선언하고
	// Mapper는 하나의 호출(쿼리문)을 선언하는 용도입니다
	// 그런데 기본적인 로직은 하나의 동작이 하나의 쿼리문으로
	// 현재는 그냥 로직별로 하나씩 메서드를 만들어주시면 됩니다.
	// 단, 나중에 사용자에게는 글삭제 이지만, 백로직에서는 글과 댓글이 모두 삭제된다던지... 하는 식으로
	// 사용자 기준의 하나의 동작과 로직개념적 하나의 동작이 일치하지 않을수도 있으니 주의해야합니다.
	public List<BoardVO> getList(Criteria cri);
	
	public void insert(BoardVO vo);

	public void delete(Long bno);
	
	public void update(BoardVO vo);
	
	public BoardVO boardDetail(Long bno);
}
