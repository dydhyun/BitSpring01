package com.bit.springboard.dao;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.service.BoardRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

//JDBC Template 을 필드변수로 선언하고 의존성 주입해 사용하기!!
@Repository
public class NoticeBoardDaoFieldVar {
    private JdbcTemplate jdbcTemplate;

    //JDBC Template 을 필드변수로 선언하고 의존성 주입해 사용하기!!
    @Autowired
    public void NoticeDotJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // NoticeBoard 게시글 작성하기
    private final String POST = "INSERT INTO Notice(TITLE, CONTENT, WRITER_ID) VALUES(?,?,?)";

    // NoticeBoard 게시글 수정하기
    private final String MODIFY = "UPDATE Notice SET TITLE = ?, CONTENT = ?, MODDATE = ? WHERE ID = ?";

    // NoticeBoard 게시글 삭제
    private final String DELETE = "DELETE FROM Notice WHERE ID = ?";

    // 게시글 전체 불러오기
    private final String GET_NOTICE_LIST = "SELECT Notice.ID, Notice.TITLE, Notice.CONTENT,Notice.WRITER_ID, member.NICKNAME, Notice.REGDATE, Notice.MODDATE, Notice.CNT " +
            "FROM Notice " +
            "join member " +
            "on Notice.WRITER_ID = member.ID";

    // 게시글 하나 찝어서 보기
    private final String GET_ONE_NOTICE = "SELECT" +
                                        " notice.ID," +
                                        " notice.TITLE," +
                                        " notice.CONTENT," +
                                        "notice.WRITER_ID," +
                                        " member.NICKNAME," +
                                        " notice.REGDATE," +
                                        " notice.MODDATE," +
                                        " notice.CNT" +
                                        "   FROM notice" +
                                        "   join member" +
                                        "   on notice.WRITER_ID = member.ID" +
                                        "   WHERE notice.id = ?";

//************************************************************

    public void noticePost(BoardDto boardDto){
        System.out.println("JDBC Template 을 필드변수로 선언하고 의존성 주입해 사용하기!!");
        System.out.println("NoticeBoardDao - post 메서드 실행");

        jdbcTemplate.update(POST, boardDto.getTitle(), boardDto.getContent(), boardDto.getWRITER_ID());

        System.out.println("NoticeBoardDao - post 메서드 종료");
    }


    public void noticeModify(BoardDto boardDto){
        System.out.println("JDBC Template 을 필드변수로 선언하고 의존성 주입해 사용하기!!");
        System.out.println("NoticeBoadrDao - modify 메서드 호출");

        jdbcTemplate.update(MODIFY, boardDto.getTitle(), boardDto.getContent(), boardDto.getModdate().toString(), boardDto.getId());

        System.out.println("NoticeBoadrDao - modify 메서드 종료");
    }


    public void delete(int id){
        System.out.println("JDBC Template 을 필드변수로 선언하고 의존성 주입해 사용하기!!");
        System.out.println("NoticeBoardDao - delete 메서드 호출");

        jdbcTemplate.update(DELETE, id);

        System.out.println("NoticeBoardDao - delete 메서드 종료");
    }


    public List<BoardDto> getBoardList(){
        System.out.println("JDBC Template 을 필드변수로 선언하고 의존성 주입해 사용하기!!");
        System.out.println("NoticeBoardDao - getBoardList 메서드 호출");
        List<BoardDto> boardDtoList;

        boardDtoList = jdbcTemplate.query(GET_NOTICE_LIST, new BoardRowMapper());

        System.out.println("NoticeBoardDao - getBoardList 메서드 종료");
        return boardDtoList;
    }


    public BoardDto getBoard(int id){
        System.out.println("JDBC Template 을 필드변수로 선언하고 의존성 주입해 사용하기!!");
        System.out.println("NoticeBoardDao - getBoard 메서드 실행");
        BoardDto boardDto;

        Object[] args = {id};

        boardDto = jdbcTemplate.queryForObject(GET_ONE_NOTICE, args, new BoardRowMapper());

        System.out.println("NoticeBoardDao - getBoard 메서드 종료");
        return boardDto;
    }


}
