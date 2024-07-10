package com.bit.springboard.service;

import com.bit.springboard.dto.BoardDto;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.time.LocalDateTime;

public class NoticeBoardServiceRun {
    public static void main(String[] args) {
        AbstractApplicationContext ac = new GenericXmlApplicationContext("root-context.xml");

        BoardService boardService = ac.getBean("noticeBoardServiceImpl", BoardService.class);

        BoardDto boardDto = new BoardDto();

//        boardDto.setTitle("공지게시판 1-2호");
//        boardDto.setContent("공지 글2 입니다.");
//        boardDto.setWRITER_ID(5);
//
//        boardService.post(boardDto);

        boardDto.setId(1);
        boardDto.setTitle("공지게시판 1-4호 - 수정함");
        boardDto.setContent("공지 글 4 입니다. - 수정함");
        boardDto.setModdate(LocalDateTime.now());

        boardService.modify(boardDto);

        boardService.delete(1);

        boardService.getBoardList().forEach(board -> {
            System.out.println(board);
        });

        System.out.println(boardService.getBoard(3));
        ac.close();


    }
}
