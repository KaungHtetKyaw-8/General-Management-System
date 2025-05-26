package com.khk.mgt.controller;

import com.khk.mgt.dto.common.AccessKeyDto;
import com.khk.mgt.service.AccessLogService;
import com.khk.mgt.service.PassKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    PassKeyService passKeyService;

    @Autowired
    AccessLogService accessLogService;

    @GetMapping({"","/"})
    public String homeView(Model model) {
        return "fragments/home/homeView";
    }

    @GetMapping("/auth/error")
    public String unauthorizeView(Model model) {
        return "fragments/util/noAuthority";
    }
}
