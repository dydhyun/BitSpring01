package com.bit.springboard.service.impl;

import com.bit.springboard.dao.FreeBoardDao;
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
public class FreeBoardServiceImpl implements BoardService {
    private FreeBoardDao freeBoardDao;

    @Autowired
    public FreeBoardServiceImpl(FreeBoardDao freeBoardDao) {
        this.freeBoardDao = freeBoardDao;
    }


    @Override
    public void post(BoardDto boardDto) {
//        if(boardDto.getId() == 0) {
//            throw new RuntimeException("id에 0은 입력될 수 없습니다.");
//        }
        freeBoardDao.post(boardDto);
    }

    @Override
    public void modify(BoardDto boardDto) {
        boardDto.setModdate(LocalDateTime.now());
        freeBoardDao.modify(boardDto);
    }

    @Override
    public void delete(int id) {
        freeBoardDao.delete(id);
    }

    @Override
    public List<BoardDto> getBoardList(Map<String, String> searchMap, Criteria cri) {
        cri.setStartNum((cri.getPageNum() - 1) * cri.getAmount());

        // mybatis에서 parameter를 하나만 받을 수 있다.
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("search", searchMap);
        paramMap.put("cri", cri);

        return freeBoardDao.getBoardList(paramMap);
    }

    @Override
    public BoardDto getBoard(int id) {
        return freeBoardDao.getBoard(id);
    }

    @Override
    public void updateCnt(int id) {
        freeBoardDao.updateCnt(id);
    }

    @Override
    public int getBoardTotalCnt(Map<String, String> searchMap) {
        return freeBoardDao.getBoardTotalCnt(searchMap);
    }














}
