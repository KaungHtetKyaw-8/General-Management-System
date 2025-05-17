package com.khk.mgt.dao;

import com.khk.mgt.ds.Customer;
import com.khk.mgt.ds.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerDao extends JpaRepository<Customer,Integer> {

    Optional<Customer> findById(Long id);

    void deleteById(Long id);

    @Query("SELECT c FROM Customer c WHERE " +
            "CAST(c.id AS string) LIKE :keyword OR " +
            "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Customer> searchIdOrNameByKeyword(@Param("keyword") String keyword);
}
