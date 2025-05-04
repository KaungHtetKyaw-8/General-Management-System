package com.khk.mgt.dao;

import com.khk.mgt.ds.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersDao extends CrudRepository<Orders, Integer> {
}
