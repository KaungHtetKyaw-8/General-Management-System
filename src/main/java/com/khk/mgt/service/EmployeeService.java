package com.khk.mgt.service;

import com.khk.mgt.dao.EmployeeDao;
import com.khk.mgt.ds.Employee;
import com.khk.mgt.dto.chart.GroupedLabelValue;
import com.khk.mgt.dto.chart.LabelValue;
import com.khk.mgt.dto.common.EmployeeDto;
import com.khk.mgt.mapper.CustomerMapper;
import com.khk.mgt.mapper.EmployeeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    // ThymeLeaf
    public List<EmployeeDto> getAllEmployees() {
        return employeeDao.findAll()
                .stream().map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean emailExists(String email) {
        return employeeDao.existsByEmail(email);
    }

    public EmployeeDto getEmployeeById(long id) {
        return employeeDao.findById(id)
                .map(EmployeeMapper::toDto)
                .orElse(null);
    }

    public List<EmployeeDto> searchIdOrName(String keyword) {
        return employeeDao.searchIdOrNameByKeyword(keyword)
                .stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EmployeeDto> newest5Employees() {
        return employeeDao.findTop5ByOrderByEmploymentDateDesc()
                .stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public void updateEmployee(EmployeeDto employee) {
        Employee existEmp = employeeDao.findById(employee.getId()).orElse(null);
        if (existEmp != null) {
            Employee updateEmp = EmployeeMapper.toEntity(employee);
            BeanUtils.copyProperties(updateEmp, existEmp, "id", "address");

            if (updateEmp.getAddress() != null && existEmp.getAddress() != null) {
                BeanUtils.copyProperties(updateEmp.getAddress(), existEmp.getAddress(), "id");
            }

            employeeDao.save(existEmp);
        }
    }

    public void deleteEmployee(long id) {
        employeeDao.deleteById(id);
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

    // For Chart
    public List<LabelValue> getCountByGender() {
        return employeeDao.findCountByGender();
    }

    public List<LabelValue> getCountByDepartmentName() {
        return employeeDao.findCountByDepartmentName();
    }

    public List<LabelValue> getCountByEmploymentType() {
        return employeeDao.findCountByEmploymentType();
    }

    public List<GroupedLabelValue> getCountByCity() {
        return employeeDao.findCountByCity();
    }

    public List<Date> getAllDateOfBirth() {
        return employeeDao.findAllDateOfBirth();
    }


    // For Rest
    public List<String> suggestDepartmentName(String departmentName) {
        Pageable top5 = PageRequest.of(0, 5);
        return employeeDao.findSuggestionDepartmentNames(departmentName, top5);
    }

    public List<String> suggestEmploymentType(String employmentType) {
        Pageable top5 = PageRequest.of(0, 5);
        return employeeDao.findSuggestionEmploymentType(employmentType, top5);
    }
}
