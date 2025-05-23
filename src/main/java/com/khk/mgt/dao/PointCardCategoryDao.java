package com.khk.mgt.dao;

import com.khk.mgt.ds.PointCardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointCardCategoryDao extends JpaRepository<PointCardCategory, Long> {

    PointCardCategory getById(Long id);
}
