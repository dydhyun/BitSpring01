package com.bit.springboard.dao;

import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.service.MemberRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

// JDBCTemplate 필드 변수로 선언해서 사용해보기~~
@Repository
public class MemberDaoFieldVar {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
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
        System.out.println("JDBC Template 필드변수로 선언해서 사용해보기");
        System.out.println("MemberDao join 메서드 실행");

        jdbcTemplate.update(JOIN, memberDto.getUsername(), memberDto.getPassword(), memberDto.getEmail(), memberDto.getNickname(), memberDto.getTel());

        System.out.println("MemberDao join 메서드 실행 종료.");
    }

    public List<MemberDto> getMembers(){
        System.out.println("JDBC Template 필드변수로 선언해 쓰기.");
        System.out.println("MemberDao getMembers 실행");
        List<MemberDto> memberDtoList;

        memberDtoList = jdbcTemplate.query(GET_MEMBERS, new MemberRowMapper());

        System.out.println("MemberDao getMembers 실행종료");
        return memberDtoList;
    }




    public MemberDto getMemberByUsername(String username) {
        System.out.println("JDBC Template 필드변수로 선언해 써보기");
        System.out.println("getMemberByUsername 실행");

        MemberDto memberDto = null;
        Object[] args = {username};

        memberDto = jdbcTemplate.queryForObject(GET_MEMBER_BY_USERNAME, args, new MemberRowMapper());

        System.out.println("getMemberByUsername 실행종료");

        return memberDto;
    }

}
