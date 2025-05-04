package com.khk.mgt.dao;

import com.khk.mgt.ds.DepartmentCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentCategoryDao extends CrudRepository<DepartmentCategory, Integer> {
}
