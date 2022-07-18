package com.ict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ict.mapper.BoardAttachMapper;
import com.ict.mapper.BoardMapper;
import com.ict.mapper.ReplyMapper;
import com.ict.persistent.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	private ReplyMapper mapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private BoardAttachMapper attachMapper;
	
	@Override
	public void addReply(ReplyVO vo) {
		mapper.create(vo);
		boardMapper.updateReplyCount(vo.getBno(), +1);
	}

	@Override
	public List<ReplyVO> listReply(Long bno) {
		return mapper.getList(bno);
	}

	@Override
	public void modifyReply(ReplyVO vo) {
		mapper.update(vo);
	}

	@Override
	public void removeReply(Long rno) {
		// 댓글 카운팅을 하기 위해서는 해당 댓글이 달려있던 bno에 대한 정보가 필요합니다.
		// 댓글이 삭제된 다음 bno를 얻어올 수는 없기때문에 제일 먼저 bno부터 얻어오도록 합니다
		Long bno = mapper.getBno(rno);
		// 댓글 삭제
		mapper.delete(rno);
		attachMapper.deleteAll(rno);
		// 댓글 삭제 후에 updateReplyCount를 실행해 해당 bno번 글 정보의 댓글개수를 1개 차감
		// 테스트삼아 댓글 삭제를 SQLDeveloper에서 햇을 경우, commit을 반드시 해 주신 다음
		// 해당 로직을 테스트해야 정상적으로 서버가 처리됩니다. commit을 안하면
		// pending상태가 유지됩니다.
		boardMapper.updateReplyCount(bno, -1);
	}
	
	
	
	
}
