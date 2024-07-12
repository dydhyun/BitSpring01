package com.bit.springboard.dao;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.service.BoardRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

// JDBCDaoSupport 클래스 상속 방식으로 JDBC Template 사용하기
@Repository
public class NoticeBoardDaoJDBCSupport extends JdbcDaoSupport {

    // JDBCDaoSupport 클래스 상속 방식으로 JDBC Template 사용하기
    @Autowired
    public void setSuperDataSource(DataSource dataSource){
        super.setDataSource(dataSource);
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
        System.out.println("JDBCDaoSupport 클래스 상속 방식으로 JDBC Template 사용하기!!");
        System.out.println("NoticeBoardDao - post 메서드 실행");

        getJdbcTemplate().update(POST, boardDto.getTitle(), boardDto.getContent(), boardDto.getWRITER_ID());

        System.out.println("NoticeBoardDao - post 메서드 종료");
    }


    public void noticeModify(BoardDto boardDto){
        System.out.println("JDBCDaoSupport 클래스 상속 방식으로 JDBC Template 사용하기!!");
        System.out.println("NoticeBoadrDao - modify 메서드 호출");

        getJdbcTemplate().update(MODIFY, boardDto.getTitle(), boardDto.getContent(), boardDto.getModdate().toString(), boardDto.getId());

        System.out.println("NoticeBoadrDao - modify 메서드 종료");
    }


    public void delete(int id){
        System.out.println("JDBCDaoSupport 클래스 상속 방식으로 JDBC Template 사용하기!!");
        System.out.println("NoticeBoardDao - delete 메서드 호출");

        getJdbcTemplate().update(DELETE, id);

        System.out.println("NoticeBoardDao - delete 메서드 종료");
    }


    public List<BoardDto> getBoardList(){
        System.out.println("JDBCDaoSupport 클래스 상속 방식으로 JDBC Template 사용하기!!");
        System.out.println("NoticeBoardDao - getBoardList 메서드 호출");
        List<BoardDto> boardDtolist = new ArrayList<>();

        // new BoardRowMapper()를 생성해서 쿼리 실행 결과를 담아서 boardDtoList 에 저장한다.
        boardDtolist = getJdbcTemplate().query(GET_NOTICE_LIST, new BoardRowMapper());

        System.out.println("NoticeBoardDao - getBoardList 메서드 종료");
        return boardDtolist;
    }


    public BoardDto getBoard(int id){
        System.out.println("JDBCDaoSupport 클래스 상속 방식으로 JDBC Template 사용하기!!");
        System.out.println("NoticeBoardDao - getBoard 메서드 실행");
//        BoardDto boardDto = new BoardDto();
        BoardDto boardDto;

        // 한 행만 가져오기 위한 queryForObject() 의 두 번째 매개변수는 Object 배열 형태여야 한다.
        Object[] args = {id}; // 아이디를 오브젝트 배열 객체로 새로 저장해서

        //GET_ONE_NOTICE 를 실행할 때 args 를 넘겨주어 new BoardRowMapper() 에 실행결과 저장해서 boardDto에 저장.
        boardDto = getJdbcTemplate().queryForObject(GET_ONE_NOTICE, args, new BoardRowMapper());

        System.out.println("NoticeBoardDao - getBoard 메서드 종료");
        return boardDto;
    }


}
