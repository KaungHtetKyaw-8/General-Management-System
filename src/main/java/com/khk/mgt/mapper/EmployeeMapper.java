package com.khk.mgt.mapper;

import com.khk.mgt.ds.Employee;
import com.khk.mgt.dto.EmployeeDto;

public class EmployeeMapper {

    public static Employee toEntity(EmployeeDto dto) {
        if (dto == null) return null;

        Employee entity = new Employee();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setGender(dto.getGender());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setEmploymentDate(dto.getEmploymentDate());
        entity.setEmploymentType(dto.getEmploymentType());
        entity.setDepartmentName(dto.getDepartmentName());
        entity.setSalary(dto.getSalary());

        // Nested objects
        if (dto.getAddress() != null) {
            entity.setAddress(AddressMapper.toEntity(dto.getAddress()));
        }

        return entity;
    }


    public static EmployeeDto toDto(Employee entity) {
        if (entity == null) return null;

        EmployeeDto dto = new EmployeeDto();

        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setGender(entity.getGender());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setEmploymentDate(entity.getEmploymentDate());
        dto.setEmploymentType(entity.getEmploymentType());
        dto.setDepartmentName(entity.getDepartmentName());
        dto.setSalary(entity.getSalary());

        // Nested objects
        if (entity.getAddress() != null) {
            dto.setAddress(AddressMapper.toDto(entity.getAddress()));
        }

        return dto;
    }
}
