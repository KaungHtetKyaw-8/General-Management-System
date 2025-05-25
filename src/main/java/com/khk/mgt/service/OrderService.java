package com.khk.mgt.service;


import com.khk.mgt.dao.OrdersDao;
import com.khk.mgt.ds.*;
import com.khk.mgt.dto.chart.GroupedLabelValue;
import com.khk.mgt.dto.chart.LabelValue;
import com.khk.mgt.dto.common.OrderDto;
import com.khk.mgt.dto.common.PosOrderDto;
import com.khk.mgt.mapper.OrdersMapper;
import com.khk.mgt.util.PointConvert;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class OrderService {
    @Value("${customer.dummy.id}")
    private Long DUMMY_CUSTOMER_ID;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private PointCardService pointCardService;

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private EntityManager entityManager;


    public List<OrderDto> getLatest100orders() {
        List<OrderDto> orderDtoList = new ArrayList<>();
        List<Orders> ordersList = ordersDao.findTop100ByOrderByOrderDateDesc();

        if (ordersList == null || ordersList.isEmpty()) {
            return null;
        }

        orderDtoList = ordersList.stream().map(OrdersMapper::toDto).collect(Collectors.toList());

        orderDtoList.sort(
                Comparator.comparing(OrderDto::getOrderDate,Comparator.reverseOrder())
                        .thenComparing(OrderDto::getOrderId,Comparator.reverseOrder())
        );

        return orderDtoList;
    }

    public OrderDto getOrdersByOrderId(Long id) {
        Orders orders = ordersDao.findById(id).orElse(null);
        if (orders == null) {
            return null;
        }
        return OrdersMapper.toDto(orders);
    }

    public List<OrderDto> getOrdersByCustomerId(Long id) {
        List<OrderDto> orderDtoList = new ArrayList<>();
        List<Orders> ordersList = ordersDao.findByCustomerId(id);

        if (ordersList == null || ordersList.isEmpty()) {
            return null;
        }

        orderDtoList = ordersList.stream().map(OrdersMapper::toDto).collect(Collectors.toList());

        orderDtoList.sort(
                Comparator.comparing(OrderDto::getOrderDate,Comparator.reverseOrder())
                        .thenComparing(OrderDto::getOrderId,Comparator.reverseOrder())
        );

        return orderDtoList;
    }

    public List<GroupedLabelValue> getOrderDtoByRecentOrderedDate() {
        LocalDate pastTwoDayLocalDate = LocalDate.now().minusDays(2);
        List<GroupedLabelValue> ordersList = ordersDao.findOrdersByRecentOrderedDate(pastTwoDayLocalDate);
        if (ordersList == null || ordersList.isEmpty()) {
            return null;
        }

        return ordersList;
    }

    public List<LabelValue> getLabelValueByCustomer(){
        List<LabelValue> result = ordersDao.findLabelValueByCustomer();
        result.sort(Comparator.comparing(LabelValue::getValue));
        return result;
    }

    public List<LabelValue> getLabelValueByProduct(){
        List<LabelValue> result = ordersDao.findLabelValueByProduct();
        result.sort(Comparator.comparing(LabelValue::getValue));
        return result;
    }

    public List<LabelValue> getLabelValueByCategory(){
        List<LabelValue> result = ordersDao.findLabelValueByCategory();
        result.sort(Comparator.comparing(LabelValue::getValue));
        return result;
    }

    public List<GroupedLabelValue> getOrdersByOrdersDate(){
        LocalDate pastTwoDayLocalDate = LocalDate.now().minusDays(5);
        List<GroupedLabelValue> result = ordersDao.findGroupedLabelValueByOrderDate(pastTwoDayLocalDate);
        result.sort(Comparator.comparingLong(item -> item.getValue().longValue()));
        return ordersDao.findGroupedLabelValueByOrderDate(pastTwoDayLocalDate);
    }


    public void saveOrder(PosOrderDto posOrderDto) {
        // Create New Orders
        Orders orders = new Orders();

        // Retrieve Customer Entity
        Customer customer;
        if (posOrderDto.getCustomerId() == null && posOrderDto.getPointCardId() == null) {
            customer = customerService.findCustomerById(DUMMY_CUSTOMER_ID);
        }else if (posOrderDto.getCustomerId() != null){
            customer = customerService.findCustomerById(posOrderDto.getCustomerId());
        }else{
            customer = customerService.findCustomerByPointCardId(posOrderDto.getPointCardId());
        }
        // Added the customer For order
        orders.setCustomer(customer);

        // Ordered Date Add
        orders.setOrderDate(Date.valueOf(LocalDate.now()));

        // Save the Order
        Orders savedOrders = ordersDao.save(orders);

        // Point Count
        PointConvert pointConvert = new PointConvert();

        // Order Detail List Create
        posOrderDto.getItemList()
                .forEach(item -> {
                    // Create Dummy Sell Detail
                    SellDetail sellDetail = new SellDetail();

                    // Inventory Product Count Updated
                    inventoryService.updateProductQtyAdjust(item.getProductId(),item.getProductQty());

                    // Fetch Managed Entities
                    Product product = inventoryService.findProductById(item.getProductId());

                    // Fill Product and Quantities
                    sellDetail.setProduct(product);
                    sellDetail.setQuantity(item.getProductQty());
                    sellDetail.setId(new SellDetailId(product.getId(), savedOrders.getId()));

                    // Point Count Add
                    pointConvert.setAndCalculatePoint(item.getProductQty(),product.getSellPrice());

                    // Detail List Add
                    savedOrders.addSellDetail(sellDetail);
                });

        if (!customer.getId().equals(DUMMY_CUSTOMER_ID) && posOrderDto.getPointCardId() != null) {
            pointCardService.updatePointCardCount(posOrderDto.getPointCardId(),pointConvert.getPoint());
        }

    }

    public void deleteOrder(OrderDto orderDto) {
        Orders orders = ordersDao.findById(orderDto.getOrderId()).orElse(null);
        if (orders == null) {
            return;
        }

        orders.getSellDetail().forEach(item -> {
            // Inventory Item Re-fill
            inventoryService.updateProductQtyAdjust(item.getProduct().getId(),item.getQuantity()*-1);
        });

        ordersDao.delete(orders);
    }


}
