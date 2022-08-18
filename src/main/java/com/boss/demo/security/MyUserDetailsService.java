package com.boss.demo.security;

import com.boss.demo.entity.Consumer;
import com.boss.demo.entity.Member;
import com.boss.demo.repository.ConsumerRepository;
import com.boss.demo.service.MemberService;
import com.boss.demo.tools.ItemsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ConsumerRepository consumerRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /* //原本做法
        Member member = memberService.getMemberByUsername(username);
        if( member==null ){
            throw new UsernameNotFoundException("找不到這個使用者");
        }
        String role = ItemsInfo.getMemberRoleMessage(member.getRole());
        return new CustomUser(member.getId(), member.getUsername(), passwordEncoder.encode(member.getPassword()), getGrants(role));
         */
        Member member = memberService.getMemberByUsername(username);
        Consumer consumer = consumerRepository.findByPhone(username);
        if( member==null && consumer==null ) {
            throw new UsernameNotFoundException("找不到這個使用者");
        }
        if( consumer!=null ){
            String role = "ROLE_USER,ROLE_MANAGER";
            return new CustomUser(consumer.getId(), consumer.getPhone(), passwordEncoder.encode(consumer.getPassword()), getGrants(role));
        } else if( member!=null ){
            String role = ItemsInfo.getMemberRoleMessage(member.getRole());
            return new CustomUser(member.getId(), member.getUsername(), passwordEncoder.encode(member.getPassword()), getGrants(role));
        }
        return null;

    }

    private Collection<GrantedAuthority> getGrants(String role) {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(role);
    }
}
