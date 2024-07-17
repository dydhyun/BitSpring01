package com.bit.springboard.controller;

import com.bit.springboard.dto.BoardDto;
import com.bit.springboard.dto.MemberDto;
import com.bit.springboard.service.BoardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {
    private BoardService boardService;
    private ApplicationContext applicationContext;

    @Autowired
    public BoardController(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @GetMapping("free-list.do")
    public String freeListView(Model model) {
        boardService = applicationContext.getBean("freeBoardServiceImpl", BoardService.class);

        model.addAttribute("freeBoardList", boardService.getBoardList());

        return "board/free-list";
    }


    /*
     * https://itcoin.tistory.com/694
     * 기본 동작으로 / 를 붙여준다
     * 블로그 참고하기
     */


    @GetMapping("/free-detail.do")
    public String freeDetailView(/*HttpServletRequest request*//*@RequestParam("id") int id*/BoardDto boardDto, Model model){

//        int id = Integer.valueOf(request.getParameter("id");

        System.out.println(boardDto.getCnt());
        boardService.cntUp(boardDto);

        boardService = applicationContext.getBean("freeBoardServiceImpl", BoardService.class);
        model.addAttribute("freeBoardInfo", boardService.getBoard(boardDto.getId()));
        return "board/free-detail";
    }

    @GetMapping("/post.do")
    public String postView(HttpSession session){
//         loginMember :
// MemberController 에서 세션에 setAttribute()로 담아준 loginMember 를
// getAttribute 로 받아올 수 있다.
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        if(loginMember == null){
            return "redirect:/member/login.do";
        }else {
            return "board/post";
        }
    }

    @GetMapping("/notice-list.do")
    public String noticeListView(Model model) {
        boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);

        // boardService.getBoardList() 의 이름을 noticeList 로 설정해서 모델객체에 넣어줌.
        model.addAttribute("noticeList", boardService.getBoardList());
        // notice-list.jsp 에서 jstl 을 이용한 forEach 문의 items 로 꺼낼 수 있음.
        return "board/notice-list";
    }

    @GetMapping("/notice-detail.do")
    public String noticeDetailView(BoardDto boardDto, Model model){
        boardService = applicationContext.getBean("noticeServiceImpl",BoardService.class);

        boardService.cntUp(boardDto);

        model.addAttribute("noticeInfo",boardService.getBoard(boardDto.getId()));

//        System.out.println(model);
//        System.out.println(boardService.getBoard(boardDto.getId()));
        return "board/notice-detail";
    }

    @PostMapping("/post.do")
    public String postView(BoardDto boardDto) {
        // 게시판 타입에 따른 동적 의존성 주입
        if(boardDto.getType().equals("free")) {
            boardService = applicationContext.getBean("freeBoardServiceImpl", BoardService.class);
        }else{
            boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        }

        boardService.post(boardDto);

        if(boardDto.getType().equals("free")){
            return "redirect:/board/free-list.do";
        }else{
            return "redirect:/board/notice-list.do";
        }
    }

    @PostMapping("/modify.do")
    public String modifyView(BoardDto boardDto){
//        System.out.println(boardDto);
//        System.out.println(boardService instanceof FreeBoardServiceImpl);
        if(boardDto.getType().equals("free")) {
            boardService = applicationContext.getBean("freeBoardServiceImpl", BoardService.class);
            boardService.modify(boardDto);
            return "redirect:/board/free-detail.do?id=" + boardDto.getId();
        }
        boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        boardService.modify(boardDto);
        return "redirect:/board/notice-detail.do?id=" + boardDto.getId();
    }

    @GetMapping("/delete.do")
    public String deleteView(BoardDto boardDto/*, Model model*/){
//        System.out.println(boardDto);
        System.out.println(boardDto.getType());
        if(boardDto.getType().equals("free")) {
            boardService = applicationContext.getBean("freeBoardServiceImpl", BoardService.class);
            boardService.delete(boardDto.getId());
//            System.out.println(boardService.getClass() + " " + boardDto.getId() + "번 게시물 삭제");
            return "redirect:/board/free-list.do";
        }
//        model.addAttribute("freeBoardList", boardService.getBoardList()); 굳이 안씀 위에 만든 do 메서드 활용
        boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        boardService.delete(boardDto.getId());

        return "redirect:/board/notice-list.do";
    }

}
