package com.bit.springboard.service;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.BoardFileDto;
import com.bit.springboard.dto.Criteria;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BoardService {
    void post(BoardDto boardDto, MultipartFile[] uploadFiles);

    void modify(BoardDto boardDto, MultipartFile[] uploadFiles, MultipartFile[] changeFiles, String originFiles);

    void delete(int id);

    List<BoardDto> getBoardList(Map<String, String> searchMap, Criteria cri);

    BoardDto getBoard(int id);

    void updateCnt(int id);

    int getBoardTotalCnt(Map<String, String> searchMap);

    List<BoardFileDto> getBoardFileList(int id);
}
