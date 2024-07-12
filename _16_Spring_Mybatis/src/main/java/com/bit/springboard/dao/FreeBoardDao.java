package com.bit.springboard.dao;

import com.bit.springboard.dto.BoardDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class FreeBoardDao {

    private SqlSessionTemplate mybatis;

    @Autowired
    public FreeBoardDao(SqlSessionTemplate sqlSessionTemplate){ this.mybatis = sqlSessionTemplate; }


    //***************************************************************************************************
    //***************************************************************************************************

    public void post(BoardDto boardDto){
        System.out.println("FreeBoardDao post 메서드 호출");

        mybatis.insert("FreeBoardDao.post", boardDto);
//        mybatis.insert(/*쿼리문의 호출은 Mapper.xml 파일의 nameSpace 값.쿼리문의 id값*/);

        System.out.println("FreeBoardDao post 메서드 실행종료");
    }

    public void modify(BoardDto boardDto){
        System.out.println("FreeBoardDao modify 메서드 호출");

        mybatis.update("FreeBoardDao.modify", boardDto);

        System.out.println("FreeBoardDao modify 메서드 호출종료");
    }

    public List<BoardDto> getBoardList(){
        System.out.println("FreeBoardDao getBoardList 메서드 호출");
        List<BoardDto> boardDtoList = new ArrayList<>();

        // List 를 가져올 때는 SqlSessionTemplate 의 SelectList 사용
        boardDtoList = mybatis.selectList("FreeBoardDao.getBoardList");

        System.out.println("FreeBoardDao getBoardList 메서드 호출종료");
        return boardDtoList;
    }


    public void delete(int id){
        System.out.println("FreeBoardDao delete 메서드 호출");

        mybatis.delete("FreeBoardDao.delete", id);

        System.out.println("FreeBoardDao delete 메서드 호출종료");
    }


    public BoardDto getBoard(int id){
        System.out.println("JDBCDaoSupport 클래스 상속 방식으로 JDBC Template 사용하기!!");
        System.out.println("FreeBoardDao getBoard 메서드 호출");
        BoardDto boardDto = new BoardDto();


        // 하나만 가져올 때 SqlSessionTemplate 의 selectOne 메소드 사용
        boardDto = mybatis.selectOne("FreeBoardDao.getBoard", id);

        System.out.println("FreeBoardDao getBoard 메서드 호출종료");
        return boardDto;
    }

}
