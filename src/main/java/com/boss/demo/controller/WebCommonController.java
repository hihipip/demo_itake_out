package com.boss.demo.controller;


import com.boss.demo.security.roles.IsAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;


@Controller
@RequestMapping("web/common")
public class WebCommonController {

    @Value("${demo.path}")
    private String basePath;

    @GetMapping("/upload")
    public String get(){
        return "index/upload";
    };

    @IsAdmin
    @PostMapping("/upload")
    public String upload(MultipartFile file, Model model) {
        String originName = file.getOriginalFilename();
        String ext = originName.substring(originName.lastIndexOf("."));
        String fileName = UUID.randomUUID() + ext;
        File dir = new File(basePath);
        if (!dir.exists()) dir.mkdirs();
        try {
            file.transferTo(new File(basePath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        model.addAttribute("fileName",fileName);
        return "index/upload";
    }

    @GetMapping("/download")
    public void download(@RequestParam String fileName, HttpServletResponse response){
        try{
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+fileName));
            response.setContentType("image/jpg");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            int len=0;
            byte[] bytes = new byte[1024];
            while( (len=fileInputStream.read(bytes))!=-1 ){
                servletOutputStream.write(bytes,0,len);
                servletOutputStream.flush();
            }
            servletOutputStream.close();
            fileInputStream.close();
        }catch (IOException e){
            e.printStackTrace();;
        }
    }







}
