package com.boss.demo.apicontroller;

import com.boss.demo.entity.Member;
import com.boss.demo.service.MemberService;
import com.boss.demo.tools.R;
import com.boss.demo.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/members")
    public R list(SearchVo searchVo){
        return R.ok().data("data",memberService.getAllMember(searchVo));
    }

    @PostMapping("/members")
    public Member save(Member member){
        return memberService.saveMember(member);
    }



}
