package com.khk.mgt.rest;

import com.khk.mgt.dto.common.PosProductDto;
import com.khk.mgt.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/product")
@CrossOrigin("*")
public class ProductRestController {

    @Autowired
    private InventoryService inventoryService;


    @GetMapping("/item/{productId}")
    public ResponseEntity<PosProductDto> getProductById(@PathVariable("productId")String productID) {
        try{
            Long id = Long.parseLong(productID);
            return itemSelection(inventoryService.getProductById(id));
        }catch (NumberFormatException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PosProductDto>> getCategoryById(@PathVariable("categoryId")String categoryID) {
        try{
            Long id = Long.parseLong(categoryID);
            return listSelection(inventoryService.getItemListByCategoryId(id));
        }catch (NumberFormatException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    private ResponseEntity<PosProductDto> itemSelection(PosProductDto item) {
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    private ResponseEntity<List<PosProductDto>> listSelection(List<PosProductDto> dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            return new ResponseEntity<>(dataList, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
