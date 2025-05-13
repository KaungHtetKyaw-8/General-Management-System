package com.khk.mgt.dao;

import com.khk.mgt.ds.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdersDao extends JpaRepository<Orders, Integer> {

    @Query("SELECT p.orderDate FROM Orders p WHERE p.orderDate >= :fromDate")
    List<LocalDate> findCreatedDatesAfter(@Param("fromDate") LocalDate fromDate);
}
