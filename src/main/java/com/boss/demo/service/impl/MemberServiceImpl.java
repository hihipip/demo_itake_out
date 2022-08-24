package com.boss.demo.service.impl;

import com.boss.demo.entity.Member;
import com.boss.demo.repository.MemberRepository;
import com.boss.demo.security.CustomUser;
import com.boss.demo.service.MemberService;
import com.boss.demo.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Member saveMember(Member member) {
        if( member.getInterests()!=null ){
            member.setInterest(StringUtils.join(member.getInterests(),","));
        }
        return memberRepository.save(member);
    }

    @Override
    public Member getMemberById(long id){
        return memberRepository.getReferenceById(id);
    }





    @Override
    public Page<Member> getAllMember(SearchVo searchVo){

        int page = searchVo.getPage();
        if( page<0 ) page = 0;
        Pageable sortedBy = PageRequest.of(page, 2, Sort.by(Sort.Direction.ASC,"username"));
        if( searchVo.getSortBy()!=null && !searchVo.getSortBy().isEmpty() ){
            sortedBy = PageRequest.of(page, 2, Sort.by(Sort.Direction.ASC,searchVo.getSortBy()));
        }
        //設定預設日期
        if( searchVo.getStartDate()==null || "".equals(searchVo.getStartDate()) ){
            searchVo.setStartDate("2022-08-01");
        }
        if( searchVo.getEndDate()==null || "".equals(searchVo.getEndDate()) ){
            searchVo.setEndDate("2022-08-31");
        }
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchVo.getStartDate());
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(searchVo.getEndDate());
        }catch(Exception e){
            e.printStackTrace();
        }
        String searchName = searchVo.getSearchName()!=null ? searchVo.getSearchName() : "";
        //以下二種方式都可以
        //Page<Member> list = (Page<Member>) memberRepository.findAllByNameLikeAndCreateTimeBetween("%"+searchName+"%",startDate,endDate , sortedBy);
        Page<Member> list = (Page<Member>) memberRepository.findAllByQuery("%"+searchName+"%",startDate,endDate , sortedBy);

        //Page<Member> list = (Page<Member>) memberRepository.findAllByNameLikeOrUsernameContains("%小%","ter" , sortedBy);
        //Page<Member> list = (Page<Member>) memberRepository.findAll(sortedByName);
        //https://www.baeldung.com/spring-jpa-like-queries
        return list;
    }

    @Override
    public Member update(Member member,Long memberId) {
        if( member.getInterests()!=null ){
            member.setInterest(StringUtils.join(member.getInterests(),","));
        }
        Member dbMember = memberRepository.findById(memberId).get();
        member.setCreateTime(dbMember.getCreateTime());
        member.setCreateUser(dbMember.getCreateUser());
        return memberRepository.save(member);
    }

    @Override
    public void delete(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    public Member getMemberByUsername(String username){
        return memberRepository.findByUsername(username);
    }

}
