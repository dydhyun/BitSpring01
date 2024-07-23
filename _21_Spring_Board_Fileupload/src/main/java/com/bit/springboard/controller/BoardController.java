package com.bit.springboard.controller;

import com.bit.springboard.dto.*;
import com.bit.springboard.service.BoardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
        model.addAttribute("fileList", boardService.getBoardFileList(boardDto.getId()));

        return "board/free-detail";
    }

    @RequestMapping("/notice-list.do")
    public String noticeListView(Model model, @RequestParam Map<String, String> searchMap, Criteria cri) {
        boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);

        cri.setAmount(9);

        List<Map<String, Object>> noticeList = new ArrayList<>();

        boardService.getBoardList(searchMap, cri).forEach(boardDto -> {
            List<BoardFileDto> boardFileDtoList = boardService.getBoardFileList(boardDto.getId());

            Map<String, Object> map = new HashMap<>();
            map.put("boardDto", boardDto);

            if (boardFileDtoList.size() > 0)
                map.put("file", boardFileDtoList.get(0));

            noticeList.add(map);
        });

        model.addAttribute("noticeList", noticeList);
        model.addAttribute("searchMap", searchMap);

        int total = boardService.getBoardTotalCnt(searchMap);

        model.addAttribute("page", new NoticePageDto(cri, total));

        return "board/notice-list";
    }

    @GetMapping("/notice-detail.do")
    public String noticeDetailView(BoardDto boardDto, Model model) {
        boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        model.addAttribute("notice", boardService.getBoard(boardDto.getId()));
        model.addAttribute("fileList", boardService.getBoardFileList(boardDto.getId()));
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
    public String post(BoardDto boardDto, MultipartFile[] uploadFiles) {
        // 게시판 타입에 따른 동적 의존성 주입
        if(boardDto.getType().equals("free")) {
            boardService = applicationContext.getBean("freeBoardServiceImpl", BoardService.class);
        } else {
            boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        }

        boardService.post(boardDto, uploadFiles);

        if(boardDto.getType().equals("free")) {
            return "redirect:/board/free-list.do";
        } else {
            return "redirect:/board/notice-list.do";
        }
    }

    @PostMapping("/modify.do")
    public String modify(BoardDto boardDto, MultipartFile[] uploadFiles, MultipartFile[] changeFiles,
                         @RequestParam(name = "originFiles", required = false) String originFiles) {
        if(boardDto.getType().equals("free")) {
            boardService = applicationContext.getBean("freeBoardServiceImpl", BoardService.class);
        } else {
            boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);
        }
        boardService.modify(boardDto, uploadFiles, changeFiles, originFiles);

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
    @ResponseBody
    public Map<String, Object> noticeListAjax(@RequestParam Map<String, String> searchMap, Criteria cri) {
        boardService = applicationContext.getBean("noticeServiceImpl", BoardService.class);

//        cri.setAmount(9);

        List<Map<String, Object>> noticeList = new ArrayList<>();

        boardService.getBoardList(searchMap, cri).forEach(boardDto -> {
            List<BoardFileDto> boardFileDtoList = boardService.getBoardFileList(boardDto.getId());

            Map<String, Object> map = new HashMap<>();

            map.put("boardDto", boardDto);

            if(boardFileDtoList.size() > 0)
                map.put("file", boardFileDtoList.get(0));

            noticeList.add(map);
        });

        Map<String, Object> returnMap = new HashMap<>();

        returnMap.put("noticeList", noticeList);

        return returnMap;
    }









}
