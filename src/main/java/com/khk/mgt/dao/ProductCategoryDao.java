package com.khk.mgt.dao;

import com.khk.mgt.ds.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryDao extends CrudRepository<ProductCategory, Long> {
}
