package com.boss.demo.controller.front;


import com.boss.demo.line.LineService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequestMapping("/front")
public class LineController {

    @Value("${line.clientId}")
    private String clientId;
    @Value("${line.clientSecret}")
    private String clientSecret;
    @Value("${line.clientReturnUrl}")
    private String clientReturnUrl;

    @Autowired
    private LineService lineService;

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/sendLineNotify")
    @ResponseBody
    public String sendLineNotify() throws URISyntaxException {
        this.lineService.LineNotify("ttcXEtpEU8L3JuER3jI0WEeNI29TvwiCrvqpDMFg0ww","Hello 您好");
        return "OK";
    }


    @GetMapping("/linenotify")
    public void getLineNotify(HttpServletResponse response) throws IOException {
        String url = "https://notify-bot.line.me/oauth/authorize?response_type=code&scope=notify&response_mode=form_post&client_id="+clientId+"&redirect_uri="+clientReturnUrl+"&state=state";
        response.sendRedirect(url);
    }

    @PostMapping("/linenotify")
    @ResponseBody
    public String postLineNotify(@RequestParam String code){
        System.out.println(code);

        try {
            String url = "https://notify-bot.line.me/oauth/token";
            String data = "grant_type=authorization_code&redirect_uri="+clientReturnUrl+"&client_id="+clientId+"&client_secret="+clientSecret+"&code="+code;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            map.add("grant_type", "authorization_code");
            map.add("redirect_uri", clientReturnUrl);
            map.add("client_id", clientId);
            map.add("client_secret", clientSecret);
            map.add("code", code);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
            String rs = this.restTemplate.postForObject(new URI(url), request , String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String,Object> hash = objectMapper.readValue(rs, new TypeReference<HashMap<String,Object>>() {});
            System.out.println(hash.get("access_token"));
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
