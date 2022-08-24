package com.boss.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class WebErrorController implements ErrorController {

    @GetMapping(value = "/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Exception exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        model.addAttribute("status",status);
        model.addAttribute("exception",exception);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error";
            }
        }

        return "error";
    }
}
