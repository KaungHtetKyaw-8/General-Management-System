package com.khk.mgt.controller;

import com.khk.mgt.ds.Employee;
import com.khk.mgt.dto.EmployeeDto;
import com.khk.mgt.dto.TableHeaderList;
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

    @Autowired
    private TableHeaderList tableHeaderDto;

    @GetMapping
    public String index(Model model) {
        EmployeeDto employeeData = new EmployeeDto();
        employeeData.setHeader(getCombinedHeaders());

        model.addAttribute("employeeData", employeeData);
        return "employeeIndex";
    }

    @PostMapping(params = "addRow")
    public String addRow(@Valid @ModelAttribute("employeeData") EmployeeDto employeeData, BindingResult bindingResult, Model model) {
        System.out.println("Row Add Process is working");
        if (bindingResult.hasErrors()) {
            System.out.println("Return Value Empty");
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            return "employeeIndex";
        } else {
            employeeData.getListData().add(new Employee().init());
            employeeData.setHeader(getCombinedHeaders());

            System.out.println("Employee Data : " + employeeData.getListData());

            model.addAttribute("employeeData", employeeData);
            return "employeeIndex";
        }
    }

    @PostMapping(params = "submit")
    public String submit(@Valid @ModelAttribute("employeeData") EmployeeDto employeeDto,BindingResult bindingResult, Model model) {
        System.out.println("Submit Process is working");
        if (bindingResult.hasErrors()) {
            System.out.println("Return Value Empty");
        }
        // process the form submission
        System.out.println("Submitted: " + employeeDto.getData().toString());
        employeeService.saveEmployee(employeeDto.getData());
        return "redirect:/employees";
    }

    private List<String> getCombinedHeaders() {
        List<String> headers = new ArrayList<>();
        headers.addAll(tableHeaderDto.getPersonHeader());
        headers.addAll(tableHeaderDto.getAddressHeader());
        headers.addAll(tableHeaderDto.getEmployeeHeader());
        return headers;
    }

}
