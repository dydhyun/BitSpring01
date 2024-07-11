package com.bit.springboard.service.impl;

import com.bit.springboard.common.LogConsole;
import com.bit.springboard.dao.FreeBoardDao;
import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.service.BoardService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeBoardServiceImpl implements BoardService {
    private static final Log log = LogFactory.getLog(FreeBoardServiceImpl.class);
    private FreeBoardDao freeBoardDao;
    private LogConsole logConsole;

    @Autowired
    public FreeBoardServiceImpl(FreeBoardDao freeBoardDao){
        this.freeBoardDao = freeBoardDao;
        this.logConsole = new LogConsole();
    }

    @Override
    public void post(BoardDto boardDto) {
        logConsole.consoleLog();
        freeBoardDao.post(boardDto);
    }

    @Override
    public void modify(BoardDto boardDto) {
        logConsole.consoleLog();
        freeBoardDao.modify(boardDto);
    }

    @Override
    public void delete(int id) {
        logConsole.consoleLog();
        freeBoardDao.delete(id);
    }

    @Override
    public List<BoardDto> getBoardList() {
        logConsole.consoleLog();
        return freeBoardDao.getBoardList();
    }

    @Override
    public BoardDto getBoard(int id) {
        logConsole.consoleLog();
        return freeBoardDao.getBoard(id);
    }


}
