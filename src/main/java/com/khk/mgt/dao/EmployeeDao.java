package com.khk.mgt.dao;

import com.khk.mgt.ds.Employee;
import com.khk.mgt.dto.chart.GroupedLabelValue;
import com.khk.mgt.dto.chart.LabelValue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {
    boolean existsByEmail(String email);

    List<Employee> findTop5ByOrderByEmploymentDateDesc();

    Optional<Employee> findById(Long id);

    @Query("SELECT e FROM Employee e WHERE " +
            "CAST(e.id AS string) LIKE :keyword OR " +
            "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Employee> searchIdOrNameByKeyword(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT e.departmentName FROM Employee e WHERE LOWER(e.departmentName) LIKE LOWER(CONCAT(:namePart, '%')) ORDER BY e.departmentName ASC")
    List<String> findSuggestionDepartmentNames(@Param("namePart") String namePart, Pageable pageable);

    @Query("SELECT DISTINCT e.employmentType FROM Employee e WHERE LOWER(e.employmentType) LIKE LOWER(CONCAT(:namePart, '%')) ORDER BY e.employmentType ASC")
    List<String> findSuggestionEmploymentType(@Param("namePart") String namePart, Pageable pageable);

    @Query("SELECT new com.khk.mgt.dto.chart.LabelValue(e.gender, COUNT(e)) FROM Employee e GROUP BY e.gender")
    List<LabelValue> findCountByGender();

    @Query("SELECT new com.khk.mgt.dto.chart.LabelValue(e.departmentName, COUNT(e)) FROM Employee e GROUP BY e.departmentName")
    List<LabelValue> findCountByDepartmentName();

    @Query("SELECT new com.khk.mgt.dto.chart.LabelValue(e.employmentType, COUNT(e)) FROM Employee e GROUP BY e.employmentType")
    List<LabelValue> findCountByEmploymentType();

    @Query("SELECT new com.khk.mgt.dto.chart.GroupedLabelValue(e.gender,e.address.city, COUNT(e)) FROM Employee e GROUP BY e.gender,e.address.city")
    List<GroupedLabelValue> findCountByCity();

    @Query("SELECT e.dateOfBirth FROM Employee e WHERE e.dateOfBirth IS NOT NULL")
    List<Date> findAllDateOfBirth();

    void deleteById(Long id);
}
