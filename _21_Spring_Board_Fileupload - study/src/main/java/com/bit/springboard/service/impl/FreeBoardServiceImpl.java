package com.bit.springboard.service.impl;

import com.bit.springboard.common.FileUtils;
import com.bit.springboard.dao.FreeBoardDao;
import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.BoardFileDto;
import com.bit.springboard.dto.Criteria;
import com.bit.springboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FreeBoardServiceImpl implements BoardService {
    private FreeBoardDao freeBoardDao;

    @Autowired
    public FreeBoardServiceImpl(FreeBoardDao freeBoardDao) {
        this.freeBoardDao = freeBoardDao;
    }


    @Override
    public void post(BoardDto boardDto, MultipartFile[] uploadFiles) {
        List<BoardFileDto> boardFileDtoList = new ArrayList<>();

        if(uploadFiles != null && uploadFiles.length > 0) {
            // 업로드 폴더 지정하기
            String attachPath = "C://tmp/upload/";

            File directory = new File(attachPath);
            // 업로드 폴더 존재않으면 폴더 생성하기
            if(!directory.exists()){
                // 하위폴더까지 새로 만들기위해 mkdir() 대신 mkdirs() 를 사용
                directory.mkdirs();
            }


            Arrays.stream(uploadFiles).forEach(file -> {
                // 내용은 FileUtils 로 옮겨짐

                // 가끔 파일첨부를 안해도 빈칸으로 넘어오는경우를 막기 위한 조건문.
                if(file.getOriginalFilename() != null && !file.getOriginalFilename().equals("")){
                    BoardFileDto boardFileDto = FileUtils.parseFileInfo(file, attachPath);
                    boardFileDtoList.add(boardFileDto);

                }

            });
        }

        freeBoardDao.post(boardDto, boardFileDtoList);
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


    @Override
    public List<BoardFileDto> getBoardFileList(int id) {
        System.out.println(freeBoardDao.getFreeBoardFileList(id));
        return freeBoardDao.getFreeBoardFileList(id);
    }


}
