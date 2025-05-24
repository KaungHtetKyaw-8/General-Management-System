package com.khk.mgt.controller;

import com.khk.mgt.dto.common.*;
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
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            case "empAdd":
                model.addAttribute("addEmployeeDto", new EmployeeDto());
                break;
            case "empUpdate":
                model.addAttribute("updateEmployeeDto", new EmployeeDto());
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            case "empDelete":
                model.addAttribute("deleteEmployeeDto", new EmployeeDto());
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            default:
        }
        return "employeeIndex";
    }

    @PostMapping(value = "/viewsearch",params = "search")
    public String viedSearch(@ModelAttribute("searchQuery") SearchDto searchDto, BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "empView");
        model.addAttribute("searchQuery" , searchDto);

        if (bindingResult.hasErrors()) {
            return "employeeIndex";
        }
        long queryId = 0L;

        if (!searchDto.getSearchType().equals("1")){
            try {
                queryId = Long.parseLong(searchDto.getValue());
            }catch (NumberFormatException e) {
                model.addAttribute("viewEmployeesDtoList", new ArrayList<VendorDto>());
                model.addAttribute("viewFailForNumber", true);
                return "employeeIndex";
            }
        }

        List<EmployeeDto> result = new ArrayList<>();

        switch (searchDto.getSearchType()) {
            // ALL
            case "1" :
                result = employeeService.getAllEmployees();
                break;
            // By Employee ID
            case "2" :
                EmployeeDto employeeDto = employeeService.getEmployeeById(queryId);
                if (employeeDto != null) {
                    result.add(employeeDto);
                }
                break;
            default:
                result = new ArrayList<>();
        }

        if (result != null && !result.isEmpty()) {
            model.addAttribute("viewEmployeesDtoList", result);
        }else{
            model.addAttribute("viewEmployeesDtoList", new ArrayList<EmployeeDto>());
            model.addAttribute("viewFail", true);
        }

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
    public String employeeUpdateSearch(@Validated @ModelAttribute("searchQuery") SearchDto searchDto,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "empUpdate");
        model.addAttribute("searchQuery" , searchDto);

        if (bindingResult.hasErrors()) {
            model.addAttribute("updateEmployeeDto", new EmployeeDto());
            return "employeeIndex";
        }

        try {
            long id = Long.parseLong(searchDto.getValue());
            EmployeeDto updateEmployeesDto = employeeService.getEmployeeById(id);
            model.addAttribute("updateEmployeeDto", updateEmployeesDto);
            if (updateEmployeesDto == null) {
                model.addAttribute("updateSearchNotFound",true);
                model.addAttribute("updateEmployeeDto", new EmployeeDto());
            }
        }catch (NumberFormatException e) {
            model.addAttribute("updateSearchNotFound",true);
            model.addAttribute("updateEmployeeDto", new EmployeeDto());
        }

        return "employeeIndex";
    }

    @PostMapping(value = "/update",params = "submit")
    public String employeeUpdate(@Validated(OnUpdate.class) @ModelAttribute("updateEmployeeDto") EmployeeDto employeeDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "empUpdate");
        model.addAttribute("searchQuery" , new SearchDto());

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
    public String employeeDeleteSearch(@Validated @ModelAttribute("searchQuery") SearchDto searchDto,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "empDelete");
        model.addAttribute("searchQuery" , searchDto);

        if (bindingResult.hasErrors()) {
            model.addAttribute("deleteEmployeeDto", new EmployeeDto());
            return "employeeIndex";
        }

        try {
            long id = Long.parseLong(searchDto.getValue());
            EmployeeDto deleteEmployeesDto = employeeService.getEmployeeById(id);
            model.addAttribute("deleteEmployeeDto", deleteEmployeesDto);
            if (deleteEmployeesDto == null) {
                model.addAttribute("deleteSearchNotFound",true);
                model.addAttribute("deleteEmployeeDto", new EmployeeDto());
            }
        }catch (NumberFormatException e) {
            model.addAttribute("deleteSearchNotFound",true);
            model.addAttribute("deleteEmployeeDto", new EmployeeDto());
        }

        return "employeeIndex";
    }

    @PostMapping(value = "/delete",params = "submit")
    public String employeeDelete(@Validated(OnUpdate.class) @ModelAttribute("deleteEmployeeDto") EmployeeDto employeeDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "empDelete");
        model.addAttribute("searchQuery" , new SearchDto());

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
