package com.bit.springboard.dao;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.service.BoardRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

// JDBC Template 사용방식 1
// JdbcDaoSupport 클래스를 상속받아 사용하는 방식
@Repository
public class FreeBoardDao extends JdbcDaoSupport {

    // JdbcDaoSupport 상속 방식
    @Autowired
    public void setSuperDataSource(DataSource dataSource){
        super.setDataSource(dataSource);
    }

    // 게시글 등록 쿼리
    private final String POST = "INSERT INTO FreeBoard(ID, TITLE, CONTENT, WRITER_ID) VALUES(?,?,?,?)";

    // 게시글 수정 쿼리
    private final String MODIFY = "UPDATE FreeBoard SET TITLE = ?, CONTENT = ?, MODDATE = ? WHERE ID = ?";


    private final String GET_BOARD_LIST = "SELECT freeboard.ID, freeboard.TITLE, freeboard.CONTENT,\n" +
            "       freeboard.WRITER_ID, member.NICKNAME, freeboard.REGDATE, freeboard.MODDATE, freeboard.CNT\n" +
            "FROM FreeBoard\n" +
            "join member\n" +
            "on freeboard.WRITER_ID = member.ID";

    // 게시글 삭제
    private final String DELETE = "DLELTE FROM FreeBoard WHERE ID = ?";


    // 특정 게시글 하나 조회
    private final String GET_BOARD = "SELECT freeboard.ID, freeboard.TITLE, freeboard.CONTENT, " +
            "       freeboard.WRITER_ID, member.NICKNAME, freeboard.REGDATE, freeboard.MODDATE, freeboard.CNT " +
            "FROM FreeBoard " +
            "join member " +
            "on freeboard.WRITER_ID = member.ID WHERE FreeBoard.id = ?";


    //***************************************************************************************************
    //***************************************************************************************************

    public void post(BoardDto boardDto){
        System.out.println("FreeBoardDao post 메서드 호출");

        getJdbcTemplate().update(POST,boardDto.getId(), boardDto.getTitle(), boardDto.getContent(), boardDto.getWRITER_ID());

        System.out.println("FreeBoardDao post 메서드 실행종료");
    }

    public void modify(BoardDto boardDto){
        System.out.println("FreeBoardDao modify 메서드 호출");

        getJdbcTemplate().update(MODIFY, boardDto.getTitle(), boardDto.getContent(), boardDto.getModdate().toString(), boardDto.getId());

        System.out.println("FreeBoardDao modify 메서드 호출종료");
    }

    public List<BoardDto> getBoardList(){
        System.out.println("FreeBoardDao getBoardList 메서드 호출");
        List<BoardDto> boardDtoList = new ArrayList<>();

        boardDtoList = getJdbcTemplate().query(GET_BOARD_LIST, new BoardRowMapper());
        // ResultSet 에 담아 반환하였듯, new BoardRowMapper() 에 담아 리턴하도록 한다.

        System.out.println("FreeBoardDao getBoardList 메서드 호출종료");
        return boardDtoList;
    }


    public void delete(int id){
        System.out.println("FreeBoardDao delete 메서드 호출");

        getJdbcTemplate().update(DELETE, id);

        System.out.println("FreeBoardDao delete 메서드 호출종료");
    }


    public BoardDto getBoard(int id){
        System.out.println("JDBCDaoSupport 클래스 상속 방식으로 JDBC Template 사용하기!!");
        System.out.println("FreeBoardDao getBoard 메서드 호출");
        BoardDto boardDto = new BoardDto();

        // queryForObject 의 두 번째 매개변수는 Object 배열 형태여야 한다.
        Object[] args = {id};

        boardDto = getJdbcTemplate().queryForObject(GET_BOARD, args, new BoardRowMapper());
        // 한 행만 가져올 때는 queryForObject() 메소드를 사용해야 된다.
        // ResultSet 에 담아 반환하였듯, new BoardRowMapper() 에 담아 리턴하도록 한다.

        System.out.println("FreeBoardDao getBoard 메서드 호출종료");
        return boardDto;
    }

}
