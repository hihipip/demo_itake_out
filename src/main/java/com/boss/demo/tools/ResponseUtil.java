package com.boss.demo.tools;

import com.boss.demo.filter.RSAService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseUtil {


    public static void out(HttpServletResponse response, R r) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        OutputStream outputStream = response.getOutputStream();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        String out = objectMapper.writeValueAsString(r);
        outputStream.write(out.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }

    public static void out(RSAService rasService,HttpServletResponse response, R r) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        OutputStream outputStream = response.getOutputStream();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        String out = objectMapper.writeValueAsString(r);
        response.addHeader("custom_sign",rasService.signSHA256RSA(out));
        outputStream.write(out.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }


}
