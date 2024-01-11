package com.bookrealm.controller;

import com.bookrealm.model.Book;
import com.bookrealm.model.Member;
import com.bookrealm.naver.NaverBookClient;
import com.bookrealm.naver.dto.SearchBookReq;
import com.bookrealm.naver.dto.SearchBookRes;
import com.bookrealm.service.AdminService;
import com.bookrealm.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final NaverBookClient naverBookClient;
    private final AdminService adminService;

    private final MemberService memberService;

    @Autowired
    AdminController(NaverBookClient naverBookClient,
                    AdminService adminService,
                    MemberService memberService){

        this.naverBookClient = naverBookClient;
        this.adminService = adminService;
        this.memberService = memberService;
    }

    // 관리자 홈 화면
    @RequestMapping
    public String home(){
        return "/admin/home";
    }

    // DB 저장 도서 관리 페이지
    @RequestMapping("/book/manage")
    public ModelAndView manageBook() {
        ModelAndView mav = new ModelAndView("/admin/book/manage");
        List<Book> bookList = adminService.findAll();
        mav.addObject("bookList",bookList);
        return mav;
    }

    // 수정 폼
    @GetMapping("/book/manage/edit")
    public ModelAndView editBookView(@RequestParam Long id){
        ModelAndView mav = new ModelAndView("/admin/book/edit");
        Optional<Book> book = adminService.findById(id);
        mav.addObject("book", book.get());
        return mav;
    }

    // 수정 요청
    @PostMapping("/book/manage/edit")
    public String editBook(Book editbook){
        adminService.save(editbook);

        return "redirect:/admin/book/manage";
    }

    @GetMapping("/book/search")
    public String searchBook(){

        return "/admin/book/search";
    }
    @GetMapping("/book/search/result")
    public ModelAndView searchBook(@RequestParam(name = "query") String query){

        ModelAndView mav = new ModelAndView("/admin/book/search_result_save");

        SearchBookRes result = naverBookClient.searchBookApi(new SearchBookReq(query));
        //List<SearchBookRes.SearchBookItem> list = result.getItems();

        mav.addObject("result",result);
        return mav;
    }

    @GetMapping("/member")
    public ModelAndView members(){
        ModelAndView mav = new ModelAndView("admin/user/users");
        List<Member> members = memberService.findAllUsers();
        System.out.println(members);
        mav.addObject("members", members);
        return mav;
    }
}
