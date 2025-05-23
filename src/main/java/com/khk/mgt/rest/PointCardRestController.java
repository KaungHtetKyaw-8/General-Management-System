package com.khk.mgt.rest;

import com.khk.mgt.dto.common.CustomerDto;
import com.khk.mgt.dto.common.PosPointCardDto;
import com.khk.mgt.dto.common.PosProductDto;
import com.khk.mgt.service.CustomerService;
import com.khk.mgt.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/point/card")
@CrossOrigin("*")
public class PointCardRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{Id}")
    public ResponseEntity<PosPointCardDto> getCardByCardId(@PathVariable("Id")String cardID) {
        try{
            Long id = Long.parseLong(cardID);
            return itemSelection(customerService.getPointCardsByIdForPOS(id));
        }catch (NumberFormatException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/customer/{Id}")
    public ResponseEntity<List<PosPointCardDto>> getCardByCustomerId(@PathVariable("Id")String customerID) {
        try{
            Long id = Long.parseLong(customerID);
            return listSelection(customerService.getPointCardsByCustomerIdForPOS(id));
        }catch (NumberFormatException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    private ResponseEntity<PosPointCardDto> itemSelection(PosPointCardDto item) {
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    private ResponseEntity<List<PosPointCardDto>> listSelection(List<PosPointCardDto> dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            return new ResponseEntity<>(dataList, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
