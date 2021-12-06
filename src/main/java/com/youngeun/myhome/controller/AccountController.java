package com.youngeun.myhome.controller;

import com.youngeun.myhome.model.User;
import com.youngeun.myhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "/account/login";
    }

    @GetMapping("/register")
    public String register(){
        return "/account/register";
    }

    @PostMapping("/register")
    public String register(User user){
        userService.save(user,1);
        return "redirect:/";
    }
    @PostMapping("/admin")
    public String registerAdmin(@RequestBody User user) {
        try {
            userService.save(user, 2);
        } catch (DataIntegrityViolationException e) {
            return "Username is already in use";
        }
        return "Registered";
    }

    @PostMapping("/institution")
    public String registerInstitution(@RequestBody User user) {
        try {
            userService.save(user, 3);
        }
        catch (DataIntegrityViolationException e) {
            return "Username is already in use";
        }
        return "Registered";
    }
}
