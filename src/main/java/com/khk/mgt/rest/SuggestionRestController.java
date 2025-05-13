package com.khk.mgt.rest;

import com.khk.mgt.dto.common.SuggestionDto;
import com.khk.mgt.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/suggest")
public class SuggestionRestController {

    @Autowired
    private EmployeeService employeeService;

//    @CrossOrigin(origins = "*")
    @GetMapping(value = "/departmentname",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuggestionDto> getDepartmentName(@RequestParam("q") String departmentName) {
        return listSuggestions(employeeService.suggestDepartmentName(departmentName));

    }

    @GetMapping(value = "/employmenttype",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuggestionDto> getEmploymentType(@RequestParam("q") String employmentType) {
        return listSuggestions(employeeService.suggestEmploymentType(employmentType));
    }


    private ResponseEntity<SuggestionDto> listSuggestions(List<String> dataList) {
        SuggestionDto dto = new SuggestionDto();
        if (dataList != null && !dataList.isEmpty()) {
            dto.setData(dataList);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }else {
            dto.setData(null);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
