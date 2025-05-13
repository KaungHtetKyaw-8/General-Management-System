package com.khk.mgt.rest;

import com.khk.mgt.dto.chart.ChartDto;
import com.khk.mgt.service.ChartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee/chart")
public class EmployeeChartRestController {

    @Autowired
    private ChartsService chartsService;


    @GetMapping(value = "/gender",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getEmployeeGender(){
        System.out.println("getEmployeeGender is called");
        return listChartData(chartsService.getDonutSingleDataChart(
                ChartsService.EMPLOYEE_GENDER,
                "Gender BreakDown",
                4L
                )
        );
    }

    @GetMapping(value = "/department",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getEmployeeDepartmentName(){
        return listChartData(chartsService.getDonutSingleDataChart(
            ChartsService.EMPLOYEE_DEPARTMENT,
            "Employee Count Group By Department",
            3L
            )
        );
    }

    @GetMapping(value = "/age",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getEmployeeAgeDifference(){
        return listChartData(chartsService.getDonutSingleDataChart(
            ChartsService.EMPLOYEE_AGE_RANGE,
                "Employee Count Group By Age",
                4L
            )
        );
    }

    @GetMapping(value = "/city",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getEmployeeByCity(){
        return listChartData(chartsService.getBarMultiDataChart(
                        ChartsService.EMPLOYEE_CITY,
                        Long.MAX_VALUE
                )
        );
    }

//    @GetMapping(value = "/employment",produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ChartDto> getEmploymentTypeChart(){
//        return listChartData(chartsService.getDonutSingleDataChart(
//                        ChartsService.EMPLOYEE_EMPLOYMENT,
//                        "Employee Count Group By Department",
//                        4
//                )
//        );
//    }


    private ResponseEntity<ChartDto> listChartData(ChartDto chartDto) {
        if (chartDto != null && !chartDto.getDatasets().isEmpty()) {
            return new ResponseEntity<>(chartDto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
