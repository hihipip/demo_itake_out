package com.boss.demo.service;

import com.boss.demo.entity.Member;
import com.boss.demo.security.CustomUser;
import com.boss.demo.vo.SearchVo;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface MemberService {
    Member saveMember(Member member);
    Member getMemberById(long id);
    Page<Member> getAllMember(SearchVo searchVo);
    Member update(Member member,Long memberId);
    void delete(Long memberId);

    Member getMemberByUsername(String username);




}

