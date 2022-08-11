package com.boss.demo.apicontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWordController {

    @RequestMapping("/")
    String index() {
        return "Hello World!";
    }
}
