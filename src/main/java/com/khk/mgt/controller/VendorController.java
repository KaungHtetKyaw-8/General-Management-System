package com.khk.mgt.controller;

import com.khk.mgt.dto.common.*;
import com.khk.mgt.service.VendorService;
import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;


    @GetMapping({"","/","home","index"})
    public String index(Model model) {
        return "redirect:/inventory";
    }

    @GetMapping(params = "nav")
    public String navChange(@RequestParam("nav") String navStatus, Model model) {
        model.addAttribute("navStatus", navStatus);

        switch (navStatus) {
            case "vendorView":
                model.addAttribute("viewVendorDtoList", new ArrayList<VendorDto>());
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            case "vendorAdd":
                model.addAttribute("addVendorDto", new VendorDto());
                break;
            case "vendorUpdate":
                model.addAttribute("updateVendorDto", new VendorDto());
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            case "vendorDelete":
                model.addAttribute("deleteVendorDto", new VendorDto());
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            default:
        }
        return "inventoryIndex";
    }

    @PostMapping(value = "/viewsearch",params = "search")
    public String viewSearch(@ModelAttribute("searchQuery") SearchDto searchDto, BindingResult bindingResult, Model model, ServletRequest servletRequest) {

        model.addAttribute("navStatus", "vendorView");
        model.addAttribute("searchQuery" , searchDto);

        if (bindingResult.hasErrors()) {
            return "inventoryIndex";
        }

        long queryId = 0L;

        if (!searchDto.getSearchType().equals("1")){
            try {
                queryId = Long.parseLong(searchDto.getValue());
            }catch (NumberFormatException e) {
                model.addAttribute("viewVendorDtoList", new ArrayList<VendorDto>());
                model.addAttribute("viewFailForNumber", true);
                return "inventoryIndex";
            }
        }

        List<VendorDto> result = new ArrayList<>();

        switch (searchDto.getSearchType()) {
            // By Customer ID
            case "1" :
                result = vendorService.getAllVendor();
                break;
            // By Point Card ID
            case "2" :
                result.add(vendorService.getVendorById(queryId));
                break;
            default:
                result = new ArrayList<>();
        }

        model.addAttribute("viewVendorDtoList", result);
        return "inventoryIndex";
    }

    @PostMapping(value = "/add",params = "submit")
    public String vendorAdd(@Validated(OnCreate.class) @ModelAttribute("addVendorDto") VendorDto vendorDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "vendorAdd");

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("registerFail", true);
            return "inventoryIndex";
        }else{
            // process the form submission
            vendorService.saveVendor(vendorDto);
            model.addAttribute("registerSuccess", true);
            model.addAttribute("addVendorDto", new VendorDto());
            return "inventoryIndex";
        }
    }

    @PostMapping(value = "/update",params = "search")
    public String vendorUpdateSearch(@ModelAttribute("searchQuery") SearchDto searchDto,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "vendorUpdate");
        model.addAttribute("searchQuery" , new SearchDto());

        if (bindingResult.hasErrors()) {
            return "inventoryIndex";
        }

        try {
            long id = Long.parseLong(searchDto.getValue());
            VendorDto updateVendorDto = vendorService.getVendorById(id);
            model.addAttribute("updateVendorDto", updateVendorDto);
            if (updateVendorDto == null) {
                model.addAttribute("updateSearchNotFound",true);
                model.addAttribute("updateVendorDto", new VendorDto());
            }
        }catch (NumberFormatException e) {
            model.addAttribute("updateSearchNotFound",true);
            model.addAttribute("updateVendorDto", new VendorDto());
        }

        return "inventoryIndex";
    }

    @PostMapping(value = "/update",params = "submit")
    public String customerUpdate(@Validated(OnUpdate.class) @ModelAttribute("updateVendorDto") VendorDto vendorDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "vendorUpdate");
        model.addAttribute("searchQuery" , new SearchDto());

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("updateFail", true);
            return "inventoryIndex";
        }else{
            // process the form submission
            vendorService.updateVendor(vendorDto);
            model.addAttribute("updateSuccess", true);
            model.addAttribute("updateVendorDto", new VendorDto());
            return "inventoryIndex";
        }
    }


    @PostMapping(value = "/delete",params = "search")
    public String customerDeleteSearch(@ModelAttribute("searchQuery") SearchDto searchDto,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "vendorDelete");
        model.addAttribute("searchQuery" , new SearchDto());

        if (bindingResult.hasErrors()) {
            return "inventoryIndex";
        }

        try {
            long id = Long.parseLong(searchDto.getValue());
            VendorDto deleteVendorDto = vendorService.getVendorById(id);
            model.addAttribute("deleteVendorDto", deleteVendorDto);
            if (deleteVendorDto == null) {
                model.addAttribute("deleteSearchNotFound",true);
                model.addAttribute("deleteVendorDto", new VendorDto());
            }
        }catch (NumberFormatException e) {
            model.addAttribute("deleteSearchNotFound",true);
            model.addAttribute("deleteVendorDto", new VendorDto());
        }

        return "inventoryIndex";
    }

    @PostMapping(value = "/delete",params = "submit")
    public String customerDelete(@Validated(OnUpdate.class) @ModelAttribute("deleteVendorDto") VendorDto vendorDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "vendorDelete");
        model.addAttribute("searchQuery" , new SearchDto());

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("deleteFail", true);
            return "inventoryIndex";
        }else{
            // process the form submission
            vendorService.deleteVendor(vendorDto.getId());
            model.addAttribute("deleteSuccess", true);
            model.addAttribute("deleteVendorDto", new VendorDto());
            return "inventoryIndex";
        }
    }
}
