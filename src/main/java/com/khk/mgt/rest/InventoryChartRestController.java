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
@RequestMapping("/api/inventory/chart")
public class InventoryChartRestController {

    @Autowired
    private ChartsService chartsService;

    @GetMapping(value = "/vendor/gender",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getInventoryVendorGender(){
        return listChartData(chartsService.getDonutSingleDataChart(
                ChartsService.INVENTORY_VENDOR_BY_GENDER,
                "Vendor Gender",
                4L
                )
        );
    }

    @GetMapping(value = "/vendor",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getProductByVendor(){
        return listChartData(chartsService.getDonutSingleDataChart(
            ChartsService.INVENTORY_PRODUCT_BY_VENDOR,
                "Product By Vendor",
                4L
            )
        );
    }

    @GetMapping(value = "/company",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getProductByCompany(){
        return listChartData(chartsService.getDonutSingleDataChart(
                        ChartsService.INVENTORY_PRODUCT_BY_COMPANY,
                        "Product By Company",
                        4L
                )
        );
    }

    @GetMapping(value = "/price",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartDto> getPriceRangeByProduct(){
        return listChartData(chartsService.getBarMultiDataChart(
                        ChartsService.INVENTORY_PRICE_RANGE,
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
