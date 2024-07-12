package com.bit.springboard.service;

import com.bit.springboard.dto.BoardDto;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.time.LocalDateTime;

public class FreeBoardServiceRun {
    public static void main(String[] args) {
        AbstractApplicationContext factory = new GenericXmlApplicationContext("root-context.xml");

        BoardService boardService = factory.getBean("freeBoardServiceImpl", BoardService.class);

        BoardDto boardDto = new BoardDto();

        boardDto.setId(60);
        boardDto.setTitle("자유게시글  2");
        boardDto.setContent("자유게시글 3 번 입니다~");
        // writer_id 는 member 테이블의 id 컬럼을 foreign key 로 가져오기 때문에,
        // member 테이블에 존재하는 id 값만 입력할 수 있다.
        boardDto.setWRITER_ID(5);

        boardService.post(boardDto);

        // 게시글 수정
        BoardDto modifyBoardDto = new BoardDto();

        modifyBoardDto.setId(18);
        modifyBoardDto.setTitle("자유게시글1 수정");
        modifyBoardDto.setContent("자유게시글 1 번 입니다!--수정됨");
        modifyBoardDto.setModdate(LocalDateTime.now());

        boardService.modify(modifyBoardDto);

        // 게시글 삭제
//        boardService.delete(17);

        // 게시글 목록 조회
        boardService.getBoardList().forEach(board -> {
            System.out.println(board);
        });

        // 특정 id 의 게시글 하나 조회
        System.out.println(boardService.getBoard(25));

        factory.close();
    }
}
