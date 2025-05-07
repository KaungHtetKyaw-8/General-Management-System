package com.khk.mgt.controller;

import com.khk.mgt.dao.DepartmentCategoryDao;
import com.khk.mgt.dto.DepartmentTableDto;
import com.khk.mgt.dto.TableHeaderList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentCategoryDao departmentCategoryDao;

    @Autowired
    private TableHeaderList tableHeaderList;

    @GetMapping({"/","home"})
    public String index(Model model) {
        DepartmentTableDto departmentTableDto = new DepartmentTableDto();

        departmentTableDto.setHeader(tableHeaderList.getDepartmentHeader());

        model.addAttribute("departmentTableDto", departmentTableDto);

        return "index";
    }
}
