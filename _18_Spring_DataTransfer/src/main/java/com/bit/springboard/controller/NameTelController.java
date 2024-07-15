package com.bit.springboard.controller;

import com.bit.springboard.dto.NameTelDto;
import com.bit.springboard.service.NameTelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NameTelController {

    private NameTelService nameTelService;

    @Autowired
    public NameTelController(NameTelService nameTelService){
        this.nameTelService = nameTelService;
    }

    // 요청 방식(get, post) 에 따라 다른 메서드를 실행시킬 수 있도록 지정할 수 있다.
    // 요청 url 매핑: @RequestMapping, @GetMapping, @PostMapping 에 지정된 url 로 요청이 발생할 경우,
    // 매핑되어 있는 메서드를 실행시킨다.
//    @RequestMapping(value = "/name-tel.do", method = RequestMethod.GET) // @GetMapping("/name-tel.do")와 동일방식.
//    public String nameTelView(){
//        System.out.println("Get 방식 동작");
//        return "name-tel";
//    }
//
//    // 동일한 요청 url 로 여러 방식 매핑시킬 수 있다.
//
//    @RequestMapping(value = "/name-tel.do", method = RequestMethod.POST) // @PostMapping("/name-tel.do")와 동일방식.
//    public String nameTelPost(){
//        System.out.println("Post 방식 동작");
//        return "name-tel";
////        return "redirect:/";
//    }

    // get/post 방식 모두 지원하는 url 매핑
//    @RequestMapping("/name-tel.do")
//    public String nameTelGetPost(){
//        System.out.println("get/post 방식 모두 동작");
//        return "name-tel";
//    }


    // 1. 화면단에서 전송한 데이터 받기
    // 1-1 HttpServletRequest 객체를 통해 파라미터 받기
    // Controller 객체의 메소드의 파라미터들은 객체를 생성하는 특수한 기능을 하게 되는데,
    // 이 기능으로 만들어진 객체를 Command 객체 라고 한다.
//    @GetMapping("/name-tel.do")
//    public String nameTelGet(HttpServletRequest request) {
//        System.out.println("name: " + request.getParameter("name")); // name 키의 벨류를 가져옴
//        System.out.println("tel: " + request.getParameter("tel")); // tel 키의 벨류를 가져옴
//        return "name-tel";
//    }


    // 1-2 @RequestParam 어노테이션 이용해 데이터 받기
//    @GetMapping("/name-tel.do")
//    // @RequestParam 의 required 속성은 true 가 기본값이라 데이터를 전송하지 않으면 에러 발생함.
//    public String nameTelGet(@RequestParam(value = "name", required = false, defaultValue = "test") String n,
//                             @RequestParam(value = "tel", required = false, defaultValue = "test") String t) {
//        System.out.println("name: " + n);
//        System.out.println("tel: " + t);
//        return "name-tel";
//    }


    // 1-3 @RequestParam 으로 전송된 데이터 모두를 Map 객체에 매핑
    // Map<String, String> => 지정했을 때 화면에서 넘겨준 키&벨류 형식의 데이터가 그대로 Map 에 매핑된다.
//    @GetMapping("/name-tel.do")
//    public String nameTelGet(@RequestParam Map<String, String> paramMap) {
//        System.out.println("name: " + paramMap.get("name"));
//        System.out.println("tel: " + paramMap.get("tel"));
//        return "name-tel";
//    }


    // 1-4 Command 객체와 @ModelAttribute 어노테이션을 이용한 데이터 받기
    // Controller 객체의 메서드의 매개변수로 클래스 타입의 변수를 선언하면 자동으로 객체를 생성하고
    // 전송된 데이터의 키와 클래스의 맴버변수명이 같으면 자동으로 해당 변수의 setter 메서드가 동작해서
    // 전송된 데이터 벨류 값이 맴버변수에 담긴다.
    // 그래서 setter 메서드가 구현되어 있지 않으면 값이 담기지 않는다.
    // @ModelAttribute 어노테이션은 생략 가능하다.

    // 조회 기능
    @GetMapping("/name-tel.do")
    public String nameTelGet(Model model) {
        System.out.println("nameTelGet 메서드 실행");
        model.addAttribute("nameTelList", nameTelService.getNameTelList());

        return "name-tel";
    }

    // 등록 기능
    @PostMapping("/name-tel.do")
    public String nameTelPost(@ModelAttribute NameTelDto nameTelDto, Model model, HttpServletRequest request, HttpSession httpSession) {
        System.out.println("name: " + nameTelDto.getName());
        System.out.println("tel: " + nameTelDto.getTel());
        nameTelService.postNameTel(nameTelDto);

        // 2. 자바에서 화면단으로 데이터 전송
        // 2-1 Model 객체를 통한 데이터 전송, Model 객체의 addAttribute (키,벨류) 로 데이터 매핑해서 화면단으로 전송
        // 화면단에서는 el 표기법(${키}) 으로 키 값에 해당하는 벨류를 사용할 수 있다.
//        model.addAttribute("nm", nameTelDto.getName());
//        model.addAttribute("tl", nameTelDto.getTel());

        // Model 객체에는 클래스 타입의 변수도 담아서 전송할 수 있다.
//        model.addAttribute("nameTelDto", nameTelDto);

//        model.addAttribute("nameTelList", nameTelService.getNameTelList());


        // 2-2 HttpServletRequest 객체를 통한 데이터 전송
        // HttpServlet 객체의 setAttribute(키, 벨류);
        request.setAttribute("nameTelList", nameTelService.getNameTelList());


        // 2-3 HttpSession 객체를 이용한 데이터 전송
        // Model 객체와 HttpServletRequest 객체는 하나의 요청이 시작될 때 생성돼서 요청이 종료되면 사라지는 객체
        // HttpSession 객체는 사용자가 웹 어플리케이션에 접속하는 순간 생성되는 객체
        // WEB Server (web.xml)에 설정되어 있는 시간동안 유지되는 객체
        // (기본값은 30분으로 설정되어 있고 사용자가 웹 어플리케이션에서 다른 동작(이벤트나 요청)을 발생시키면 다시 30분으로 초기화.)
        // 해당 요청에 한정되지 않고 다른 페이지들에서도 HttpSession 에 담겨있는 데이터를 사용할 수 있다.
        // 주로 로그인 사용자의 정보를 담아둘 때 사용한다.
//        httpSession.setAttribute("nameTelList", nameTelService.getNameTelList());

        // redirect 기능으로 다른 요청을 호출.
        // + redirect 는 get 방식으로 요청을 보낸다.

        return "redirect:/name-tel.do";
    }


}
