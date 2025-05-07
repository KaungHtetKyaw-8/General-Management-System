package com.khk.mgt.service;

import com.khk.mgt.dao.EmployeeDao;
import com.khk.mgt.ds.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeDao.findAll());
    }

    public Employee getEmployeeById(int id) {
        return employeeDao.findById(id).orElse(null);
    }

    public void saveEmployee(Employee employee) {
        employeeDao.save(employee);
    }

    public void saveEmployees(List<Employee> employees) {
        employeeDao.saveAll(employees);
    }


}
