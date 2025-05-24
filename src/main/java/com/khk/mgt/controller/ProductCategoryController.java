package com.khk.mgt.controller;

import com.khk.mgt.dto.common.*;
import com.khk.mgt.service.ProductCategoryService;
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
@RequestMapping("/category")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;


    @GetMapping({"","/","home","index"})
    public String index(Model model) {
        return "redirect:/inventory";
    }

    @GetMapping(params = "nav")
    public String navChange(@RequestParam("nav") String navStatus, Model model) {
        model.addAttribute("navStatus", navStatus);

        switch (navStatus) {
            case "categoryView":
                model.addAttribute("viewCategoryDtoList", new ArrayList<ProductCategoryDto>());
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            case "categoryAdd":
                model.addAttribute("addCategoryDto", new ProductCategoryDto());
                break;
            case "categoryUpdate":
                model.addAttribute("updateCategoryDto", new ProductCategoryDto());
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            case "categoryDelete":
                model.addAttribute("deleteCategoryDto", new ProductCategoryDto());
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            default:
        }
        return "inventoryIndex";
    }

    @PostMapping(value = "/viewsearch",params = "search")
    public String viewSearch(@ModelAttribute("searchQuery") SearchDto searchDto, BindingResult bindingResult, Model model, ServletRequest servletRequest) {

        model.addAttribute("navStatus", "categoryView");
        model.addAttribute("searchQuery" , searchDto);

        if (bindingResult.hasErrors()) {
            return "inventoryIndex";
        }

        long queryId = 0L;

        if (!searchDto.getSearchType().equals("1")){
            try {
                queryId = Long.parseLong(searchDto.getValue());
            }catch (NumberFormatException e) {
                model.addAttribute("viewCategoryDtoList", new ArrayList<ProductCategoryDto>());
                model.addAttribute("viewFailForNumber", true);
                return "inventoryIndex";
            }
        }

        List<ProductCategoryDto> result = new ArrayList<>();

        switch (searchDto.getSearchType()) {
            // All Products
            case "1" :
                result = productCategoryService.getAllProductCategories();
                break;
            // By Category ID
            case "2" :
                ProductCategoryDto productCategoryDto = productCategoryService.getProductCategoryDtoById(queryId);
                if (productCategoryDto != null) {
                    result.add(productCategoryDto);
                }
                break;
            default:
                result = new ArrayList<>();
        }

        if (result != null && !result.isEmpty()) {
            model.addAttribute("viewCategoryDtoList", result);
        }else{
            model.addAttribute("viewCategoryDtoList", new ArrayList<PointCardDto>());
            model.addAttribute("viewFail", true);
        }
        return "inventoryIndex";
    }

    @PostMapping(value = "/add",params = "submit")
    public String vendorAdd(@Validated(OnCreate.class) @ModelAttribute("addCategoryDto") ProductCategoryDto productCategoryDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "categoryAdd");

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("registerFail", true);
            return "inventoryIndex";
        }else{
            // process the form submission
            productCategoryService.saveProductCategory(productCategoryDto);
            model.addAttribute("registerSuccess", true);
            model.addAttribute("addCategoryDto", new ProductCategoryDto());
            return "inventoryIndex";
        }
    }

    @PostMapping(value = "/update",params = "search")
    public String vendorUpdateSearch(@ModelAttribute("searchQuery") SearchDto searchDto,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "categoryUpdate");
        model.addAttribute("searchQuery" , new SearchDto());

        if (bindingResult.hasErrors()) {
            return "inventoryIndex";
        }

        try {
            long id = Long.parseLong(searchDto.getValue());
            ProductCategoryDto updateCategoryDto = productCategoryService.getProductCategoryDtoById(id);
            model.addAttribute("updateCategoryDto", updateCategoryDto);
            if (updateCategoryDto == null) {
                model.addAttribute("updateSearchNotFound",true);
                model.addAttribute("updateCategoryDto", new ProductCategoryDto());
            }
        }catch (NumberFormatException e) {
            model.addAttribute("updateSearchNotFound",true);
            model.addAttribute("updateCategoryDto", new ProductCategoryDto());
        }

        return "inventoryIndex";
    }

    @PostMapping(value = "/update",params = "submit")
    public String customerUpdate(@Validated(OnUpdate.class) @ModelAttribute("updateCategoryDto") ProductCategoryDto productCategoryDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "categoryUpdate");
        model.addAttribute("searchQuery" , new SearchDto());

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("updateFail", true);
            return "inventoryIndex";
        }else{
            // process the form submission
            productCategoryService.updateProductCategory(productCategoryDto);
            model.addAttribute("updateSuccess", true);
            model.addAttribute("updateCategoryDto", new ProductCategoryDto());
            return "inventoryIndex";
        }
    }


    @PostMapping(value = "/delete",params = "search")
    public String customerDeleteSearch(@ModelAttribute("searchQuery") SearchDto searchDto,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "categoryDelete");
        model.addAttribute("searchQuery" , new SearchDto());

        if (bindingResult.hasErrors()) {
            return "inventoryIndex";
        }

        try {
            long id = Long.parseLong(searchDto.getValue());
            ProductCategoryDto deleteCategoryDto = productCategoryService.getProductCategoryDtoById(id);
            model.addAttribute("deleteCategoryDto", deleteCategoryDto);
            if (deleteCategoryDto == null) {
                model.addAttribute("deleteSearchNotFound",true);
                model.addAttribute("deleteCategoryDto", new ProductCategoryDto());
            }
        }catch (NumberFormatException e) {
            model.addAttribute("deleteSearchNotFound",true);
            model.addAttribute("deleteCategoryDto", new ProductCategoryDto());
        }

        return "inventoryIndex";
    }

    @PostMapping(value = "/delete",params = "submit")
    public String customerDelete(@Validated(OnUpdate.class) @ModelAttribute("deleteCategoryDto") ProductCategoryDto productCategoryDto, BindingResult bindingResult, Model model) {
        model.addAttribute("navStatus", "categoryDelete");
        model.addAttribute("searchQuery" , new SearchDto());

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("deleteFail", true);
            return "inventoryIndex";
        }else{
            // process the form submission
            productCategoryService.deleteProductCategory(productCategoryDto.getId());
            model.addAttribute("deleteSuccess", true);
            model.addAttribute("deleteCategoryDto", new ProductCategoryDto());
            return "inventoryIndex";
        }
    }
}
