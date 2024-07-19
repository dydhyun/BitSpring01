package com.bit.springboard.controller;

import com.bit.springboard.dto.*;
import com.bit.springboard.service.BoardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {
    private BoardService boardService;
    private ApplicationContext applicationContext;

    @Autowired
    public BoardController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @RequestMapping("/free-list.do")
    public String freeListView(Model model, @RequestParam Map<String, String> searchMap, Criteria cri) {
        System.out.println(cri);
//        System.out.println(searchMap);
        boardService = applicationContext.getBean("freeBoardServiceImpl", BoardService.class);

        model.addAttribute("freeBoardList", boardService.getBoardList(searchMap, cri));
        model.addAttribute("searchMap", searchMap);

        // 게시글의 총 개수
        int total = boardService.getBoardTotalCnt(searchMap);

        // 화면에서 페이지 표시를 하기 위해 PageDto객체 화면에 전달
        model.addAttribute("page", new PageDto(cri, total));

        return "board/free-list";
    }

    @GetMapping("update-cnt.do")
    public String updateCnt(BoardDto boardDto) {
        if(boardDto.getType().equals("free")) {
            boardService = applicationContext.getBean("freeBoardServiceImpl", BoardService.class);
        } else {
            boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        }

        boardService.updateCnt(boardDto.getId());

        if(boardDto.getType().equals("free")) {
            return "redirect:/board/free-detail.do?id=" + boardDto.getId();
        } else {
            return "redirect:/board/notice-detail.do?id=" + boardDto.getId();
        }
    }

    @GetMapping("/free-detail.do")
    public String freeDetailView(/*HttpServletRequest request*//*@RequestParam("id") int id*/BoardDto boardDto, Model model) {
//        int id = Integer.valueOf(request.getParameter("id"));
        boardService = applicationContext.getBean("freeBoardServiceImpl", BoardService.class);
//        boardService.updateCnt(boardDto.getId());
        model.addAttribute("freeBoard", boardService.getBoard(boardDto.getId()));

        return "board/free-detail";
    }

    @RequestMapping("/notice-list.do")
    public String noticeListView(Model model, @RequestParam Map<String, String> searchMap, Criteria cri) {
        boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);

        cri.setAmount(9);

        model.addAttribute("noticeList", boardService.getBoardList(searchMap, cri));
        model.addAttribute("searchMap", searchMap);

        int total = boardService.getBoardTotalCnt(searchMap);

        model.addAttribute("page", new NoticePageDto(cri, total));

        return "board/notice-list";
    }

    @GetMapping("/notice-detail.do")
    public String noticeDetailView(BoardDto boardDto, Model model) {
        boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        model.addAttribute("notice", boardService.getBoard(boardDto.getId()));
        return "board/notice-detail";
    }

    @GetMapping("/post.do")
    public String postView(HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");

        if(loginMember == null) {
            return "redirect:/member/login.do";
        }

        return "board/post";
    }

    @PostMapping("/post.do")
    public String post(BoardDto boardDto) {
        // 게시판 타입에 따른 동적 의존성 주입
        if(boardDto.getType().equals("free")) {
            boardService = applicationContext.getBean("freeBoardServiceImpl", BoardService.class);
        } else {
            boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        }

        boardService.post(boardDto);

        if(boardDto.getType().equals("free")) {
            return "redirect:/board/free-list.do";
        } else {
            return "redirect:/board/notice-list.do";
        }
    }

    @PostMapping("/modify.do")
    public String modify(BoardDto boardDto) {
        if(boardDto.getType().equals("free")) {
            boardService = applicationContext.getBean("freeBoardServiceImpl", BoardService.class);
        } else {
            boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        }
        boardService.modify(boardDto);

        if(boardDto.getType().equals("free"))
            return "redirect:/board/free-detail.do?id=" + boardDto.getId();
        else
            return "redirect:/board/notice-detail.do?id=" + boardDto.getId();
    }

    @GetMapping("/delete.do")
    public String delete(BoardDto boardDto) {
        if(boardDto.getType().equals("free")) {
            boardService = applicationContext.getBean("freeBoardServiceImpl", BoardService.class);
        } else {
            boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        }

        boardService.delete(boardDto.getId());

//        model.addAttribute("freeBoardList", boardService.getBoardList());
        if(boardDto.getType().equals("free"))
            return "redirect:/board/free-list.do";
        else
            return "redirect:/board/notice-list.do";
    }




    @PostMapping("/notice-list-ajax.do")
    @ResponseBody // Map으로 리턴하면 알아서 json 형태로 날아간다
    public Map<String, Object> noticeListAjax(@RequestParam Map<String, String> searchMap, Criteria cri){
        boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);

//        cri.setAmount(cri.getPageNum() * 9);

        List<BoardDto> noticeList = boardService.getBoardList(searchMap, cri);

        Map<String, Object> returnMap = new HashMap<>();

        returnMap.put("noticeList", noticeList);

        return returnMap;
    }



}
