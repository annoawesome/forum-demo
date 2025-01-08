package com.hoongseth.forumdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}
