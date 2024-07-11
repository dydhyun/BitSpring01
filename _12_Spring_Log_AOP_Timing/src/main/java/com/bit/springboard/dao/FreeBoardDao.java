package com.bit.springboard.dao;

import com.bit.springboard.common.JDBCUtil;
import com.bit.springboard.dto.BoardDto;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FreeBoardDao {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    // 게시글 등록 쿼리
    private final String POST = "INSERT INTO FreeBoard(TITLE, CONTENT, WRITER_ID) VALUES(?,?,?)";

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

    public void post(BoardDto boardDto){
        System.out.println("FreeBoardDao post 메서드 호출");
        try {
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(POST);

            stmt.setString(1, boardDto.getTitle());
            stmt.setString(2, boardDto.getContent());
            stmt.setInt(3, boardDto.getWRITER_ID());

            stmt.executeUpdate();

        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            JDBCUtil.close(conn, stmt);
        }

        System.out.println("FreeBoardDao post 메서드 실행종료");
    }


    public void modify(BoardDto boardDto){
        System.out.println("FreeBoardDao modify 메서드 호출");
        try{
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(MODIFY);

            stmt.setString(1, boardDto.getTitle());
            stmt.setString(2, boardDto.getContent());
            stmt.setString(3, boardDto.getModdate().toString());
            // String 으로 넘겨주어도 DB 에서 자동으로 Date 변환함
            stmt.setInt(4, boardDto.getId());

            stmt.executeUpdate();

        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            JDBCUtil.close(conn, stmt);
        }
        System.out.println("FreeBoardDao modify 메서드 호출종료");
    }

    public List<BoardDto> getBoardList(){
        System.out.println("FreeBoardDao getBoardList 메서드 호출");
        List<BoardDto> boardDtoList = new ArrayList<>();
        try {
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(GET_BOARD_LIST);

            rs = stmt.executeQuery();// select 는 executeQuery() 로 바로 실행.

            while (rs.next()){
                BoardDto boardDto = new BoardDto();
                boardDto.setId(rs.getInt("ID"));
                boardDto.setTitle(rs.getString("TITLE"));
                boardDto.setContent(rs.getString("CONTENT"));
                boardDto.setWRITER_ID(rs.getInt("WRITER_ID"));
                boardDto.setNickname(rs.getString("NICKNAME"));
                // java.sql.Data 타입 -> LocalDateTime 타입으로 변환.
                boardDto.setRegdate(rs.getTimestamp("REGDATE").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
                boardDto.setModdate(rs.getTimestamp("MODDATE").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
                boardDto.setCnt(rs.getInt("CNT"));
                boardDtoList.add(boardDto);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            JDBCUtil.close(conn, stmt, rs);
        }
        System.out.println("FreeBoardDao getBoardList 메서드 호출종료");
        return boardDtoList;
    }


    public void delete(int id){
        System.out.println("FreeBoardDao delete 메서드 호출");
        try{
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(DELETE);

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            JDBCUtil.close(conn, stmt);
        }
        System.out.println("FreeBoardDao delete 메서드 호출종료");
    }


    public BoardDto getBoard(int id){
        System.out.println("FreeBoardDao getBoard 메서드 호출");
        BoardDto boardDto = new BoardDto();
        try{
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(GET_BOARD);

            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()){
                boardDto.setId(rs.getInt("ID"));
                boardDto.setTitle(rs.getString("TITLE"));
                boardDto.setContent(rs.getString("CONTENT"));
                boardDto.setWRITER_ID(rs.getInt("WRITER_ID"));
                boardDto.setNickname(rs.getString("NICKNAME"));
                boardDto.setRegdate(rs.getTimestamp("REGDATE").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
                boardDto.setModdate(rs.getTimestamp("MODDATE").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
                boardDto.setCnt(rs.getInt("CNT"));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            JDBCUtil.close(conn, stmt, rs);
        }
        System.out.println("FreeBoardDao getBoard 메서드 호출종료");
        return boardDto;
    }

}
