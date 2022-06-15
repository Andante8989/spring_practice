package com.ict.mapper;

import java.util.List;

import com.ict.persistent.ReplyVO;

public interface ReplyMapper {

	public List<ReplyVO> getList(Long bno);
	
	public void create(ReplyVO vo);
	
	public void update(ReplyVO vo);
	
	public void delete(Long rno);
}
