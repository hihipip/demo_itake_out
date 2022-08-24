package com.boss.demo.line;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class LineService {

    private RestTemplate restTemplate = new RestTemplate();

    public void LineNotify(String access_token,String message) throws URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization","Bearer "+access_token);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("message", message);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        String rs = this.restTemplate.postForObject(new URI("https://notify-api.line.me/api/notify"), request , String.class);
        System.out.println(rs);
    }


}
