package com.khk.mgt.mapper;

import com.khk.mgt.ds.Customer;
import com.khk.mgt.dto.common.CustomerDto;
import org.springframework.beans.BeanUtils;


public class CustomerMapper {
    public static Customer toEntity(CustomerDto dto) {
        if (dto == null) return null;

        Customer entity = new Customer();

        BeanUtils.copyProperties(dto, entity);

        // Nested objects
        if (dto.getAddress() != null) {
            entity.setAddress(AddressMapper.toEntity(dto.getAddress()));
        }

        return entity;
    }


    public static CustomerDto toDto(Customer entity) {
        if (entity == null) return null;

        CustomerDto dto = new CustomerDto();

        BeanUtils.copyProperties(entity, dto);

        // Nested objects
        if (entity.getAddress() != null) {
            dto.setAddress(AddressMapper.toDto(entity.getAddress()));
        }

        return dto;
    }
}
