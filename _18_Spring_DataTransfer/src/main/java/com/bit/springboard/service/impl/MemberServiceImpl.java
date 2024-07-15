package com.bit.springboard.service.impl;

import com.bit.springboard.dao.MemberDao;
import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Service: 추가로 필요한 비즈니스 로직을 작성하는 클래스
@Service
public class MemberServiceImpl implements MemberService {
    private MemberDao memberDao;

    @Autowired
    public MemberServiceImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public void join(MemberDto memberDto) {
        // 필요한 로직 처리 코드 작성 후 Dao의 메소드를 호출해서 쿼리를 실행한다.
        // 회원가입에서 필요한 로직은 비밀번호 암호화 같은 로직을 말한다.
        memberDao.join(memberDto);
    }

    @Override
    public List<MemberDto> getMembers() {
        return memberDao.getMembers();
    }

    @Override
    public MemberDto getMemberByUsername(MemberDto memberDto) {
        return memberDao.getMemberByUsername(memberDto.getUsername() );
    }
}
