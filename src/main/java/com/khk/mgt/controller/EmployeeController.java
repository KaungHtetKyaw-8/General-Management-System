package com.khk.mgt.controller;

import com.khk.mgt.dto.common.EmployeeDto;
import com.khk.mgt.dto.common.OnCreate;
import com.khk.mgt.dto.common.OnUpdate;
import com.khk.mgt.service.EmployeeService;
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
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping({"","/","home","index"})
    public String index(Model model) {
        model.addAttribute("navStatus", "dashBoard");
        model.addAttribute("newEmployees", employeeService.newest5Employees());

        return "employeeIndex";
    }

    @GetMapping(params = "nav")
    public String navChange(@RequestParam("nav") String navStatus, Model model) {
        model.addAttribute("navStatus", navStatus);

        switch (navStatus) {
            case "dashBoard":
                model.addAttribute("newEmployees", employeeService.newest5Employees());
                break;
            case "empView":
                model.addAttribute("viewEmployeesDtoList", new ArrayList<EmployeeDto>());
                model.addAttribute("searchQuery" , "");
                break;
            case "empAdd":
                model.addAttribute("addEmployeeDto", new EmployeeDto());
                break;
            case "empUpdate":
                model.addAttribute("updateEmployeeDto", new EmployeeDto());
                model.addAttribute("searchQuery" , "");
                break;
            case "empDelete":
                model.addAttribute("deleteEmployeeDto", new EmployeeDto());
                model.addAttribute("searchQuery" , "");
                break;
            default:
        }
        return "employeeIndex";
    }

    @PostMapping(value = "/viewsearch",params = "search")
    public String viedSearch(@ModelAttribute("searchQuery") String query,BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "employeeIndex";
        }

        List<EmployeeDto> viewEmployeesDtoList = employeeService.searchIdOrName(query);
        System.out.println("Search Result : " + viewEmployeesDtoList);
        model.addAttribute("navStatus", "empView");
        model.addAttribute("searchQuery" , query);
        model.addAttribute("viewEmployeesDtoList", viewEmployeesDtoList);
        return "employeeIndex";
    }

    @PostMapping(value = "/add",params = "submit")
    public String employeeAdd(@Validated(OnCreate.class) @ModelAttribute("addEmployeeDto") EmployeeDto employeeDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "empAdd");

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("registerFail", true);
            return "employeeIndex";
        }else{
            // process the form submission
            employeeService.saveEmployee(employeeDto);

            model.addAttribute("registerSuccess", true);
            model.addAttribute("employeeDto", new EmployeeDto());
            return "employeeIndex";
        }
    }

    @PostMapping(value = "/update",params = "search")
    public String employeeUpdateSearch(@ModelAttribute("searchQuery") String query,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "empUpdate");

        if (bindingResult.hasErrors()) {
            return "employeeIndex";
        }

        try {
            long id = Long.parseLong(query);
            EmployeeDto updateEmployeesDto = employeeService.getEmployeeById(id);
            model.addAttribute("updateEmployeeDto", updateEmployeesDto);
            model.addAttribute("searchQuery" , "");
            if (updateEmployeesDto == null) {
                model.addAttribute("updateSearchNotFound",true);
                model.addAttribute("updateEmployeeDto", new EmployeeDto());
                model.addAttribute("searchQuery" , query);
            }
        }catch (NumberFormatException e) {
            model.addAttribute("updateSearchNotFound",true);
            model.addAttribute("updateEmployeeDto", new EmployeeDto());
            model.addAttribute("searchQuery" , query);
        }

        return "employeeIndex";
    }

    @PostMapping(value = "/update",params = "submit")
    public String employeeUpdate(@Validated(OnUpdate.class) @ModelAttribute("updateEmployeeDto") EmployeeDto employeeDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "empUpdate");

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("updateFail", true);
            return "employeeIndex";
        }else{
            // process the form submission
            employeeService.updateEmployee(employeeDto);
            model.addAttribute("updateSuccess", true);
            model.addAttribute("updateEmployeeDto", new EmployeeDto());
            return "employeeIndex";
        }
    }


    @PostMapping(value = "/delete",params = "search")
    public String employeeDeleteSearch(@ModelAttribute("searchQuery") String query,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "empDelete");

        if (bindingResult.hasErrors()) {
            return "employeeIndex";
        }

        try {
            long id = Long.parseLong(query);
            EmployeeDto deleteEmployeesDto = employeeService.getEmployeeById(id);
            model.addAttribute("deleteEmployeeDto", deleteEmployeesDto);
            model.addAttribute("searchQuery" , "");
            if (deleteEmployeesDto == null) {
                model.addAttribute("deleteSearchNotFound",true);
                model.addAttribute("deleteEmployeeDto", new EmployeeDto());
                model.addAttribute("searchQuery" , query);
            }
        }catch (NumberFormatException e) {
            model.addAttribute("deleteSearchNotFound",true);
            model.addAttribute("deleteEmployeeDto", new EmployeeDto());
            model.addAttribute("searchQuery" , query);
        }

        return "employeeIndex";
    }

    @PostMapping(value = "/delete",params = "submit")
    public String employeeDelete(@Validated(OnUpdate.class) @ModelAttribute("deleteEmployeeDto") EmployeeDto employeeDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "empDelete");

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("deleteFail", true);
            return "employeeIndex";
        }else{
            // process the form submission
            employeeService.deleteEmployee(employeeDto.getId());
            model.addAttribute("deleteSuccess", true);
            model.addAttribute("deleteEmployeeDto", new EmployeeDto());
            return "employeeIndex";
        }
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
}
