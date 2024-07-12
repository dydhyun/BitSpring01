package com.bit.springboard.dao;

import com.bit.springboard.dto.MemberDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

// DAO(Data Access Object): 데이터베이스에 직접 접근해서 쿼리를 실행하는 클래스
@Repository
public class MemberDao {

    private SqlSessionTemplate mybatis;

    public MemberDao(SqlSessionTemplate sqlSessionTemplate) { this.mybatis = sqlSessionTemplate; }

    public void join(MemberDto memberDto) {
        // MemberDto 에 담긴 내용을 Member 테이블에 insert
        System.out.println("MemberDao join 메서드 실행");

        // Mapper.xml 의 namespace 이름. 실행할 쿼리id
        mybatis.insert("memberDaoMap.join", memberDto);

        System.out.println("MemberDao join 메서드 실행 종료.");
    }

    public List<MemberDto> getMembers(){
        System.out.println("MemberDao getMembers 실행");

        // 리턴 할 MemberDto 목록
        List<MemberDto> memberDtoList;
        memberDtoList = mybatis.selectList("memberDaoMap.getMembers");

        System.out.println("MemberDao getMembers 실행종료");
        return memberDtoList;
    }


    public MemberDto getMemberByUsername(String username) {
        System.out.println("getMemberByUsername 실행");

        MemberDto memberDto = mybatis.selectOne("memberDaoMap.getOneByUsername", username);

        System.out.println("getMemberByUsername 실행종료");

        return memberDto;
    }

}
