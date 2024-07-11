package com.bit.springboard.dao;

import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.service.MemberRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

// JDBC Dao Support 클래스 상속 방식으로 Jdbc Template 써보기
@Repository
public class MemberDaoClassInherit extends JdbcDaoSupport {

    @Autowired // 데이터소스 연결해주기
    public void setSuperDataSource(DataSource dataSource){
        super.setDataSource(dataSource);
    }

    // 쿼리문 작성
    // 회원가입 쿼리문
    private final String JOIN = "INSERT INTO MEMBER(USERNAME, PASSWORD, EMAIL, NICKNAME, TEL) VALUES (?, ?, ?, ?, ?)";
    // 모든 회원 목록 조회 쿼리문
    private final String GET_MEMBERS = "SELECT ID" +
            "                                , USERNAME" +
            "                                , PASSWORD" +
            "                                , NICKNAME" +
            "                                , EMAIL" +
            "                                , TEL" +
            "                               FROM MEMBER";
    // 특정 회원 조회 쿼리문
    private final String GET_MEMBER_BY_USERNAME = "SELECT ID" +
            "                                           , USERNAME" +
            "                                           , PASSWORD" +
            "                                           , NICKNAME" +
            "                                           , EMAIL" +
            "                                           , TEL" +
            "                                          FROM MEMBER" +
            "                                          WHERE USERNAME = ?";

    public void join(MemberDto memberDto) {
        // MemberDto 에 담긴 내용을 Member 테이블에 insert
        System.out.println("Jdbc Dao Support 클래스 상속을 이용해 Jdbc Template 써보기!");
        System.out.println("MemberDao join 메서드 실행");

        getJdbcTemplate().update(JOIN, memberDto.getUsername(), memberDto.getPassword(), memberDto.getEmail(),
                                memberDto.getNickname(), memberDto.getTel());

        System.out.println("MemberDao join 메서드 실행 종료.");
    }

    public List<MemberDto> getMembers(){
        System.out.println("Jdbc Dao Support 클래스 extends 해서 JdbcTemplate 써보기.");
        System.out.println("MemberDao getMembers 실행");

        // 리턴 할 MemberDto 목록
        List<MemberDto> memberDtoList = new ArrayList<MemberDto>();

        memberDtoList = getJdbcTemplate().query(GET_MEMBERS, new MemberRowMapper());

        System.out.println("MemberDao getMembers 실행종료");
        return memberDtoList;
    }




    public MemberDto getMemberByUsername(String username) {
        System.out.println("Jdbc Dao Support 클래스 상속받아 Jdbc Template 사용하기");
        System.out.println("getMemberByUsername 실행");

        MemberDto memberDto = null;

        Object[] args = {username};

        memberDto = getJdbcTemplate().queryForObject(GET_MEMBER_BY_USERNAME, args, new MemberRowMapper());

        System.out.println("getMemberByUsername 실행종료");

        return memberDto;
    }

}
