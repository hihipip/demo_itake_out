package com.boss.demo.controller;


import com.boss.demo.entity.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("web")
public class WebLoginController {

    @GetMapping("/login")
    public String login(Model model){
        return "index/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "index/logout";
    }

    @GetMapping("/403")
    public String noPermission() {
        return "403";
    }

}
