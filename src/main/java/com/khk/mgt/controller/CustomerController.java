package com.khk.mgt.controller;

import com.khk.mgt.dto.common.CustomerDto;
import com.khk.mgt.dto.common.OnCreate;
import com.khk.mgt.dto.common.OnUpdate;
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
//        model.addAttribute("employeeDto", new EmployeeDto());
//        model.addAttribute("employeeDtoList", new ArrayList<Employee>());
//        model.addAttribute("navStatus", "dashBoard");
//        model.addAttribute("newEmployees", employeeService.newest5Employees());

        return "customerIndex";
    }

    @GetMapping(params = "nav")
    public String navChange(@RequestParam("nav") String navStatus, Model model) {
        model.addAttribute("navStatus", navStatus);

        switch (navStatus) {
            case "dashBoard":
//                model.addAttribute("newEmployees", employeeService.newest5Employees());
                break;
            case "cusView":
                model.addAttribute("viewCustomerDtoList", new ArrayList<CustomerDto>());
                model.addAttribute("searchQuery" , "");
                break;
            case "cusAdd":
                model.addAttribute("addCustomerDto", new CustomerDto());
                break;
            case "cusUpdate":
                model.addAttribute("updateCustomerDto", new CustomerDto());
                model.addAttribute("searchQuery" , "");
                break;
            case "cusDelete":
                model.addAttribute("deleteCustomerDto", new CustomerDto());
                model.addAttribute("searchQuery" , "");
                break;
            default:
        }
        return "customerIndex";
    }

    @PostMapping(value = "/viewsearch",params = "search")
    public String viedSearch(@ModelAttribute("searchQuery") String query,BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "customerIndex";
        }

        List<CustomerDto> viewCustomerDtoList = customerService.searchIdOrName(query);

        model.addAttribute("navStatus", "cusView");
        model.addAttribute("searchQuery" , query);
        model.addAttribute("viewCustomerDtoList", viewCustomerDtoList);
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
    public String customerUpdateSearch(@ModelAttribute("searchQuery") String query,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "cusUpdate");

        if (bindingResult.hasErrors()) {
            return "customerIndex";
        }

        try {
            long id = Long.parseLong(query);
            CustomerDto updateCustomerDto = customerService.getCustomerById(id);
            model.addAttribute("updateCustomerDto", updateCustomerDto);
            model.addAttribute("searchQuery" , "");
            if (updateCustomerDto == null) {
                model.addAttribute("updateSearchNotFound",true);
                model.addAttribute("updateCustomerDto", new CustomerDto());
                model.addAttribute("searchQuery" , query);
            }
        }catch (NumberFormatException e) {
            model.addAttribute("updateSearchNotFound",true);
            model.addAttribute("updateCustomerDto", new CustomerDto());
            model.addAttribute("searchQuery" , query);
        }

        return "customerIndex";
    }

    @PostMapping(value = "/update",params = "submit")
    public String customerUpdate(@Validated(OnUpdate.class) @ModelAttribute("updateCustomerDto") CustomerDto CustomerDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "cusUpdate");

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
    public String customerDeleteSearch(@ModelAttribute("searchQuery") String query,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "cusDelete");

        if (bindingResult.hasErrors()) {
            return "customerIndex";
        }

        try {
            long id = Long.parseLong(query);
            CustomerDto deleteCustomerDto = customerService.getCustomerById(id);
            model.addAttribute("deleteCustomerDto", deleteCustomerDto);
            model.addAttribute("searchQuery" , "");
            if (deleteCustomerDto == null) {
                model.addAttribute("deleteSearchNotFound",true);
                model.addAttribute("deleteCustomerDto", new CustomerDto());
                model.addAttribute("searchQuery" , query);
            }
        }catch (NumberFormatException e) {
            model.addAttribute("deleteSearchNotFound",true);
            model.addAttribute("deleteCustomerDto", new CustomerDto());
            model.addAttribute("searchQuery" , query);
        }

        return "customerIndex";
    }

    @PostMapping(value = "/delete",params = "submit")
    public String customerDelete(@Validated(OnUpdate.class) @ModelAttribute("deleteCustomerDto") CustomerDto customerDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "cusDelete");

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
