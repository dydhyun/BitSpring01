package com.bit.springboard.service.impl;

import com.bit.springboard.common.FileUtils;
import com.bit.springboard.dao.NoticeDao;
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
public class NoticeServiceImpl implements BoardService {
    private NoticeDao noticeDao;

    @Autowired
    public NoticeServiceImpl(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

    @Override
    public void post(BoardDto boardDto, MultipartFile[] uploadFiles) {
        List<BoardFileDto> boardFileDtoList = new ArrayList<>();

        if(uploadFiles != null && uploadFiles.length > 0){
            String attachPath = "C://tmp/upload/";

            File directory = new File(attachPath);
            // 위 경로가 존재하지 않으면,
            // 있으면 참, 없으면 거짓 : !false -> 새 폴더 생성하기
            if (!directory.exists()){
                directory.mkdirs();
            }

            Arrays.stream(uploadFiles).forEach(file ->{

                if (file.getOriginalFilename() != null && !file.getOriginalFilename().equals("")) {
                    BoardFileDto boardFileDto = FileUtils.parseFileInfo(file, attachPath);
                    boardFileDtoList.add(boardFileDto);
                }
            });

        }

        noticeDao.post(boardDto, boardFileDtoList);
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
    public List<BoardDto> getBoardList(Map<String, String> searchMap, Criteria cri) {
        cri.setStartNum((cri.getPageNum() - 1) * cri.getAmount());

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("search", searchMap);
        paramMap.put("cri", cri);

        return noticeDao.getNoticeList(paramMap);
    }

    @Override
    public BoardDto getBoard(int id) {
        return noticeDao.getNotice(id);
    }

    @Override
    public void updateCnt(int id) {
        noticeDao.updateCnt(id);
    }

    @Override
    public int getBoardTotalCnt(Map<String, String> searchMap) {
        return noticeDao.getBoardTotalCnt(searchMap);
    }


    @Override
    public List<BoardFileDto> getBoardFileList(int id) {
        return List.of();
    }

}
