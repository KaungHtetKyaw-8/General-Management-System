package com.khk.mgt.mapper;

import com.khk.mgt.ds.Orders;
import com.khk.mgt.ds.Product;
import com.khk.mgt.dto.common.InventoryDto;
import com.khk.mgt.dto.common.OrderDetailDto;
import com.khk.mgt.dto.common.OrderDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class OrdersMapper {
    public static OrderDto toDto(Orders entity) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(entity.getId());
        orderDto.setOrderDate(entity.getOrderDate());
        orderDto.setCustomerName(entity.getCustomer().getFirstName() + " " + entity.getCustomer().getLastName());

        List<OrderDetailDto> orderDetailList = new ArrayList<>();

        orderDetailList = entity.getSellDetail().stream().map(detailItem ->{
            OrderDetailDto orderDetailDto = new OrderDetailDto();
            orderDetailDto.setProductId(detailItem.getProduct().getId());
            orderDetailDto.setProductName(detailItem.getProduct().getName());
            orderDetailDto.setProductPrice(detailItem.getProduct().getSellPrice());
            orderDetailDto.setProductQty(detailItem.getQuantity());
            return orderDetailDto;
        }).collect(Collectors.toList());

        orderDto.setItemList(orderDetailList);

        return orderDto;
    }
}
