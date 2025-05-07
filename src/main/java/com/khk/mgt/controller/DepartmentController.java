package com.khk.mgt.controller;

import com.khk.mgt.dao.DepartmentCategoryDao;
import com.khk.mgt.ds.DepartmentCategory;
import com.khk.mgt.dto.TableHeaderList;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentCategoryDao departmentCategoryDao;

    @Autowired
    private TableHeaderList tableHeaderList;

    @GetMapping
    public String index(DepartmentCategory departmentData,Model model) {

        model.addAttribute("departmentData", departmentData);

        return "index";
    }

    @RequestMapping(value ="/add",method = RequestMethod.POST,params = "submit")
    public String add(@Valid @ModelAttribute DepartmentCategory departmentData , BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Binding Error");
            return index(departmentData,model);
        }else{
            System.out.println("Department Category save success : " + departmentData);
            departmentCategoryDao.save(departmentData);
            return "redirect:/department";
        }
    }
}
