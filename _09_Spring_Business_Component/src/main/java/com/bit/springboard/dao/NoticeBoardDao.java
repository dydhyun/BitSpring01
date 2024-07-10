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
public class NoticeBoardDao {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    // NoticeBoard 게시글 작성하기
    private final String POST = "INSERT INTO Notice(TITLE, CONTENT, WRITER_ID) VALUES(?,?,?)";

    // NoticeBoard 게시글 수정하기
    private final String MODIFY = "UPDATE Notice SET TITLE = ?, CONTENT = ?, MODDATE = ? WHERE ID = ?";

    // NoticeBoard 게시글 삭제
    private final String DELETE = "DELETE FROM Notice WHERE ID = ?";

    // 게시글 전체 불러오기
    private final String GET_BOARD_LIST = "SELECT Notice.ID, Notice.TITLE, Notice.CONTENT,Notice.WRITER_ID, member.NICKNAME, Notice.REGDATE, Notice.MODDATE, Notice.CNT\n" +
            "FROM Notice\n" +
            "join member\n" +
            "on Notice.WRITER_ID = member.ID";

    // 게시글 하나 찝어서 보기
    private final String GET_BOARD = "SELECT notice.ID, notice.TITLE, notice.CONTENT," +
            "            notice.WRITER_ID, member.NICKNAME, notice.REGDATE, notice.MODDATE, notice.CNT" +
            "            FROM notice" +
            "            join member" +
            "            on notice.WRITER_ID = member.ID WHERE notice.id = ?";

//************************************************************

    public void post(BoardDto boardDto){
        System.out.println("NoticeBoardDao post 메서드 실행");

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
        System.out.println("NoticeBoardDao post 메서드 종료");
    }


    public void modify(BoardDto boardDto){
        System.out.println("NoticeBoadrDao - modify 메서드 호출");
        try {
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(MODIFY);

            stmt.setString(1, boardDto.getTitle());
            stmt.setString(2, boardDto.getContent());
            stmt.setString(3, boardDto.getModdate().toString());
            stmt.setInt(4, boardDto.getId());

            stmt.executeUpdate();

        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            JDBCUtil.close(conn, stmt);
        }
        System.out.println("NoticeBoadrDao - modify 메서드 종료");
    }


    public void delete(int id){
        System.out.println("NoticeBoardDao - delete 메서드 호출");
        try {
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(DELETE);

            stmt.setInt(1,id);
            stmt.executeUpdate();

        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            JDBCUtil.close(conn, stmt);
        }
        System.out.println("NoticeBoardDao - delete 메서드 종료");
    }


    public List<BoardDto> getBoardList(){
        System.out.println("NoticeBoardDao - getBoardList 메서드 호출");
        List<BoardDto> boardDtolist = new ArrayList<>();

        try{
            conn = JDBCUtil.getConnection();

            stmt = conn.prepareStatement(GET_BOARD_LIST);

            rs = stmt.executeQuery();

            while (rs.next()){
                BoardDto boardDto = new BoardDto();
                boardDto.setId(rs.getInt("ID"));
                boardDto.setTitle(rs.getString("TITLE"));
                boardDto.setContent(rs.getString("CONTENT"));
                boardDto.setWRITER_ID(rs.getInt("WRITER_ID"));
                boardDto.setNickname(rs.getString("NICKNAME"));
                boardDto.setRegdate(rs.getTimestamp("REGDATE").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
                boardDto.setModdate(rs.getTimestamp("MODDATE").toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime());
                boardDto.setCnt(rs.getInt("CNT"));

                boardDtolist.add(boardDto);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            JDBCUtil.close(conn, stmt, rs);
        }

        System.out.println("NoticeBoardDao - getBoardList 메서드 종료");
        return boardDtolist;
    }


    public BoardDto getBoard(int id){
        System.out.println("NoticeBoardDao - getBoard 메서드 실행");
        BoardDto boardDto = new BoardDto();

        try {
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
                boardDto.setRegdate(rs.getTimestamp("REGDATE").toLocalDateTime());
                boardDto.setModdate(rs.getTimestamp("MODDATE").toLocalDateTime());
                boardDto.setCnt(rs.getInt("CNT"));
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            JDBCUtil.close(conn, stmt, rs);
        }
        System.out.println("NoticeBoardDao - getBoard 메서드 종료");
        return boardDto;
    }


}
