package com.khk.mgt.dao;

import com.khk.mgt.ds.Vendor;
import com.khk.mgt.dto.chart.LabelValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorDao extends JpaRepository<Vendor, Long> {


    List<Vendor> findByCompanyName(String companyName);

    @Query("SELECT new com.khk.mgt.dto.chart.LabelValue(v.gender, COUNT(v)) FROM Vendor v GROUP BY v.gender")
    List<LabelValue> findCountByGender();
}
