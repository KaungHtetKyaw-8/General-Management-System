package com.khk.mgt.dao;

import com.khk.mgt.ds.Employee;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {
    boolean existsByEmail(String email);

    @Query("SELECT DISTINCT e.departmentName FROM Employee e WHERE LOWER(e.departmentName) LIKE LOWER(CONCAT(:namePart, '%')) ORDER BY e.departmentName ASC")
    List<String> findSuggestionDepartmentNames(@Param("namePart") String namePart, Pageable pageable);
}
