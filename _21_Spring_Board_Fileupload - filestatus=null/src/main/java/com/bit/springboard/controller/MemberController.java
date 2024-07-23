package com.bit.springboard.controller;

import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class MemberController {
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //@RequestMapping(value = "/member/join.do", method = RequestMethod.GET) == @GetMapping("/member/join.do")
    @RequestMapping(value = "/join.do", method = RequestMethod.GET)
    public String joinView() {
        return "member/join";
    }

    @GetMapping("/login.do")
    public String loginView() {
        return "member/login";
    }

    @PostMapping("/usernameCheck.do")
    // Controller 메소드에서 String을 리턴하면 해당 String으로 jsp 파일을 찾게 된다.
    // jsp 파일 자체가 respone(응답 객체)의 body에 담겨서 리턴되는데
    // jsp 파일이나 html 파일이 아닌 리턴할 값을 response(응답 객체)의 body에 담아주려면
    // @ResponseBody 어노테이션을 사용한다. @ResponseBody 어노테이션이 달려있는 메소드에서는
    // viewResolver가 동작하지 않는다.
    @ResponseBody
    public String usernameCheck(MemberDto memberDto) {
        System.out.println(memberService.usernameCheck(memberDto.getUsername()));
        return memberService.usernameCheck(memberDto.getUsername());
    }

    @PostMapping("/nicknameCheck.do")
    @ResponseBody
    public String nicknameCheck(MemberDto memberDto) {
        return memberService.nicknameCheck(memberDto.getNickname());
    }

    @PostMapping("/join.do")
    public String join(MemberDto memberDto) {
        memberService.join(memberDto);
        return "member/login";
    }

    @PostMapping("/login.do")
    public String login(MemberDto memberDto, Model model, HttpSession session) {
        try {
            MemberDto loginMember = memberService.login(memberDto);

            loginMember.setPassword("");

            session.setAttribute("loginMember", loginMember);

            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("loginFailMsg", e.getMessage());
            return "member/login";
        }
    }

    @GetMapping("/logout.do")
    public String logout(HttpSession session) {
        // 세션에 있는 내용 모두 초기화
        session.invalidate();

        return "redirect:/member/login.do";
    }













}
