package com.khk.mgt.dao;

import com.khk.mgt.ds.PointCardCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointCardCategoryDao extends CrudRepository<PointCardCategory, Long> {

}
