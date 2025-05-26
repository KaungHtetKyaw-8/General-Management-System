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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    PassKeyService passKeyService;

    @Autowired
    AccessLogService accessLogService;

    @GetMapping("/view")
    public String logView(Model model) {

        model.addAttribute("logs",accessLogService.getAllLog());

        return "fragments/admin/adminLogTableView";
    }

    @GetMapping("/register")
    public String registerPassKeyView(Model model) {
        model.addAttribute("register",new AccessKeyDto());
        return "fragments/admin/adminPassKeyRegister";
    }

    @PostMapping(value = "/register",params = "submit")
    public String registerPassKey(@Validated @ModelAttribute AccessKeyDto accessKeyDto, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            model.addAttribute("register",new AccessKeyDto());
            return "fragments/admin/adminPassKeyRegister";
        }

        passKeyService.saveAccessKey(accessKeyDto);

        redirectAttributes.addFlashAttribute("saveSuccess",true);
        return "redirect:/admin/register";
    }
}
