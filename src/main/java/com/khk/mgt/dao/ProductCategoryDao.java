package com.khk.mgt.dao;

import com.khk.mgt.ds.ProductCategory;
import com.khk.mgt.dto.common.ProductCategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryDao extends JpaRepository<ProductCategory, Long> {

    @Query("SELECT new com.khk.mgt.dto.common.ProductCategoryDto(pc.name,COUNT(p),SUM(p.buyPrice)) FROM Product p JOIN p.category pc GROUP BY p.category")
    List<ProductCategoryDto> getCategoryDtoByProductCountAndTotalPrice();
}
