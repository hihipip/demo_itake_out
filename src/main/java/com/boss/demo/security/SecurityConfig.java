package com.boss.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String SECRET_KEY = "123456";

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*  OK
        //?????? in a frame because it set X-Frame-Options to
        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.formLogin()
                .loginPage("/web/login") // ?????????????????????
                //.failureUrl("/web/login?error") // ??????????????????
                .and()
                .logout()
                .logoutUrl("/web/logout")// ?????????????????????
                //.logoutSuccessHandler(myLogoutSuccessHandler)
                //.logoutSuccessUrl("/web/member/")
                .and()
                .rememberMe() // ????????????????????????
                .rememberMeServices(getRememberMeServices()) // ????????????
                .key(SECRET_KEY) // ???SECRET???????????????TokenBasedRememberMeServices???????????????
                .and()
                 //???????????????????????????????????????????????????????????????????????????????????????
                 //??????????????????????????????????????????????????????
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/web/403"); // ????????????????????????403
        http.logout().permitAll();
        //http.logout().logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)));
        http.logout().logoutSuccessHandler(myLogoutSuccessHandler);
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
*/


        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.formLogin()
                .loginPage("/web/login") // ?????????????????????
                //.failureUrl("/web/login?error") // ??????????????????
                .and()
                .logout()
                .logoutUrl("/web/logout")// ?????????????????????
                //.logoutSuccessHandler(myLogoutSuccessHandler)
                //.logoutSuccessUrl("/web/member/")
                .and()
                .rememberMe() // ????????????????????????
                .rememberMeServices(getRememberMeServices()) // ????????????
                .key(SECRET_KEY) // ???SECRET???????????????TokenBasedRememberMeServices???????????????
                .and()
                //???????????????????????????????????????????????????????????????????????????????????????
                //??????????????????????????????????????????????????????
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/web/403"); // ????????????????????????403
        http.logout().permitAll();
        //http.logout().logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)));
        http.logout().logoutSuccessHandler(myLogoutSuccessHandler);
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);





        /*
        http.headers().
                frameOptions().disable();
        http.csrf().disable();
        http.formLogin()
                .loginPage("/web/login").permitAll() // ?????????????????????
                .and()
                .logout()
                .logoutUrl("/web/logout").permitAll()// ?????????????????????
                .and()
                .rememberMe() // ????????????????????????
                .rememberMeServices(getRememberMeServices()) // ????????????
                .key(SECRET_KEY) // ???SECRET???????????????TokenBasedRememberMeServices???????????????
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/web/403"); // ????????????????????????403
        http.logout().permitAll();
        //http.logout().logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)));
        http.logout().logoutSuccessHandler(myLogoutSuccessHandler);
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        */




    }



    @Bean
    public JwtTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtTokenFilter();
    }


    /**
     * ???????????????cookie????????????????????????????????????????????????????????????
     */
    private TokenBasedRememberMeServices getRememberMeServices() {
        TokenBasedRememberMeServices services = new TokenBasedRememberMeServices(SECRET_KEY, myUserDetailsService);
        services.setCookieName("remember-cookie");
        services.setTokenValiditySeconds(10000); // ??????14???
        return services;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    //??????google ??????
    //https://www.796t.com/content/1548155191.html
    //https://console.cloud.google.com/apis/credentials/consent?project=innotw001


/*
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    }
    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(this.clientRegistrationRepository());
    }
    private ClientRegistration googleClientRegistration() {
        return CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId("839981015892-ptan87gkiobet3gib4svutb6ndkvhft5.apps.googleusercontent.com")
                .clientSecret("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")
                .build();
    }
*/


}
