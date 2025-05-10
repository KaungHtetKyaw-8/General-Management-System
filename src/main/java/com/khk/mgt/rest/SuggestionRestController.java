package com.khk.mgt.rest;

import com.khk.mgt.dto.SuggestionDto;
import com.khk.mgt.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/suggest")
public class SuggestionRestController {

    @Autowired
    private EmployeeService employeeService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/departmentname",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuggestionDto> getDepartmentName(@RequestParam("q") String departmentName) {
        SuggestionDto dto = new SuggestionDto();
        List<String> dataList = employeeService.suggestDepartmentName(departmentName);
        System.out.println("Requested : "+departmentName);
        System.out.println("Retrieved : "+dataList);

        if (dataList != null && dataList.size() > 0) {
            dto.setData(dataList);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }else {
            dto.setData(null);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
