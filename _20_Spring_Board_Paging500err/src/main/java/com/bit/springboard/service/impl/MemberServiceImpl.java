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
    public String userNameCheck(String username){
        int usernameCheck = memberDao.userNameCheck(username); // 0 혹은 1 값 받아오기

        // String 값을 그대로 리턴하는 경우는 거의 없으며,
        // 대부분 json 형태의 String 을 리턴해 사용한다.

        // java 에서 json 데이터 형태로 만드는 방식 1 - JsonView
        // java 에서 json 데이터 형태로 만드는 방식 2 - ObjectMapper
        // ObjectMapper 객체를 이용한 json 형태의 데이터 만들기
        ObjectMapper objectMapper = new ObjectMapper();

        // Json 데이터 형태는 키와 벨류로 매핑되어있는 데이터 형태이기 때문에
        // Map 을 사용해 키와 벨류로 매핑된 데이터 형태로 만들어 준다.
        Map<String, String> jsonMap = new HashMap<>();

        if(usernameCheck == 0) {
          jsonMap.put("usernameCheckMsg", "usernamePass");
        }else{
          jsonMap.put("usernameCheckMsg", "usernameFail");
        }

        String jsonString = "";

        try{
            // 어떤 writer 를 사용할것 인지 선택하는 메서드
            // writerWithDefaultPrettyPrinter():
            // 들여쓰기와 엔터값이 포함하여 시인성이 높은 형태로 데이터를 써주는 writer
            jsonString = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonMap);
            // jsonMap 형태의 값을 String 값으로 변환시켜주는 메서드
        } catch (JsonProcessingException je){
            System.out.println(je.getMessage());
        }
        return jsonString;

//        String 방식
//        if(usernameCheck == 0){
//            return "usernamePass";
//        }
//        // 가입 된 아이디면 ->
//        return "usernameFail";
    }


    @Override
    public String nicknameCheck(String nickname){
        int nicknameCheck = memberDao.nicknameCheck(nickname);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> jsonNicknameMap = new HashMap<>();

        if(nicknameCheck == 0){
            jsonNicknameMap.put("nicknameCheckMsg", "nicknamePass");
        }else
            jsonNicknameMap.put("nicknameCheckMsg", "nicknameFail");

        String jsonNicknameString = "";

        try{
            jsonNicknameString = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonNicknameMap);
        }catch (JsonProcessingException je){
            System.out.println(je.getMessage());
        }
        return jsonNicknameString;
    }

    @Override
    public MemberDto login(MemberDto memberDto) {
        int usernameCheck = memberDao.userNameCheck(memberDto.getUsername());

        if(usernameCheck == 0)
            throw new RuntimeException("idNotExist");

        MemberDto loginMember = memberDao.login(memberDto);

        if(loginMember == null)
            throw new RuntimeException("wrongPassword");

        return loginMember;

    }

}
