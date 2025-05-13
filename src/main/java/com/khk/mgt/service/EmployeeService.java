package com.khk.mgt.service;

import com.khk.mgt.dao.EmployeeDao;
import com.khk.mgt.ds.Employee;
import com.khk.mgt.dto.common.EmployeeDto;
import com.khk.mgt.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public void saveEmployee(EmployeeDto employee) {
        employeeDao.save(EmployeeMapper.toEntity(employee));
    }

    public void saveEmployeeAll(List<EmployeeDto> employees) {
        employeeDao.saveAll(employees.stream()
                .map(EmployeeMapper::toEntity)
                .collect(Collectors.toList())
        );
    }

    public List<String> suggestDepartmentName(String departmentName) {
        Pageable top5 = PageRequest.of(0, 5);
        return employeeDao.findSuggestionDepartmentNames(departmentName, top5);
    }

    public List<String> suggestEmploymentType(String employmentType) {
        Pageable top5 = PageRequest.of(0, 5);
        return employeeDao.findSuggestionEmploymentType(employmentType, top5);
    }


}
