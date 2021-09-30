package com.youngeun.myhome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoardController {
    @GetMapping("/list")
    public String list(){
        return "list";
    }
}
