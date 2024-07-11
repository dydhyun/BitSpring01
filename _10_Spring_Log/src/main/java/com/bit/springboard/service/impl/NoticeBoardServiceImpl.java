package com.bit.springboard.service.impl;

import com.bit.springboard.common.LogConsoleV2;
import com.bit.springboard.dao.NoticeBoardDao;
import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeBoardServiceImpl implements BoardService {
    private NoticeBoardDao noticeBoardDao;
    private LogConsoleV2 logConsole;

    @Autowired
    public NoticeBoardServiceImpl(NoticeBoardDao noticeBoardDao) {
        this.noticeBoardDao = noticeBoardDao;
        this.logConsole = new LogConsoleV2();
    }

    @Override
    public void post(BoardDto boardDto) {
        logConsole.consoleLogPlus();
        noticeBoardDao.noticePost(boardDto);
    }

    @Override
    public void modify(BoardDto boardDto) {
        logConsole.consoleLogPlus();
        noticeBoardDao.noticeModify(boardDto);
    }

    @Override
    public void delete(int id) {
        logConsole.consoleLogPlus();
        noticeBoardDao.delete(id);
    }

    @Override
    public List<BoardDto> getBoardList() {
        logConsole.consoleLogPlus();
        return noticeBoardDao.getBoardList();
    }

    @Override
    public BoardDto getBoard(int id) {
        logConsole.consoleLogPlus();
        return noticeBoardDao.getBoard(id);
    }
}
