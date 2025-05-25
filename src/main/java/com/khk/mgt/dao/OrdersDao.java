package com.khk.mgt.dao;

import com.khk.mgt.ds.Orders;
import com.khk.mgt.dto.chart.GroupedLabelValue;
import com.khk.mgt.dto.chart.LabelValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdersDao extends JpaRepository<Orders, Long> {

    List<Orders> findByCustomerId(Long customerId);

    @Query("SELECT new com.khk.mgt.dto.chart.GroupedLabelValue(sd.product.name,o.orderDate,sd.quantity) FROM Orders o JOIN o.sellDetail sd WHERE o.orderDate >= :fromDate ORDER BY o.id DESC LIMIT 5")
    List<GroupedLabelValue> findOrdersByRecentOrderedDate(@Param("fromDate") LocalDate fromDate);

    @Query("SELECT new com.khk.mgt.dto.chart.LabelValue(CONCAT(o.customer.firstName,' ',o.customer.lastName) ,SUM(sd.quantity * sd.product.sellPrice)) FROM Orders o JOIN o.sellDetail sd  GROUP BY o.customer")
    List<LabelValue> findLabelValueByCustomer();

    @Query("SELECT new com.khk.mgt.dto.chart.LabelValue(sd.product.name,SUM(sd.quantity * sd.product.sellPrice)) FROM Orders o JOIN o.sellDetail sd GROUP BY sd.product")
    List<LabelValue> findLabelValueByProduct();

    @Query("SELECT new com.khk.mgt.dto.chart.LabelValue(sd.product.category.name,SUM(sd.quantity * sd.product.sellPrice)) FROM Orders o JOIN o.sellDetail sd GROUP BY sd.product.category")
    List<LabelValue> findLabelValueByCategory();

    @Query("SELECT new com.khk.mgt.dto.chart.GroupedLabelValue(sd.product.category.name,o.orderDate,SUM(sd.quantity)) FROM Orders o JOIN o.sellDetail sd WHERE o.orderDate >= :fromDate GROUP BY o.orderDate,sd.product.category.name")
    List<GroupedLabelValue> findGroupedLabelValueByOrderDate(@Param("fromDate") LocalDate fromDate);

    List<Orders> findTop100ByOrderByOrderDateDesc();
}
