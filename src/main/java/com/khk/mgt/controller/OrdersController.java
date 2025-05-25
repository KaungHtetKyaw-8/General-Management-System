package com.khk.mgt.controller;

import com.khk.mgt.dto.common.*;
import com.khk.mgt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @GetMapping({"","/","home","index"})
    public String index(Model model) {
        model.addAttribute("navStatus", "dashBoard");
        model.addAttribute("dashBoardOrders",orderService.getOrderDtoByRecentOrderedDate());
        return "ordersIndex";
    }

    @GetMapping(params = "nav")
    public String navChange(@RequestParam("nav") String navStatus, Model model) {
        model.addAttribute("navStatus", navStatus);

        switch (navStatus) {
            case "dashBoard":
                model.addAttribute("dashBoardOrders",orderService.getOrderDtoByRecentOrderedDate());
            case "ordersView":
                model.addAttribute("viewOrdersListDto", new ArrayList<OrderDto>());
                model.addAttribute("searchQuery" , new SearchDto());
                break;
            case "ordersDelete":
                model.addAttribute("searchQuery" , new SearchDto());
                model.addAttribute("deleteOrderDto", new OrderDto());
                break;
            default:
        }
        return "ordersIndex";
    }

    @PostMapping(value = "/viewsearch",params = "search")
    public String viewSearch(@ModelAttribute("searchQuery")SearchDto searchDto,
                             BindingResult bindingResult,
                             Model model) {

        model.addAttribute("navStatus", "ordersView");
        model.addAttribute("searchQuery" , searchDto);

        if (bindingResult.hasErrors()) {
            model.addAttribute("viewOrdersListDto", new ArrayList<OrderDto>());
            model.addAttribute("viewFail", true);
            return "ordersIndex";
        }

        Long queryId = 0L;

        if (!searchDto.getSearchType().equals("1")){
            try {
                queryId = Long.parseLong(searchDto.getValue());
            }catch (NumberFormatException e) {
                model.addAttribute("viewOrdersListDto", new ArrayList<OrderDto>());
                model.addAttribute("viewFailForNumber", true);
                return "ordersIndex";
            }
        }
        List<OrderDto> result = new ArrayList<>();

        switch (searchDto.getSearchType()) {
            // All
            case "1" :
                result = orderService.getLatest100orders();
                break;
            // By Orders ID
            case "2" :
                OrderDto dtoByOrder = orderService.getOrdersByOrderId(queryId);
                if (dtoByOrder != null) {
                    result.add(dtoByOrder);
                }
                break;
            // By Customer ID
            case "3" :
                result = orderService.getOrdersByCustomerId(queryId);
                break;
            default:
                result = new ArrayList<>();
        }

        if (result != null && !result.isEmpty()) {
            model.addAttribute("viewOrdersListDto", result);
        }else{
            model.addAttribute("viewOrdersListDto", new ArrayList<PointCardDto>());
            model.addAttribute("viewFail", true);
        }

        return "ordersIndex";
    }

    @PostMapping(value = "/delete",params = "search")
    public String customerDeleteSearch(@ModelAttribute("searchQuery") SearchDto searchDto,BindingResult bindingResult, Model model) {

        model.addAttribute("navStatus", "ordersDelete");
        model.addAttribute("searchQuery" , searchDto);

        if (bindingResult.hasErrors()) {
            return "ordersIndex";
        }

        try {
            long id = Long.parseLong(searchDto.getValue());
            OrderDto deleteOrderDto = orderService.getOrdersByOrderId(id);
            model.addAttribute("deleteOrderDto", deleteOrderDto);
            if (deleteOrderDto == null) {
                model.addAttribute("deleteSearchNotFound",true);
                model.addAttribute("deleteOrderDto", new OrderDto());
            }
        }catch (NumberFormatException e) {
            model.addAttribute("deleteSearchNotFound",true);
            model.addAttribute("deleteOrderDto", new OrderDto());
        }

        return "ordersIndex";
    }

    @PostMapping(value = "/delete",params = "submit")
    public String pointCardDelete(@ModelAttribute("deleteOrderDto") OrderDto orderDto,
                                  BindingResult bindingResult,
                                  Model model) {
        model.addAttribute("navStatus", "ordersDelete");
        model.addAttribute("searchQuery" , new SearchDto());

        if (bindingResult.hasErrors()) {
            // process the form error
            System.out.println("Binding Errors : " + bindingResult.getAllErrors());
            model.addAttribute("deleteFail", true);
            return "ordersIndex";
        }else{
            // process the form submission
            orderService.deleteOrder(orderDto);
            model.addAttribute("deleteSuccess", true);
            model.addAttribute("deleteOrderDto", new OrderDto());
            return "ordersIndex";
        }
    }
}
