package com.bit.springboard.service.impl;

import com.bit.springboard.common.FileUtils;
import com.bit.springboard.dao.FreeBoardDao;
import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.BoardFileDto;
import com.bit.springboard.dto.Criteria;
import com.bit.springboard.service.BoardService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
            // 업로드 폴더 지정
            String attachPath = "C:/tmp/upload/";

            File directory = new File(attachPath);

            // 업로드 폴더가 존재하지 않으면 폴더 생성
            if(!directory.exists()) {
                // 하위폴더도 생성하려면 mkdirs 메소드를 호출한다.
                directory.mkdirs();
            }

            Arrays.stream(uploadFiles).forEach(file -> {
                if(file.getOriginalFilename() != null && !file.getOriginalFilename().equals("")) {
                    BoardFileDto boardFileDto = FileUtils.parserFileInfo(file, attachPath);
                    boardFileDtoList.add(boardFileDto);
                }
            });
        }

        freeBoardDao.post(boardDto, boardFileDtoList);
    }

    @Override
    public void modify(BoardDto boardDto, MultipartFile[] uploadFiles, MultipartFile[] changeFiles, String originFiles) {
        // JSON String 형태의 originFiles를 List<BoardFileDto> 형태로 변환
        List<BoardFileDto> originFileList = new ArrayList<>();

        try {
            originFileList = new ObjectMapper().readValue(originFiles, new TypeReference<List<BoardFileDto>>() {});
        } catch(IOException ie) {
            System.out.println(ie.getMessage());
        }

        String attachPath = "C:/tmp/upload/";

        File directory = new File(attachPath);

        if(!directory.exists()) {
            directory.mkdirs();
        }

        // 추가, 수정, 삭제 되는 파일들의 목록을 담아줄 리스트
        List<BoardFileDto> uFileList = new ArrayList<>();

        // 수정, 삭제되는 파일들을 uFileList에 담기
        if(originFileList.size() > 0) {
            originFileList.forEach(boardFileDto -> {
                if (boardFileDto.getFilestatus().equals("U") && changeFiles != null) {
                    Arrays.stream(changeFiles).forEach(file -> {
                        if (boardFileDto.getNewfilename().equals(file.getOriginalFilename())) {
                            BoardFileDto updateBoardFileDto = FileUtils.parserFileInfo(file, attachPath);

                            updateBoardFileDto.setBoard_id(boardFileDto.getBoard_id());
                            updateBoardFileDto.setId(boardFileDto.getId());
                            updateBoardFileDto.setFilestatus("U");

                            uFileList.add(updateBoardFileDto);
                        }
                    });
                } else if (boardFileDto.getFilestatus().equals("D")) {
                    BoardFileDto deletBoardFileDto = new BoardFileDto();

                    deletBoardFileDto.setBoard_id(boardFileDto.getBoard_id());
                    deletBoardFileDto.setId(boardFileDto.getId());
                    deletBoardFileDto.setFilestatus("D");

                    uFileList.add(deletBoardFileDto);

                    // 실제 서버에서 파일 삭제
                    File deleteFile = new File(attachPath + boardFileDto.getFilename());

                    deleteFile.delete();
                }
            });
        }
        
        // 추가된 파일들 uFileList에 담기
        if(uploadFiles != null && uploadFiles.length > 0) {
            Arrays.stream(uploadFiles).forEach(file -> {
                if(!file.getOriginalFilename().equals("") && file.getOriginalFilename() != null) {
                    BoardFileDto postBoardFileDto = FileUtils.parserFileInfo(file, attachPath);

                    postBoardFileDto.setBoard_id(boardDto.getId());
                    postBoardFileDto.setFilestatus("I");

                    uFileList.add(postBoardFileDto);
                }
            });
        }

        boardDto.setModdate(LocalDateTime.now());
        freeBoardDao.modify(boardDto, uFileList);
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
        return freeBoardDao.getFreeBoardFileList(id);
    }


}
