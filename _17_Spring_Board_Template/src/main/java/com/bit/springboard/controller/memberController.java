package com.bit.springboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/member") // 컨트롤러 자체를 member 폴더로 매핑해 -> ( member/join.do -> join.do )로 바꾸어 줄 수 있다.
public class memberController {
//    @RequestMapping(value = "/member/join.do", method = RequestMethod.GET) == @GetMapping("/member/join.do");
    // 같은 방식이다
    @RequestMapping(value = "/join.do", method = RequestMethod.GET)
    public String joinView(){
        return "/member/join";
    }

    @GetMapping("/login.do")
    public String loginView(){
        return "/member/login";
    }
}
