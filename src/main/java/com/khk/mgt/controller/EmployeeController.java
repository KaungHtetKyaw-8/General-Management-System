package com.khk.mgt.controller;

import com.khk.mgt.ds.Employee;
import com.khk.mgt.dto.common.EmployeeDto;
import com.khk.mgt.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping
    public String index(Model model) {
//        model.addAttribute("employeeDto", new EmployeeDto());
//        model.addAttribute("employeeDtoList", new ArrayList<Employee>());
        model.addAttribute("navStatus", "dashBoard");
        return "employeeIndex";
    }

    @GetMapping(params = "nav")
    public String navChange(@RequestParam("nav") String navStatus, Model model) {
        System.out.println("Change Nav : " + navStatus);
        model.addAttribute("navStatus", navStatus);

        switch (navStatus) {
            case "empAdd":
                model.addAttribute("employeeDto", new EmployeeDto());
                break;
            default:
        }

        return "employeeIndex";

    }

    @PostMapping(params = "addRow")
    public String addRow(@Valid @ModelAttribute("employeeDtoList") List<EmployeeDto> employeeDtoList, BindingResult bindingResult, Model model) {
        System.out.println("Row Add Process is working");
        if (bindingResult.hasErrors()) {
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            return "employeeIndex";
        } else {
            employeeDtoList.add(new EmployeeDto());
            System.out.println("Employee Data : " + employeeDtoList);
            model.addAttribute("employeeDtoList", employeeDtoList);
            return "employeeIndex";
        }
    }

    @PostMapping(params = "submit")
    public String submit(@Valid @ModelAttribute("employeeDto") EmployeeDto employeeDto,BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            System.out.println("Return Value : " + employeeDto);
            return "employeeIndex";
        }else{
            // process the form submission
            System.out.println("Submitted: " + employeeDto);
            return "redirect:/employees";
        }
    }
}
