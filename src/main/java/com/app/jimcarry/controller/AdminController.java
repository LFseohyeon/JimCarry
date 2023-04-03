package com.app.jimcarry.controller;

import com.app.jimcarry.domain.dto.PageDTO;
import com.app.jimcarry.domain.dto.SearchDTO;
import com.app.jimcarry.domain.vo.Criteria;
import com.app.jimcarry.domain.vo.UserVO;
import com.app.jimcarry.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/*")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final UserService userService;
    private final StorageService storageService;
    private final ReviewService reviewService;
    private final InquiryService inquiryService;
    private final NoticeService noticeService;
    private final PaymentService paymentService;

    /*-----------회원관리----------*/
    @GetMapping("user")
    public String user(Criteria criteria, Model model) {

        /* 한 페이지에 보여줄 게시글 개수 */
        int amount = 5;
        /* 검색된 결과의 총 개수 */
        int total = 0;
//        String type = null;
        /* 추후에 setUserId 세션으로 변경 */
        SearchDTO searchDTO = new SearchDTO();
//        searchDTO.setTypes(new ArrayList<>(Arrays.asList("userName", "userBirth")));

        PageDTO pageDTO = null;

//         페이지 번호가 없을 때, 디폴트 1페이지
        if (criteria.getPage() == 0) {
            criteria.create(1, amount);
        } else criteria.create(criteria.getPage(), amount);

        total= userService.findTotalBy(searchDTO);
        pageDTO = new PageDTO().createPageDTO(criteria, total, searchDTO);
        model.addAttribute("total", total);
        model.addAttribute("users", userService.getUserListBy(pageDTO));
        model.addAttribute("pagination", pageDTO);
        model.addAttribute("searchDTO", searchDTO);

        return "/admin/user";
    }

    /* 회원 삭제 */
    @PostMapping("user/delete")
    @ResponseBody
    public boolean removeUser(String[] userIds){
        Arrays.asList(userIds).stream().forEach(data -> userService.removeUser(Long.valueOf(data)));
        return true;
    }
    // 검색 + 페이징 처리
    @GetMapping("user/search")
    public String userSearch(Criteria criteria, String search, String condition, Model model) {

        /* 한 페이지에 보여줄 게시글 개수 */
        int amount = 5;
        /* 검색된 결과의 총 개수 */
        int total = 0;

//        String type = null;
        /* 추후에 setUserId 세션으로 변경 */
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setTypes(new ArrayList<>(Arrays.asList(condition)));
        searchDTO.setUserName(search);
        searchDTO.setUserAddress(search);
        PageDTO pageDTO = null;
//         페이지 번호가 없을 때, 디폴트 1페이지
        if (criteria.getPage() == 0) {
            criteria.create(1, amount);
        } else criteria.create(criteria.getPage(), amount);

        total= userService.findTotalBy(searchDTO);
        pageDTO = new PageDTO().createPageDTO(criteria, total, searchDTO);
//        List<UserVO> users =  userService.getUserListBy(pageDTO);
        model.addAttribute("total", total);
        model.addAttribute("users", userService.getUserListBy(pageDTO));
        model.addAttribute("pagination", pageDTO);
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("condition", condition);
        model.addAttribute("search", search);
        return "/admin/user";
    }

    /*-----------문의사항----------*/
    @GetMapping("enquiry")
        public String enquiry(Criteria criteria, Model model) {

        int amount = 5;
        int total = 0;

        SearchDTO searchDTO = new SearchDTO();
        PageDTO pageDTO = null;

        if (criteria.getPage() == 0) {
            criteria.create(1, amount);
        } else criteria.create(criteria.getPage(), amount);

        total = inquiryService.getTotalBy(searchDTO);
        pageDTO = new PageDTO().createPageDTO(criteria, total, searchDTO);
        model.addAttribute("total", total);
        model.addAttribute("inquiries", inquiryService.getDTOListBy(pageDTO));
        model.addAttribute("pagination", pageDTO);
        model.addAttribute("searchDTO", searchDTO);
        return "/admin/enquiry";
    }
    /* 문의사항 삭제 */
    @PostMapping("enquiry/delete")
    @ResponseBody
    public boolean removeinquiry(String[] inquiryIds){
        Arrays.asList(inquiryIds).stream().forEach(data -> inquiryService.removeInquiry(Long.valueOf(data)));
        return true;
    }

    // 검색 + 페이징 처리
    @GetMapping("enquiry/search")
    public String enquirySearch(Criteria criteria, String search, String condition, Model model) {

        /* 한 페이지에 보여줄 게시글 개수 */
        int amount = 5;
        /* 검색된 결과의 총 개수 */
        int total = 0;

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setTypes(new ArrayList<>(Arrays.asList(condition)));
        searchDTO.setUserName(search);
        searchDTO.setUserIdentification(search);
//        searchDTO.setInquiryContent(search);
//        searchDTO.setInquiryTitle(search);
        PageDTO pageDTO = null;
        log.info(search);
        log.info(searchDTO.toString());
//         페이지 번호가 없을 때, 디폴트 1페이지
        if (criteria.getPage() == 0) {
            criteria.create(1, amount);
        } else criteria.create(criteria.getPage(), amount);
        total= inquiryService.getTotalBy(searchDTO);
        log.info(String.valueOf(total));

        pageDTO = new PageDTO().createPageDTO(criteria, total, searchDTO);
        log.info(inquiryService.getDTOListBy(pageDTO).toString());
        model.addAttribute("total", total);
        model.addAttribute("inquiries", inquiryService.getDTOListBy(pageDTO));
        model.addAttribute("pagination", pageDTO);
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("condition", condition);
        model.addAttribute("search", search);
        return "/admin/enquiry";
    }


    /*-----------공지사항----------*/
    @GetMapping("notice")
    public String notice(Criteria criteria, Model model) {
        int amount = 5;
        int total = 0;

        SearchDTO searchDTO = new SearchDTO();

        PageDTO pageDTO = null;

        if(criteria.getPage() == 0){
            criteria.create(1, amount);
        }else criteria.create(criteria.getPage(), amount);

        total= noticeService.getTotalBy(searchDTO);
        pageDTO = new PageDTO().createPageDTO(criteria, total, searchDTO);
        model.addAttribute("total", total);
        model.addAttribute("notices", noticeService.getListBy(pageDTO));
        model.addAttribute("pagination", pageDTO);
        model.addAttribute("searchDTO", searchDTO);

        return "/admin/notice";
    }
    /* 공지사항 내역 삭제 */
    @PostMapping("notice/delete")
    @ResponseBody
    public boolean removeNotice(String[] noticeIds) {
        Arrays.asList(noticeIds).stream().forEach(data -> noticeService.removeNotice(Long.valueOf(data)));
        return true;
    }
    /* 검색 + 페이징 처리*/
    @GetMapping("notice/search")
    public String noticeSearch(Criteria criteria, String search, String condition, Model model) {
        /* 한 페이지에 보여줄 게시글 개수 */
        int amount = 5;
        /* 검색된 결과의 총 개수 */
        int total = 0;
//        String type = null;
        /* 추후에 setUserId 세션으로 변경 */
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setTypes(new ArrayList<>(Arrays.asList(condition)));
        searchDTO.setNoticeTitle(search);
        searchDTO.setNoticeWriter(search);
        PageDTO pageDTO = null;
//         페이지 번호가 없을 때, 디폴트 1페이지
        if (criteria.getPage() == 0) {
            criteria.create(1, amount);
        } else criteria.create(criteria.getPage(), amount);

        total= noticeService.getTotalBy(searchDTO);
        pageDTO = new PageDTO().createPageDTO(criteria, total, searchDTO);
        model.addAttribute("total", total);
        model.addAttribute("notices", noticeService.getListBy(pageDTO));
        model.addAttribute("pagination", pageDTO);
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("condition", condition);
        model.addAttribute("search", search);
        return "/admin/notice";
    }

    /*-----------결제관리----------*/
    @GetMapping("payment")
    public String payment(Criteria criteria, Model model) {
        int amount = 5;
        int total = 0;
        int payAmount = 0;
        SearchDTO searchDTO = new SearchDTO();
//        searchDTO.setTypes(new ArrayList<>(Arrays.asList("userId")));

        PageDTO pageDTO = null;

        if (criteria.getPage() == 0) {
            criteria.create(1, amount);
        } else criteria.create(criteria.getPage(), amount);

        total = paymentService.getTotalBy(searchDTO);
        payAmount = paymentService.getTotalPay();
        pageDTO = new PageDTO().createPageDTO(criteria, total, searchDTO);
        model.addAttribute("total", total);
        model.addAttribute("payAmount", payAmount);
        model.addAttribute("payments", paymentService.getList(pageDTO));
        model.addAttribute("pagination", pageDTO);
        model.addAttribute("searchDTO", searchDTO);

        return "/admin/payment";
    }
    /* 결제내역 삭제 */
    @PostMapping("payment/delete")
    @ResponseBody
    public boolean removePayment(String[] payIds){
        Arrays.asList(payIds).stream().forEach(data -> paymentService.removePayment(Long.valueOf(data)));
        return true;
    }

    /* 검색 + 페이징 처리*/
    @GetMapping("payment/search")
    public String paymentSearch(Criteria criteria, String search, String condition, Model model) {
        int amount = 5;
        int total = 0;

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setTypes(new ArrayList<>(Arrays.asList(condition)));
        searchDTO.setStorageAddress(search);
        searchDTO.setStorageSize(search);

        PageDTO pageDTO = null;

        if (criteria.getPage() == 0) {
            criteria.create(1, amount);
        } else criteria.create(criteria.getPage(), amount);

        total = paymentService.getTotalBy(searchDTO);
        pageDTO = new PageDTO().createPageDTO(criteria, total, searchDTO);
        model.addAttribute("total", total);
        model.addAttribute("payments", paymentService.getList(pageDTO));
        model.addAttribute("pagination", pageDTO);
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("condition", condition);
        model.addAttribute("search", search);

        return "/admin/payment";
    }

    /*-----------리뷰관리----------*/
    @GetMapping("review")
    public String review(Criteria criteria, Model model) {
        int amount = 5;
        int total = 0;

        SearchDTO searchDTO = new SearchDTO();
//        searchDTO.setTypes(new ArrayList<>(Arrays.asList("userId")));
//        searchDTO.setUserId(2L);

        PageDTO pageDTO = null;

        if (criteria.getPage() == 0) {
            criteria.create(1, amount);
        } else criteria.create(criteria.getPage(), amount);

        total = reviewService.getTotalBy(searchDTO);
        pageDTO = new PageDTO().createPageDTO(criteria, total, searchDTO);
        model.addAttribute("total", total);
        model.addAttribute("reviews", reviewService.getListBy(pageDTO));
        model.addAttribute("pagination", pageDTO);
        model.addAttribute("searchDTO", searchDTO);

        return "/admin/review";
    }

    @PostMapping("review/delete")
    @ResponseBody
    public boolean removeReview(String[] reviewIds){
        Arrays.asList(reviewIds).stream().forEach(data -> reviewService.removeReview(Long.valueOf(data)));
        return true;
    }
    /* 검색 + 페이징 */
    @GetMapping("review/search")
    public String review(Criteria criteria, String search, String condition, Model model) {
        int amount = 5;
        int total = 0;

        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setTypes(new ArrayList<>(Arrays.asList(condition)));
        searchDTO.setReviewContext(search);
        searchDTO.setReviewTitle(search);

        PageDTO pageDTO = null;

        if (criteria.getPage() == 0) {
            criteria.create(1, amount);
        } else criteria.create(criteria.getPage(), amount);

        total = reviewService.getTotalBy(searchDTO);
        pageDTO = new PageDTO().createPageDTO(criteria, total, searchDTO);
        model.addAttribute("total", total);
        model.addAttribute("reviews", reviewService.getListBy(pageDTO));
        model.addAttribute("pagination", pageDTO);
        model.addAttribute("condition", condition);
        model.addAttribute("search", search);
        model.addAttribute("searchDTO", searchDTO);

        return "/admin/review";
    }

    /*-----------창고관리----------*/
    @GetMapping("storage")
    public String storage(Criteria criteria, Model model) {

        int amount = 5;
        int total = 0;

        SearchDTO searchDTO = new SearchDTO();
//        searchDTO.setTypes(new ArrayList<>(Arrays.asList("userId")));
//        searchDTO.setUserId(2L);


        PageDTO pageDTO = null;

        if (criteria.getPage() == 0) {
            criteria.create(1, amount);
        } else criteria.create(criteria.getPage(), amount);

        total =  storageService.getTotalDTOBy(searchDTO);
        pageDTO = new PageDTO().createPageDTO(criteria, total, searchDTO);
        model.addAttribute("total", total);
        model.addAttribute("storages", storageService.getStorageList(pageDTO));
        model.addAttribute("pagination", pageDTO);
        model.addAttribute("searchDTO", searchDTO);

        return "/admin/storage";
    }
    /* 창고 삭제 */
    @PostMapping("storage/delete")
    @ResponseBody
    public boolean removeStorage(String[] storageIds){
        Arrays.asList(storageIds).stream().forEach(data -> storageService.removeStorage(Long.valueOf(data)));
        return true;
    }
    /* 검색 + 페이징 처리*/
    @GetMapping("storage/search")
    public String storageSearch(Criteria criteria, String search, String condition, Model model) {
        /* 한 페이지에 보여줄 게시글 개수 */
        int amount = 5;
        /* 검색된 결과의 총 개수 */
        int total = 0;
//        String type = null;
        /* 추후에 setUserId 세션으로 변경 */
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setTypes(new ArrayList<>(Arrays.asList(condition)));
        searchDTO.setStorageAddress(search);
        searchDTO.setStorageSize(search);
        PageDTO pageDTO = null;
//         페이지 번호가 없을 때, 디폴트 1페이지
        if (criteria.getPage() == 0) {
            criteria.create(1, amount);
        } else criteria.create(criteria.getPage(), amount);

        total= storageService.getTotalDTOBy(searchDTO);
        pageDTO = new PageDTO().createPageDTO(criteria, total, searchDTO);
//        List<UserVO> users =  userService.getUserListBy(pageDTO);
        model.addAttribute("total", total);
        model.addAttribute("storages", storageService.getStorageList(pageDTO));
        model.addAttribute("pagination", pageDTO);
        model.addAttribute("searchDTO", searchDTO);
        model.addAttribute("condition", condition);
        model.addAttribute("search", search);
        return "/admin/storage";
    }
}


