package com.boss.demo.controller;

import com.boss.demo.entity.Dept;
import com.boss.demo.entity.Member;
import com.boss.demo.security.roles.IsAdmin;
import com.boss.demo.service.DeptService;
import com.boss.demo.service.MemberService;
import com.boss.demo.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@IsAdmin
@Controller
@RequestMapping("web/member")
public class WebMemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private DeptService deptService;


    /**
     * 會員列表
     * @param searchVo
     * @param model
     * @param request
     * @return
     */
    @GetMapping
    public String list(SearchVo searchVo, Model model, HttpServletRequest request){
        model.addAttribute("searchVo", searchVo); //搜尋參數
        model.addAttribute("members", memberService.getAllMember(searchVo));
        return "member/index";
    }

    @GetMapping("/add")
    public String add(Model model, HttpServletRequest request){
        this.setModel(model,null);
        return "member/add";
    }

    @PostMapping("/addSave")
    public String addSave( @Valid Member member, BindingResult result,Model model){
        try {
            memberService.saveMember(member);
        }catch(Exception e){
            ObjectError error = new ObjectError("globalError", "SQL語法錯誤或使用者重覆");
            result.addError(error);
        }
        if( result.hasErrors() ){
            this.setModel(model,member);
            return "member/add";
        }
        return "redirect:/web/member/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") long id,Model model){
        Member member = memberService.getMemberById(id);
        this.setModel(model,member);
        model.addAttribute("member", member);
        return "member/edit";
    }

    @PostMapping("/editSave")
    public String editSave(Member member,Model model){
        memberService.update(member,member.getId());
        return "redirect:/web/member/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        memberService.delete(id);
        return "redirect:/web/member/";
    }

    private void setModel(Model model,Member member){
        String[] interests = new String[]{"藍球", "棒球", "足球", "撞球"};
        if( member==null ){
            member = new Member();
        } else {
            member.setInterests(member.getInterest().split(","));
        }
        List<Dept> depts = this.deptService.getAll();
        model.addAttribute("interests", interests);
        model.addAttribute("member", member);
        model.addAttribute("depts", depts);
    }

}
