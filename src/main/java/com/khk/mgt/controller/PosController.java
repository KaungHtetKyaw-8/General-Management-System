package com.khk.mgt.controller;

import com.khk.mgt.dto.common.*;
import com.khk.mgt.service.OrderService;
import com.khk.mgt.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/pos")
public class PosController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private OrderService orderService;


    @GetMapping({"","/","home","index"})
    public String index(Model model) {
        model.addAttribute("orderDto", new PosOrderDto());
        model.addAttribute("productCategories", productCategoryService.getAllProductCategories());
        return "posIndex";
    }

    @PostMapping(value = "/save",params = "submit")
    public String vendorAdd(@Validated @ModelAttribute("orderDto") PosOrderDto posOrderDto, BindingResult bindingResult, Model model) {
        model.addAttribute("productCategories", productCategoryService.getAllProductCategories());

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("orderFail", true);
            return "posIndex";
        }else{
            // process the form submission
            orderService.saveOrder(posOrderDto);
            model.addAttribute("orderConfirmed", true);
            return "posIndex";
        }
    }

}
