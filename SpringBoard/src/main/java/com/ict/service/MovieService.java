package com.ict.service;

import java.util.List;

import com.ict.persistent.MovieVO;

public interface MovieService {

	public List<MovieVO> getList();
	
	public List<MovieVO> topMovie();
}
