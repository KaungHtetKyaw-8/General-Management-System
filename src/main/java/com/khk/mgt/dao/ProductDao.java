package com.khk.mgt.dao;

import com.khk.mgt.ds.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ProductDao extends CrudRepository<Product, Long> {

}
