package com.bit.springboard.service.impl;

import com.bit.springboard.dao.MemberDaoFieldVar;
import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Service : 추가로 필요한 비즈니스 로직을 작성하는 클래스
@Service
public class MemberServiceImpl implements MemberService {
    private MemberDaoFieldVar memberDao;

    @Autowired
    public MemberServiceImpl(MemberDaoFieldVar memberDao) {
        this.memberDao = memberDao;
    }


    @Override
    public void join(MemberDto memberDto) {
        // 필요한 로직처리 코드 작성 후 Dao 의 메서드를 호출하여 쿼리 실행한다.
        // 회원가입에서 필요한 로직은 ex)패스워드가 들어왔을때의 비밀번호 암호화 로직 등이 있음
        memberDao.join(memberDto);
    }

    @Override
    public List<MemberDto> getMembers() {
        return memberDao.getMembers();
    }

    @Override
    public MemberDto getMemberByUsername(String username) {
        return memberDao.getMemberByUsername(username);
    }

//    public MemberDto getMemberByUsername2(MemberDto memberDto) {
//        return memberDao.getMemberByUsername2(memberDto);
//    }
}
