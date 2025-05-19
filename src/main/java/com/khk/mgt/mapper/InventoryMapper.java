package com.khk.mgt.mapper;

import com.khk.mgt.ds.Product;
import com.khk.mgt.ds.Vendor;
import com.khk.mgt.dto.common.InventoryDto;
import com.khk.mgt.dto.common.VendorDto;
import org.springframework.beans.BeanUtils;


public class InventoryMapper {
    public static Product toEntity(InventoryDto dto) {
        if (dto == null) return null;

        Product entity = new Product();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setBuyPrice(dto.getBuyPrice());
        entity.setSellPrice(dto.getSellPrice());
        entity.setCount(dto.getCount());

        return entity;
    }


    public static InventoryDto toDto(Product entity) {
        if (entity == null) return null;

        InventoryDto dto = new InventoryDto();

        dto.setId(entity.getId());
        dto.setVendorId(entity.getVendor().getId());
        dto.setCategoryId(entity.getCategory().getId());
        dto.setName(entity.getName());
        dto.setBuyPrice(entity.getBuyPrice());
        dto.setSellPrice(entity.getSellPrice());
        dto.setCount(entity.getCount());
        dto.setCategoryName(entity.getCategory().getName());
        dto.setVendorName(entity.getVendor().getFirstName() + " " + entity.getVendor().getLastName());
        dto.setCompanyName(entity.getVendor().getCompanyName());

        return dto;
    }
}
