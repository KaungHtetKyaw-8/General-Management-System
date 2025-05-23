package com.khk.mgt.rest;

import com.khk.mgt.dto.common.SelectionDto;
import com.khk.mgt.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/selection")
public class SelectRestController {

    @Autowired
    private VendorService vendorService;


    @GetMapping("/vendors/{companyname}")
    public ResponseEntity<List<SelectionDto>> getVendorSelectByCompanyId(@PathVariable("companyname")String companyName) {
        return listSelection(vendorService.getByCompanyName(companyName));
    }



    private ResponseEntity<List<SelectionDto>> listSelection(List<SelectionDto> dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            return new ResponseEntity<>(dataList, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
