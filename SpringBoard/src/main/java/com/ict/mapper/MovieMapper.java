package com.ict.mapper;

import java.util.List;

import com.ict.persistent.MemberVO;
import com.ict.persistent.MovieVO;

public interface MovieMapper {

	public List<MovieVO> getList();

	public List<MovieVO> topMovie();
	
}
