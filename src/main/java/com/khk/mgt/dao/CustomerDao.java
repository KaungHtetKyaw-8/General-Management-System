package com.khk.mgt.dao;

import com.khk.mgt.ds.Customer;
import com.khk.mgt.ds.PointCard;
import com.khk.mgt.dto.chart.GroupedLabelValue;
import com.khk.mgt.dto.chart.LabelValue;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerDao extends JpaRepository<Customer,Long> {


    @Query("SELECT c FROM PointCard pc JOIN pc.customer c where pc.id = :id")
    Optional<Customer> findByPointCardId(@Param("id")Long pointCardId);

    @Query("SELECT c FROM Customer c WHERE " +
            "CAST(c.id AS string) LIKE :keyword OR " +
            "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Customer> searchIdOrNameByKeyword(@Param("keyword") String keyword);

    @Query("SELECT c FROM PointCard pc JOIN pc.customer c ORDER BY pc.registrationDate DESC LIMIT 5")
    List<Customer> findRecent5Customers();

    @Query("SELECT c.dateOfBirth FROM Customer c WHERE c.dateOfBirth IS NOT NULL")
    List<Date> findAllDateOfBirth();

    @Query("SELECT new com.khk.mgt.dto.chart.LabelValue(c.gender, COUNT(c)) FROM Customer c GROUP BY c.gender")
    List<LabelValue> findCountByGender();

    @Query("SELECT new com.khk.mgt.dto.chart.LabelValue(pc.category.type,COUNT(pc)) FROM PointCard pc GROUP BY pc.category")
    List<LabelValue> findPointCardCategoryCount();

    @Query("SELECT new com.khk.mgt.dto.chart.GroupedLabelValue(c.gender,pc.category.type,SUM(pc.points)) FROM PointCard pc JOIN pc.customer c  GROUP BY c.gender,pc.category.type")
    List<GroupedLabelValue> findPointsGroupedByGenderAndPointCardType();

    @Query("SELECT p FROM PointCard p WHERE p.id = :id")
    PointCard findPointCardById(@Param("id")Long id);
}
