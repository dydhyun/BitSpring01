package com.bit.springboard.service.impl;

import com.bit.springboard.dao.NoticeDao;
import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.Criteria;
import com.bit.springboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeServiceImpl implements BoardService {
    private NoticeDao noticeDao;

    @Autowired
    public NoticeServiceImpl(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

    @Override
    public void post(BoardDto boardDto) {
        noticeDao.post(boardDto);
    }

    @Override
    public void modify(BoardDto boardDto) {
        boardDto.setModdate(LocalDateTime.now());
        noticeDao.modify(boardDto);
    }

    @Override
    public void delete(int id) {
        noticeDao.delete(id);
    }

    @Override
    public List<BoardDto> getBoardList(Map<String, String> searchMap, Criteria criteria) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("search", searchMap);
        paramMap.put("cri", criteria);


        return noticeDao.getNoticeList(paramMap);
    }

    @Override
    public BoardDto getBoard(int id) {
        return noticeDao.getNotice(id);
    }

    @Override
    public void cntUp(BoardDto boardDto) {
        System.out.println("impl에서 "+boardDto.getCnt());
//        boardDto.setCnt(boardDto.getCnt()+1);
        noticeDao.cntUp(boardDto);
    }

    @Override
    public int getBoardTotalCnt(Map<String, String> searchMap) {

        return noticeDao.getBoardTotalCnt(searchMap);
    }
}
