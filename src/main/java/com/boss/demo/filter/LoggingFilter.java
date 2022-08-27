package com.boss.demo.filter;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boss.demo.apicontroller.RSAController;
import com.boss.demo.tools.CodeMsg;
import com.boss.demo.tools.R;
import com.boss.demo.tools.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@WebFilter(filterName = "LoggingFilter",urlPatterns = "/api/*")
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RSAService rsaService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);


//        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
//                request.getCharacterEncoding());
//        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
//                response.getCharacterEncoding());

        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
                "utf-8");
        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(),
                "utf-8");



        //記錄Log
        log.info( "FINISHED PROCESSING : METHOD={}; REQUESTURI={}; REQUEST PAYLOAD={}; RESPONSE CODE={}; RESPONSE={}",
                request.getMethod(), request.getRequestURI(), requestBody, response.getStatus(), responseBody);



        //輸出值加校驗
        try {
            response.setHeader("custom_sign", rsaService.signSHA256RSA(responseBody));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        responseWrapper.copyBodyToResponse();
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


}