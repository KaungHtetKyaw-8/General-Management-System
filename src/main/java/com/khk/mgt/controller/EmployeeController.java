package com.khk.mgt.controller;

import com.khk.mgt.ds.Employee;
import com.khk.mgt.dto.EmployeeTableDto;
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
        EmployeeTableDto employeeTableDto = new EmployeeTableDto();
        employeeTableDto.setHeader(getCombinedHeaders());

        model.addAttribute("employeeTableData", employeeTableDto);
        return "index";
    }

    @PostMapping(params = "addRow")
    public String addRow(@Valid @ModelAttribute("employeeTableData") EmployeeTableDto employeeTableDto, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Return Value Empty");
            return "index";
        } else {
            employeeTableDto.getData().add(new Employee().init());
            employeeTableDto.setHeader(getCombinedHeaders());

            model.addAttribute("employeeTableData", employeeTableDto);
            return "index";
        }
    }

    @PostMapping(params = "submit")
    public String submit(@Valid @ModelAttribute("employeeTableData") EmployeeTableDto employeeTableDto, Model model) {
        // process the form submission
        System.out.println("Submitted: " + employeeTableDto.getData());
        employeeService.saveEmployees(employeeTableDto.getData());
        return "redirect:/";
    }

    private List<String> getCombinedHeaders() {
        List<String> headers = new ArrayList<>();
        headers.addAll(tableHeaderDto.getPersonHeader());
        headers.addAll(tableHeaderDto.getAddressHeader());
        headers.addAll(tableHeaderDto.getEmployeeHeader());
        return headers;
    }

}
