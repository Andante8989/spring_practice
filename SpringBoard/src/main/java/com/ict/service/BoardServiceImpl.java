package com.ict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ict.mapper.BoardAttachMapper;
import com.ict.mapper.BoardMapper;
import com.ict.mapper.ReplyMapper;
import com.ict.persistent.BoardAttachVO;
import com.ict.persistent.BoardVO;
import com.ict.persistent.Criteria;
import com.ict.persistent.SearchCriteria;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper mapper;
	
	@Autowired
	private BoardAttachMapper attachMapper;
	
	@Autowired
	private ReplyMapper replyMapper;

	@Override
	public List<BoardVO> getList(SearchCriteria cri) {
		return mapper.getList(cri);
	}

	@Transactional
	@Override
	public void insert(BoardVO vo) {
		log.info("글 및 첨부파일 insert");
		log.info(vo);
		mapper.insert(vo);
		if(vo.getAttachList() == null || vo.getAttachList().size() <= 0) {
			return;
		}
		vo.getAttachList().forEach(attach -> {
			attach.setBno(vo.getBno());
			attachMapper.insert(attach);
		});
	}
	
	@Transactional
	@Override
	public void delete(Long bno) {
		replyMapper.deleteAll(bno);
		attachMapper.deleteAll(bno);
		mapper.delete(bno);
	}

	@Override
	public void update(BoardVO vo) {
		mapper.update(vo);
	}

	@Override
	public BoardVO boardDetail(Long bno) {
		return mapper.boardDetail(bno);
	}

	@Override
	public Long getBoardCount(SearchCriteria cri) {
		return mapper.getBoardCount(cri);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		return attachMapper.findByBno(bno);
	}

	

	
	

	
	
	
	
	
}
