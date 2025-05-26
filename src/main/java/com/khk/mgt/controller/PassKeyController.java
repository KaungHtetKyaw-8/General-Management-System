package com.khk.mgt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PassKeyController {
    @GetMapping("/passkey")
    public String passKeyForm() {
        return "securityIndex";
    }

}

