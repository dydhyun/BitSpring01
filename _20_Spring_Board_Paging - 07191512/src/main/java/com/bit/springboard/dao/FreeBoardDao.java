package com.bit.springboard.dao;

import com.bit.springboard.dto.BoardDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// JDBC Template 사용방식 2
// JdbcTemplate을 필드로 선언하고 의존성을 주입받아서 사용하는 방식
@Repository
public class FreeBoardDao {
    private SqlSessionTemplate mybatis;

    @Autowired
    public FreeBoardDao(SqlSessionTemplate sqlSessionTemplate) {
        this.mybatis = sqlSessionTemplate;
    }

    private final String GET_BOARD_LIST = "SELECT F.ID" +
            "                                   , F.TITLE" +
            "                                   , F.CONTENT" +
            "                                   , F.WRITER_ID" +
            "                                   , M.NICKNAME" +
            "                                   , F.REGDATE" +
            "                                   , F.MODDATE" +
            "                                   , F.CNT" +
            "                                  FROM FREEBOARD F" +
            "                                  JOIN MEMBER M" +
            "                                    ON F.WRITER_ID = M.ID";

    // 게시글 삭제
    private final String DELETE = "DELETE FROM FREEBOARD" +
            "                           WHERE ID = ?";

    // 특정 id의 게시글 하나만 조회
    private final String GET_BOARD = "SELECT F.ID" +
            "                                   , F.TITLE" +
            "                                   , F.CONTENT" +
            "                                   , F.WRITER_ID" +
            "                                   , M.NICKNAME" +
            "                                   , F.REGDATE" +
            "                                   , F.MODDATE" +
            "                                   , F.CNT" +
            "                                  FROM FREEBOARD F" +
            "                                  JOIN MEMBER M" +
            "                                    ON F.WRITER_ID = M.ID" +
            "                                  WHERE F.ID = ?";

    public void post(BoardDto boardDto) {
        System.out.println("FreeBoardDao의 post 메소드 실행");

        mybatis.insert(/*쿼리문의 호출은 Mapper.xml 파일의 namespace값.쿼리문의 id*/"FreeBoardDao.post", boardDto);

        System.out.println("FreeBoardDao의 post 메소드 실행 종료");
    }

    public void modify(BoardDto boardDto) {
        System.out.println("FreeBoardDao의 modify 메소드 실행");

        mybatis.update("FreeBoardDao.modify", boardDto);

        System.out.println("FreeBoardDao의 modify 메소드 실행 종료");
    }

    public List<BoardDto> getBoardList(Map<String, Object> paramMap) {
        System.out.println("FreeBoardDao의 getBoardList 메소드 실행");

        List<BoardDto> boardDtoList = new ArrayList<>();

        // SqlSessionTemplate의 selectList메소드 사용
        boardDtoList = mybatis.selectList("FreeBoardDao.getBoardList", paramMap);

        System.out.println("FreeBoardDao의 getBoardList 메소드 실행 종료");
        return boardDtoList;
    }

    public void delete(int id) {
        System.out.println("FreeBoardDao의 delete 메소드 실행");

        mybatis.delete("FreeBoardDao.delete", id);

        System.out.println("FreeBoardDao의 delete 메소드 실행 종료");
    }
    
    public BoardDto getBoard(int id) {
        System.out.println("FreeBoardDao의 getBoard 메소드 실행");

        BoardDto boardDto = new BoardDto();

        // SqlSessionTemplate의 selectOne메소드 사용
        boardDto = mybatis.selectOne("FreeBoardDao.getBoard", id);

        System.out.println("FreeBoardDao의 getBoard 메소드 실행 종료");
        return boardDto;
    }

    public void updateCnt(int id) {
        mybatis.update("FreeBoardDao.updateCnt", id);
    }

    public int getBoardTotalCnt(Map<String, String> searchMap) {
        return mybatis.selectOne("FreeBoardDao.getBoardTotalCnt", searchMap);
    }
}
