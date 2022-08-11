package com.boss.demo.security;

import com.boss.demo.entity.Member;
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
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberService.getMemberByUsername(username);
        if( member==null ){
            throw new UsernameNotFoundException("找不到這個使用者");
        }
        String role = ItemsInfo.getMemberRoleMessage(member.getRole());
        return new CustomUser(member.getId(), member.getUsername(), passwordEncoder.encode(member.getPassword()), getGrants(role));
    }

    private Collection<GrantedAuthority> getGrants(String role) {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(role);
    }
}
