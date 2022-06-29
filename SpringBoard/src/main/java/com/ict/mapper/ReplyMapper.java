package com.ict.mapper;

import java.util.List;

import com.ict.persistent.ReplyVO;

public interface ReplyMapper {

	public List<ReplyVO> getList(Long bno);
	
	public void create(ReplyVO vo);
	
	public void update(ReplyVO vo);
	
	public void delete(Long rno);
	
	// 댓글번호 rno 입력시 해당 댓글이 속한 글번호 bno를 리턴받는 쿼리문
	public Long getBno(Long rno);
}
