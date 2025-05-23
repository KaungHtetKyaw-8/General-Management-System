package com.khk.mgt.service;


import com.khk.mgt.dao.OrdersDao;
import com.khk.mgt.ds.*;
import com.khk.mgt.dto.common.OrderDto;
import com.khk.mgt.util.PointConvert;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;


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

    public void checkEntityState(Object entity) {
        if (entityManager.contains(entity)) {
            System.out.println("Entity is in managed state.");
        } else {
            System.out.println("Entity is NOT managed.");
        }
    }

//    public ProductCategory findProductCategoryById(Long id) {
//        // Product Category find By ID
//        return productCategoryDao.findById(id).orElse(null);
//    }
//
//    public boolean isProductCategoryExist(Long id) {
//        // Product Category exists or not by ID
//        return productCategoryDao.findById(id).isPresent();
//    }
//
//    public List<ProductCategory> findAllProductCategories() {
//        // Retrieve the all Product Categories
//        return productCategoryDao.findAll();
//    }
//
//    public ProductCategoryDto getProductCategoryDtoById(Long id) {
//        ProductCategory productCategory = findProductCategoryById(id);
//
//        if (productCategory == null) {
//            return null;
//        }
//
//        return new ProductCategoryDto(productCategory.getId(), productCategory.getName());
//    }
//
//    public List<ProductCategoryDto> getAllProductCategories() {
//        List<ProductCategory> productCategories = productCategoryDao.findAll();
//
//        if (productCategories.isEmpty()) {
//            return null;
//        }
//
//        // Retrieve the all Product Categories convert to Dto
//        return productCategories
//                .stream()
//                .map(category -> new ProductCategoryDto(category.getId(),category.getName()))
//                .collect(Collectors.toList());
//    }

    public void saveOrder(OrderDto orderDto) {
        // Create New Orders
        Orders orders = new Orders();

        // Retrieve Customer Entity
        Customer customer;
        if (orderDto.getCustomerId() == null && orderDto.getPointCardId() == null) {
            customer = customerService.findCustomerById(DUMMY_CUSTOMER_ID);
        }else if (orderDto.getCustomerId() != null){
            customer = customerService.findCustomerById(orderDto.getCustomerId());
        }else{
            customer = customerService.findCustomerByPointCardId(orderDto.getPointCardId());
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
        orderDto.getItemList()
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

        if (!customer.getId().equals(DUMMY_CUSTOMER_ID) && orderDto.getPointCardId() != null) {
            pointCardService.updatePointCardCount(orderDto.getPointCardId(),pointConvert.getPoint());
        }

    }


}
