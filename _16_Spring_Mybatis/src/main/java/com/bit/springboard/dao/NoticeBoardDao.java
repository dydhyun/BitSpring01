package com.bit.springboard.dao;

import com.bit.springboard.dto.BoardDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NoticeBoardDao {

    private SqlSessionTemplate mybatis;

    @Autowired
    public NoticeBoardDao(SqlSessionTemplate sqlSessionTemplate) { this.mybatis = sqlSessionTemplate; }


//************************************************************

    public void noticePost(BoardDto boardDto){
        System.out.println("NoticeBoardDao - Post 메서드 실행");

        mybatis.insert("NoticeBoardDao.Post", boardDto);

        System.out.println("NoticeBoardDao - post 메서드 종료");
    }


    public void noticeModify(BoardDto boardDto){
        System.out.println("NoticeBoadrDao - Modify 메서드 호출");

        mybatis.update("NoticeBoardDao.Modify", boardDto);

        System.out.println("NoticeBoadrDao - modify 메서드 종료");
    }


    public void delete(int id){
        System.out.println("NoticeBoardDao - delete 메서드 호출");

        mybatis.delete("NoticeBoardDao.DELETE", id);
        // 아이디를 DELETE 로 주었다.

//        mybatis.delete("NoticeBoardDao",id); 아이디 안줘보기?

        System.out.println("NoticeBoardDao - delete 메서드 종료");
    }


    public List<BoardDto> getBoardList(){
        System.out.println("NoticeBoardDao - getBoardList 메서드 호출");
        List<BoardDto> boardDtolist = new ArrayList<>();

        boardDtolist = mybatis.selectList("NoticeBoardDao.getBoardList");

        System.out.println("NoticeBoardDao - getBoardList 메서드 종료");
        return boardDtolist;
    }


    public BoardDto getBoard(int id){
        System.out.println("NoticeBoardDao - getBoard 메서드 실행");

        BoardDto boardDto = mybatis.selectOne("NoticeBoardDao.getBoard", id);

        System.out.println("NoticeBoardDao - getBoard 메서드 종료");
        return boardDto;
    }


}
