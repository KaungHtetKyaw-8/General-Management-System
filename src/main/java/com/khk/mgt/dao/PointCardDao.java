package com.khk.mgt.dao;

import com.khk.mgt.ds.PointCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointCardDao extends JpaRepository<PointCard, Long> {
}
