package com.khk.mgt.dao;

import com.khk.mgt.ds.Product;
import com.khk.mgt.dto.chart.LabelValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.vendor.id = :vendorId")
    List<Product> findByVendor_Id(Long vendorId);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategory_Id(Long categoryId);

    @Query("SELECT new com.khk.mgt.dto.chart.LabelValue(p.name,COUNT(p)) FROM Product p GROUP BY p.name")
    List<LabelValue> findCountByProductName();

    @Query("SELECT new com.khk.mgt.dto.chart.LabelValue(p.category.name,COUNT(p)) FROM Product p GROUP BY p.category")
    List<LabelValue> findProductCountByCategory();

    @Query("SELECT new com.khk.mgt.dto.chart.LabelValue(CONCAT(p.vendor.firstName,' ',p.vendor.lastName),COUNT(p)) FROM Product p GROUP BY p.vendor")
    List<LabelValue> findProductCountByVendor();
}
