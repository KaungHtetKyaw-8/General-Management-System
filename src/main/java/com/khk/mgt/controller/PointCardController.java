package com.khk.mgt.controller;

import com.khk.mgt.dto.common.*;
import com.khk.mgt.service.CustomerService;
import com.khk.mgt.service.PointCardCategoryService;
import com.khk.mgt.service.PointCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer/point")
public class PointCardController {

    @Autowired
    private PointCardService pointCardService;

    @Autowired
    private PointCardCategoryService pointCardCategoryService;

    @Autowired
    private CustomerService customerService;

    @GetMapping({"","/","/home"})
    public String dashboard(Model model) {
        return "customerIndex";
    }

    @GetMapping({"","/","home","index"})
    public String index(Model model) {
        return "redirect:/customer";
    }

    @GetMapping(params = "nav")
    public String navChange(@RequestParam("nav") String navStatus, Model model) {
        model.addAttribute("navStatus", navStatus);

        switch (navStatus) {
            case "pointView":
                model.addAttribute("viewPointCardDtoList", new ArrayList<PointCardDto>());
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            case "pointAdd":
                model.addAttribute("addPointCardDto", new PointCardDto());
                model.addAttribute("pointCardCategoryList", pointCardCategoryService.getTypeAll());
                break;
            case "pointUpdate":
                model.addAttribute("updatePointCardDto", new PointCardDto());
                model.addAttribute("pointCardCategoryList", pointCardCategoryService.getTypeAll());
                break;
            case "pointDelete":
                model.addAttribute("deletePointCardDto", new PointCardDto());
                break;
            default:
        }
        return "customerIndex";
    }

    @PostMapping(value = "/viewsearch",params = "search")
    public String viewSearch(@Valid @ModelAttribute("searchQuery")SearchDto searchDto,
                             BindingResult bindingResult,
                             Model model) {

        model.addAttribute("navStatus", "pointView");
        model.addAttribute("searchQuery" , searchDto);

        if (bindingResult.hasErrors()) {
            return "customerIndex";
        }

        long queryId;

        try {
            queryId = Long.parseLong(searchDto.getValue());
        }catch (NumberFormatException e) {
            model.addAttribute("viewPointCardDtoList", new ArrayList<PointCardDto>());
            model.addAttribute("viewFailForNumber", true);
            return "customerIndex";
        }
        List<PointCardDto> result = new ArrayList<>();

        switch (searchDto.getSearchType()) {
            // By Customer ID
            case "1" :
                result = customerService.getPointCardsByCustomerId(queryId);
                break;
            // By Point Card ID
            case "2" :
                PointCardDto dto = pointCardService.getPointCardById(queryId);
                if (dto != null) {
                    result.add(dto);
                }
                break;
            default:
                result = new ArrayList<>();
        }

        if (result != null && !result.isEmpty()) {
            model.addAttribute("viewPointCardDtoList", result);
        }else{
            model.addAttribute("viewPointCardDtoList", new ArrayList<PointCardDto>());
            model.addAttribute("viewFail", true);
        }

        return "customerIndex";
    }

    @PostMapping(value = "/add",params = "submit")
    public String pointCardAdd(@Validated(OnCreate.class) @ModelAttribute("addPointCardDto") PointCardDto pointCardDto,
                              BindingResult bindingResult,
                              Model model) {
        model.addAttribute("navStatus", "pointAdd");

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("pointCardCategoryList", pointCardCategoryService.getTypeAll());
            model.addAttribute("registerFail", true);
            return "customerIndex";
        }else{
            // process the form submission
            pointCardService.savePointCard(pointCardDto);
            model.addAttribute("registerSuccess", true);
            model.addAttribute("addPointCardDto", new PointCardDto());
            model.addAttribute("pointCardCategoryList", pointCardCategoryService.getTypeAll());
            return "customerIndex";
        }
    }

    @PostMapping(value = "/update",params = "submit")
    public String pointCardUpdate(@Validated(OnUpdate.class) @ModelAttribute("updatePointCardDto") PointCardDto pointCardDto,
                               BindingResult bindingResult,
                               Model model) {
        model.addAttribute("navStatus", "pointUpdate");

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("pointCardCategoryList", pointCardCategoryService.getTypeAll());
            model.addAttribute("updateFail", true);
            return "customerIndex";
        }else{
            // process the form submission
            pointCardService.updatePointCard(pointCardDto);
            model.addAttribute("updateSuccess", true);
            model.addAttribute("updatePointCardDto", new PointCardDto());
            model.addAttribute("pointCardCategoryList", pointCardCategoryService.getTypeAll());
            return "customerIndex";
        }
    }


    @PostMapping(value = "/delete",params = "submit")
    public String pointCardDelete(@Validated(OnDelete.class) @ModelAttribute("deletePointCardDto") PointCardDto pointCardDto,
                                  BindingResult bindingResult,
                                  Model model) {
        model.addAttribute("navStatus", "pointDelete");

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("deleteFail", true);
            return "customerIndex";
        }else{
            // process the form submission
            pointCardService.deletePointCard(pointCardDto);
            model.addAttribute("deleteSuccess", true);
            model.addAttribute("deletePointCardDto", new PointCardDto());
            return "customerIndex";
        }
    }
}
