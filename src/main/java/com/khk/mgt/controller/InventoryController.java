package com.khk.mgt.controller;

import com.khk.mgt.dto.common.*;
import com.khk.mgt.service.InventoryService;
import com.khk.mgt.service.ProductCategoryService;
import com.khk.mgt.service.VendorService;
import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private InventoryService inventoryService;


    @GetMapping({"","/","home","index"})
    public String index(Model model) {
//        model.addAttribute("employeeDto", new EmployeeDto());
//        model.addAttribute("employeeDtoList", new ArrayList<Employee>());
//        model.addAttribute("navStatus", "dashBoard");
//        model.addAttribute("newEmployees", employeeService.newest5Employees());

        return "inventoryIndex";
    }

    @GetMapping(params = "nav")
    public String navChange(@RequestParam("nav") String navStatus, Model model) {
        model.addAttribute("navStatus", navStatus);

        switch (navStatus) {
            case "dashBoard":
//                model.addAttribute("newEmployees", employeeService.newest5Employees());
                break;
            case "inventoryView":
                model.addAttribute("viewInventoryDtoList", new ArrayList<InventoryDto>());
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            case "inventoryAdd":
                model.addAttribute("addInventoryDto", new InventoryDto());
                model.addAttribute("vendorList", vendorService.getAllVendor());
                model.addAttribute("productCategoryList", productCategoryService.getAllProductCategories());
                break;
            case "inventoryUpdate":
                model.addAttribute("updateInventoryDto", new InventoryDto());
                model.addAttribute("searchQuery" , new SearchDto());
                model.addAttribute("vendorList", vendorService.getAllVendor());
                model.addAttribute("productCategoryList", productCategoryService.getAllProductCategories());
                break;
            case "inventoryDelete":
                model.addAttribute("deleteInventoryDto", new InventoryDto());
                model.addAttribute("searchQuery" , new SearchDto());
                model.addAttribute("vendorList", vendorService.getAllVendor());
                model.addAttribute("productCategoryList", productCategoryService.getAllProductCategories());
                break;
            default:
        }
        return "inventoryIndex";
    }

    @PostMapping(value = "/viewsearch",params = "search")
    public String viewSearch(@ModelAttribute("searchQuery") SearchDto searchDto, BindingResult bindingResult, Model model, ServletRequest servletRequest) {

        model.addAttribute("navStatus", "inventoryView");
        model.addAttribute("searchQuery" , searchDto);

        if (bindingResult.hasErrors()) {
            return "inventoryIndex";
        }

        long queryId = 0L;

        if (!searchDto.getSearchType().equals("1")){
            try {
                queryId = Long.parseLong(searchDto.getValue());
            }catch (NumberFormatException e) {
                model.addAttribute("viewInventoryDtoList", new ArrayList<InventoryDto>());
                model.addAttribute("viewFailForNumber", true);
                return "inventoryIndex";
            }
        }

        List<InventoryDto> result = new ArrayList<>();

        switch (searchDto.getSearchType()) {
            // All Products
            case "1" :
                result = inventoryService.getAllProducts();
                break;
            // By Product ID
            case "2" :
                result.add(inventoryService.getInventoryById(queryId));
                break;
            // By Vendor ID
            case "3" :
                result = inventoryService.getVendorById(queryId);
                break;
            // By Category ID
            case "4" :
                result = inventoryService.getCategoryById(queryId);
                break;
            default:
                result = new ArrayList<>();
        }

        model.addAttribute("viewInventoryDtoList", result);
        return "inventoryIndex";
    }

    @PostMapping(value = "/add",params = "submit")
    public String vendorAdd(@Validated(OnCreate.class) @ModelAttribute("addInventoryDto") InventoryDto inventoryDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "inventoryAdd");
        model.addAttribute("vendorList", vendorService.getAllVendor());
        model.addAttribute("productCategoryList", productCategoryService.getAllProductCategories());

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("registerFail", true);
            return "inventoryIndex";
        }else{
            // process the form submission
            inventoryService.saveProduct(inventoryDto);
            model.addAttribute("registerSuccess", true);
            model.addAttribute("addInventoryDto", new InventoryDto());
            return "inventoryIndex";
        }
    }

    @PostMapping(value = "/update",params = "search")
    public String vendorUpdateSearch(@ModelAttribute("searchQuery") SearchDto searchDto,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "inventoryUpdate");
        model.addAttribute("searchQuery" , new SearchDto());
        model.addAttribute("vendorList", vendorService.getAllVendor());
        model.addAttribute("productCategoryList", productCategoryService.getAllProductCategories());

        if (bindingResult.hasErrors()) {
            return "inventoryIndex";
        }

        try {
            long id = Long.parseLong(searchDto.getValue());
            InventoryDto updateInventoryDto = inventoryService.getInventoryById(id);
            model.addAttribute("updateInventoryDto", updateInventoryDto);
            if (updateInventoryDto == null) {
                model.addAttribute("updateSearchNotFound",true);
                model.addAttribute("updateInventoryDto", new InventoryDto());
            }
        }catch (NumberFormatException e) {
            model.addAttribute("updateSearchNotFound",true);
            model.addAttribute("updateInventoryDto", new InventoryDto());
        }

        return "inventoryIndex";
    }

    @PostMapping(value = "/update",params = "submit")
    public String customerUpdate(@Validated(OnUpdate.class) @ModelAttribute("updateInventoryDto") InventoryDto inventoryDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "inventoryUpdate");
        model.addAttribute("searchQuery" , new SearchDto());
        model.addAttribute("vendorList", vendorService.getAllVendor());
        model.addAttribute("productCategoryList", productCategoryService.getAllProductCategories());

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("updateFail", true);
            return "inventoryIndex";
        }else{
            // process the form submission
            inventoryService.updateProduct(inventoryDto);
            model.addAttribute("updateSuccess", true);
            model.addAttribute("updateInventoryDto", new InventoryDto());
            return "inventoryIndex";
        }
    }


    @PostMapping(value = "/delete",params = "search")
    public String customerDeleteSearch(@ModelAttribute("searchQuery") SearchDto searchDto,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "inventoryDelete");
        model.addAttribute("searchQuery" , new SearchDto());
        model.addAttribute("vendorList", vendorService.getAllVendor());
        model.addAttribute("productCategoryList", productCategoryService.getAllProductCategories());

        if (bindingResult.hasErrors()) {
            return "inventoryIndex";
        }

        try {
            long id = Long.parseLong(searchDto.getValue());
            InventoryDto deleteInventoryDto = inventoryService.getInventoryById(id);
            model.addAttribute("deleteInventoryDto", deleteInventoryDto);
            if (deleteInventoryDto == null) {
                model.addAttribute("deleteSearchNotFound",true);
                model.addAttribute("deleteInventoryDto", new InventoryDto());
            }
        }catch (NumberFormatException e) {
            model.addAttribute("deleteSearchNotFound",true);
            model.addAttribute("deleteInventoryDto", new InventoryDto());
        }

        return "inventoryIndex";
    }

    @PostMapping(value = "/delete",params = "submit")
    public String customerDelete(@Validated(OnUpdate.class) @ModelAttribute("deleteInventoryDto") InventoryDto inventoryDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "inventoryDelete");
        model.addAttribute("searchQuery" , new SearchDto());
        model.addAttribute("vendorList", vendorService.getAllVendor());
        model.addAttribute("productCategoryList", productCategoryService.getAllProductCategories());

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("deleteFail", true);
            return "inventoryIndex";
        }else{
            // process the form submission
            inventoryService.deleteProduct(inventoryDto.getId());
            model.addAttribute("deleteSuccess", true);
            model.addAttribute("deleteInventoryDto", new InventoryDto());
            return "inventoryIndex";
        }
    }
}
