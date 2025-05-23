package com.khk.mgt.mapper;

import com.khk.mgt.ds.Vendor;
import com.khk.mgt.dto.common.VendorDto;
import org.springframework.beans.BeanUtils;


public class VendorMapper {
    public static Vendor toEntity(VendorDto dto) {
        if (dto == null) return null;

        Vendor entity = new Vendor();

        BeanUtils.copyProperties(dto, entity);

        // Nested objects
        if (dto.getAddress() != null) {
            entity.setAddress(AddressMapper.toEntity(dto.getAddress()));
        }

        return entity;
    }


    public static VendorDto toDto(Vendor entity) {
        if (entity == null) return null;

        VendorDto dto = new VendorDto();

        BeanUtils.copyProperties(entity, dto);

        // Nested objects
        if (entity.getAddress() != null) {
            dto.setAddress(AddressMapper.toDto(entity.getAddress()));
        }

        return dto;
    }
}
