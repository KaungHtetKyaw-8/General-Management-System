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
@RequestMapping("/api/order/chart")
public class OrderChartRestController {

    @Autowired
    private ChartsService chartsService;

    @GetMapping(value = "/customer",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getOrderByProduct(){
        return listChartData(chartsService.getDonutSingleDataChart(
                ChartsService.ORDERS_BY_CUSTOMER,
                "Sold Price ",
                5L
                )
        );
    }

    @GetMapping(value = "/product",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getOrderByCategory(){
        return listChartData(chartsService.getDonutSingleDataChart(
            ChartsService.ORDERS_BY_PRODUCT,
                "Sold Price ",
                5L
            )
        );
    }

    @GetMapping(value = "/category",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getProductByCompany(){
        return listChartData(chartsService.getDonutSingleDataChart(
                        ChartsService.ORDERS_BY_CATEGORY,
                        "Ordered Category",
                        5L
                )
        );
    }

    @GetMapping(value = "/orderdate",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getPriceRangeByProduct(){
        return listChartData(chartsService.getBarMultiDataChart(
                        ChartsService.ORDERS_BY_ORDER_DATE,
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
