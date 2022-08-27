package com.boss.demo.controller.front;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/oauth2")
public class OAuth2Controller {



    //https://www.codeleading.com/article/59583544280/
    @GetMapping("/oauth_login")
    public String getLoginPage(Model model, HttpServletResponse response) throws IOException {
        String url = "https://accounts.google.com/o/oauth2/v2/auth";
        url += "?client_id=839981015892-ptan87gkiobet3gib4svutb6ndkvhft5.apps.googleusercontent.com";
        url += "&redirect_uri="+java.net.URLEncoder.encode("http://localhost:8080/oauth2/getinfo","utf-8");
        url += "&response_type=code";
        url += "&scope="+java.net.URLEncoder.encode("https://www.googleapis.com/auth/userinfo.profile","utf-8");
        url += "&access_type=offline";
        url += "&include_granted_scopes=true";
        response.sendRedirect(url);
        return null;
    }
    //http://localhost:8080/oauth2/oauth_login
    private RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/getinfo")
    @ResponseBody
    public String getGoogleAccessToken(String code) throws JsonProcessingException, URISyntaxException {
        String url = "https://www.googleapis.com/oauth2/v4/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "authorization_code");
        map.add("redirect_uri", "http://localhost:8080/oauth2/getinfo");
        map.add("client_id", "839981015892-ptan87gkiobet3gib4svutb6ndkvhft5.apps.googleusercontent.com");
        map.add("client_secret", "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");
        map.add("code", code);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        String rs = this.restTemplate.postForObject(new URI(url), request , String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> hash = objectMapper.readValue(rs, new TypeReference<HashMap<String,Object>>() {});
        System.out.println(hash.get("access_token"));
        getUserInfo(hash.get("access_token").toString());
        return rs;
    }
    public void getUserInfo(String access_token) throws URISyntaxException {
        String url = "https://www.googleapis.com/oauth2/v2/userinfo?access_token="+access_token;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        String rs = this.restTemplate.getForObject(new URI(url), String.class);
        System.out.println(rs);
    }














}
