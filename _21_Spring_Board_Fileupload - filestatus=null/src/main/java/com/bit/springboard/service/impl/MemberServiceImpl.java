package com.bit.springboard.service.impl;

import com.bit.springboard.dao.MemberDao;
import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public String usernameCheck(String username) {
        int usernameCheck = memberDao.usernameCheck(username);

        // String 값을 그대로 리턴하는 경우는 거의 없고
        // 대부분 json 형태의 String을 리턴하여 사용한다.

        // Java에서 Json 데이터 형태로 만드는 방식1 - JsonView
        // Java에서 Json 데이터 형태로 만드는 방식1 - ObjectMapper
        // ObjectMapper 객체를 이용한 json 형태의 데이터 만들기
        ObjectMapper objectMapper = new ObjectMapper();

        // Json 데이터 형태는 키와 밸류로 매핑되어 있는 데이터 형태이기 때문에
        // Map을 사용해서 키와 밸류로 매핑되어 있는 데이터 형태로 만들어준다.
        Map<String, String> jsonMap = new HashMap<>();

        if(usernameCheck == 0) {
            jsonMap.put("usernameCheckMsg", "usernameOk");
        } else {
            jsonMap.put("usernameCheckMsg", "usernameFail");
        }

        String jsonString = "";

        try {
            // writerWithDefaultPrettyPrinter(): 들여쓰기랑 엔터값이 포함하여 시인성이 높은 형태로 데이터를 써주는 writer
            jsonString = objectMapper.writerWithDefaultPrettyPrinter()
                                     .writeValueAsString(jsonMap);
        } catch (JsonProcessingException je) {
            System.out.println(je.getMessage());
        }

        return jsonString;
//        if (usernameCheck == 0)
//            return "usernameOk";
//
//        return "usernameFail";
    }

    @Override
    public String nicknameCheck(String nickname) {
        int nicknameCheck = memberDao.nicknameCheck(nickname);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> jsonMap = new HashMap<>();

        if(nicknameCheck == 0) {
            jsonMap.put("nicknameCheckMsg", "nicknameOk");
        } else {
            jsonMap.put("nicknameCheckMsg", "nicknameFail");
        }

        String jsonString = "";

        try {
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);
        } catch (JsonProcessingException je) {
            System.out.println(je.getMessage());
        }

        return jsonString;
    }

    @Override
    public MemberDto login(MemberDto memberDto) {
        int usernameCheck = memberDao.usernameCheck(memberDto.getUsername());

        if(usernameCheck == 0)
            throw new RuntimeException("idNotExist");

        MemberDto loginMember = memberDao.login(memberDto);

        if(loginMember == null)
            throw new RuntimeException("wrongPassword");

        return loginMember;
    }
}
