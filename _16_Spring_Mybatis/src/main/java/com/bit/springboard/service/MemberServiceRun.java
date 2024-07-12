package com.bit.springboard.service;

import com.bit.springboard.dto.MemberDto;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MemberServiceRun {
    public static void main(String[] args) {
        AbstractApplicationContext factory =
                new GenericXmlApplicationContext("root-context.xml");

        MemberService memberService = factory.getBean("memberServiceImpl", MemberService.class);

        MemberDto memberDto = new MemberDto();
//        memberDto.setId(1); //Auto Increase
        memberDto.setUsername("bitcamp712-3");
        memberDto.setPassword("dkdlxl712-3");
        memberDto.setNickname("비트캠프712-3");
        memberDto.setEmail("bitcamp@bit.co.kr");
        memberDto.setTel("010-1234-5678");

        memberService.join(memberDto);

        memberService.getMembers().forEach(System.out::println);


        System.out.println(memberService.getMemberByUsername("bitcamp712-3"));


        factory.close();
    }
}