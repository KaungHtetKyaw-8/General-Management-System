package com.khk.mgt.controller;

import com.khk.mgt.dto.common.*;
import com.khk.mgt.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping({"","/","home","index"})
    public String index(Model model) {
        model.addAttribute("navStatus", "dashBoard");
        model.addAttribute("newCustomers", customerService.getTop5RecentCustomers());

        return "customerIndex";
    }

    @GetMapping(params = "nav")
    public String navChange(@RequestParam("nav") String navStatus, Model model) {
        model.addAttribute("navStatus", navStatus);

        switch (navStatus) {
            case "dashBoard":
                model.addAttribute("newCustomers", customerService.getTop5RecentCustomers());
                break;
            case "cusView":
                model.addAttribute("viewCustomerDtoList", new ArrayList<CustomerDto>());
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            case "cusAdd":
                model.addAttribute("addCustomerDto", new CustomerDto());
                break;
            case "cusUpdate":
                model.addAttribute("updateCustomerDto", new CustomerDto());
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            case "cusDelete":
                model.addAttribute("deleteCustomerDto", new CustomerDto());
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            default:
        }
        return "customerIndex";
    }

    @PostMapping(value = "/viewsearch",params = "search")
    public String viedSearch(@ModelAttribute("searchQuery") SearchDto searchDto,
                             BindingResult bindingResult,
                             Model model) {

        model.addAttribute("navStatus", "cusView");
        model.addAttribute("searchQuery" , searchDto);

        if (bindingResult.hasErrors()) {
            return "customerIndex";
        }

        long queryId = 0L;

        if (!searchDto.getSearchType().equals("1")){
            try {
                queryId = Long.parseLong(searchDto.getValue());
            }catch (NumberFormatException e) {
                model.addAttribute("viewCustomerDtoList", new ArrayList<PointCardDto>());
                model.addAttribute("viewFailForNumber", true);
                return "customerIndex";
            }
        }

        List<CustomerDto> result = new ArrayList<>();

        switch (searchDto.getSearchType()) {
            // All
            case "1" :
                result = customerService.getAllCustomers();
                break;
            // By Customer ID
            case "2" :
                CustomerDto dto = customerService.getCustomerById(queryId);
                if (dto != null) {
                    result.add(dto);
                }
                break;
            default:
                result = new ArrayList<>();
        }

        if (result != null && !result.isEmpty()) {
            model.addAttribute("viewCustomerDtoList", result);
        }else{
            model.addAttribute("viewCustomerDtoList", new ArrayList<CustomerDto>());
            model.addAttribute("viewFail", true);
        }

        return "customerIndex";
    }

    @PostMapping(value = "/add",params = "submit")
    public String customerAdd(@Validated(OnCreate.class) @ModelAttribute("addCustomerDto") CustomerDto customerDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "cusAdd");

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("registerFail", true);
            return "customerIndex";
        }else{
            // process the form submission
            customerService.saveCustomer(customerDto);
            model.addAttribute("registerSuccess", true);
            model.addAttribute("addCustomerDto", new CustomerDto());
            return "customerIndex";
        }
    }

    @PostMapping(value = "/update",params = "search")
    public String customerUpdateSearch(@Validated @ModelAttribute("searchQuery") SearchDto searchDto,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "cusUpdate");
        model.addAttribute("searchQuery" , searchDto);

        if (bindingResult.hasErrors()) {
            model.addAttribute("updateCustomerDto", new CustomerDto());
            return "customerIndex";
        }

        try {
            long id = Long.parseLong(searchDto.getValue());
            CustomerDto updateCustomerDto = customerService.getCustomerById(id);
            model.addAttribute("updateCustomerDto", updateCustomerDto);
            if (updateCustomerDto == null) {
                model.addAttribute("updateSearchNotFound",true);
                model.addAttribute("updateCustomerDto", new CustomerDto());
            }
        }catch (NumberFormatException e) {
            model.addAttribute("updateSearchNotFound",true);
            model.addAttribute("updateCustomerDto", new CustomerDto());
        }

        return "customerIndex";
    }

    @PostMapping(value = "/update",params = "submit")
    public String customerUpdate(@Validated(OnUpdate.class) @ModelAttribute("updateCustomerDto") CustomerDto CustomerDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "cusUpdate");
        model.addAttribute("searchQuery" , new SearchDto());

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("updateFail", true);
            return "customerIndex";
        }else{
            // process the form submission
            customerService.updateCustomer(CustomerDto);
            model.addAttribute("updateSuccess", true);
            model.addAttribute("updateCustomerDto", new CustomerDto());
            return "customerIndex";
        }
    }


    @PostMapping(value = "/delete",params = "search")
    public String customerDeleteSearch(@Validated @ModelAttribute("searchQuery") SearchDto searchDto,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "cusDelete");
        model.addAttribute("searchQuery" , searchDto);

        if (bindingResult.hasErrors()) {
            model.addAttribute("deleteCustomerDto", new CustomerDto());
            return "customerIndex";
        }

        try {
            long id = Long.parseLong(searchDto.getValue());
            CustomerDto deleteCustomerDto = customerService.getCustomerById(id);
            model.addAttribute("deleteCustomerDto", deleteCustomerDto);
            if (deleteCustomerDto == null) {
                model.addAttribute("deleteSearchNotFound",true);
                model.addAttribute("deleteCustomerDto", new CustomerDto());
            }
        }catch (NumberFormatException e) {
            model.addAttribute("deleteSearchNotFound",true);
            model.addAttribute("deleteCustomerDto", new CustomerDto());
        }

        return "customerIndex";
    }

    @PostMapping(value = "/delete",params = "submit")
    public String customerDelete(@Validated(OnUpdate.class) @ModelAttribute("deleteCustomerDto") CustomerDto customerDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "cusDelete");
        model.addAttribute("searchQuery" , new SearchDto());

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("deleteFail", true);
            return "customerIndex";
        }else{
            // process the form submission
            customerService.deleteCustomer(customerDto.getId());
            System.out.println("deleteSuccess");
            model.addAttribute("deleteSuccess", true);
            model.addAttribute("deleteCustomerDto", new CustomerDto());
            return "customerIndex";
        }
    }
}
