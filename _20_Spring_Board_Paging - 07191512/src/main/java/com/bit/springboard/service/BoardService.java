package com.bit.springboard.service;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.Criteria;

import java.util.List;
import java.util.Map;

public interface BoardService {
    void post(BoardDto boardDto);

    void modify(BoardDto boardDto);

    void delete(int id);

    List<BoardDto> getBoardList(Map<String, String> searchMap, Criteria cri);

    BoardDto getBoard(int id);

    void updateCnt(int id);

    int getBoardTotalCnt(Map<String, String> searchMap);
}
