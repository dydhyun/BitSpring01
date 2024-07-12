package com.bit.springboard.service.impl;

import com.bit.springboard.dao.NoticeBoardDao;
import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeBoardServiceImpl implements BoardService {
    private NoticeBoardDao noticeBoardDao;

    @Autowired
    public NoticeBoardServiceImpl(NoticeBoardDao noticeBoardDao) {
        this.noticeBoardDao = noticeBoardDao;
    }

    @Override
    public void post(BoardDto boardDto) {
        noticeBoardDao.noticePost(boardDto);
    }

    @Override
    public void modify(BoardDto boardDto) {
        noticeBoardDao.noticeModify(boardDto);
    }

    @Override
    public void delete(int id) {
        noticeBoardDao.delete(id);
    }

    @Override
    public List<BoardDto> getBoardList() {
        return noticeBoardDao.getBoardList();
    }

    @Override
    public BoardDto getBoard(int id) {
        return noticeBoardDao.getBoard(id);
    }
}
