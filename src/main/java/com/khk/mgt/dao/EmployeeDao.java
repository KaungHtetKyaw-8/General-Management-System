package com.khk.mgt.dao;

import com.khk.mgt.ds.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Integer> {
}
