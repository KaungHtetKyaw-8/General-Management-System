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
@RequestMapping("/api/customer/chart")
public class CustomerChartRestController {

    @Autowired
    private ChartsService chartsService;


    @GetMapping(value = "/gender",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getCustomerGender(){
        return listChartData(chartsService.getDonutSingleDataChart(
                ChartsService.CUSTOMER_GENDER,
                "Gender BreakDown",
                4L
                )
        );
    }

    @GetMapping(value = "/age",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getCustomerAgeDifference(){
        return listChartData(chartsService.getDonutSingleDataChart(
            ChartsService.CUSTOMER_AGE_RANGE,
                "Customer Count Group By Age",
                4L
            )
        );
    }

    @GetMapping(value = "/categorisation",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getCustomerByCardCategory(){
        return listChartData(chartsService.getDonutSingleDataChart(
                        ChartsService.CUSTOMER_POINT_CARD_CATEGORY,
                        "Customer Count Group By Card Category",
                        4L
                )
        );
    }

    @GetMapping(value = "/points",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getCustomerByPoints(){
        return listChartData(chartsService.getBarMultiDataChart(
                        ChartsService.CUSTOMER_POINTS,
                        Long.MAX_VALUE
                )
        );
    }


    private ResponseEntity<ChartDto> listChartData(ChartDto chartDto) {
        if (chartDto != null && !chartDto.getDatasets().isEmpty()) {
            return new ResponseEntity<>(chartDto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
