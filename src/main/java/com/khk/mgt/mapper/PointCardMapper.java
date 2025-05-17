package com.khk.mgt.mapper;

import com.khk.mgt.ds.PointCard;
import com.khk.mgt.dto.common.PointCardDto;

public class PointCardMapper {

    public static PointCardDto toDto(PointCard entity) {
        if (entity == null) return null;

        PointCardDto dto = new PointCardDto();

        dto.setCustomerId(entity.getCustomer().getId());
        dto.setFirstName(entity.getCustomer().getFirstName());
        dto.setLastName(entity.getCustomer().getLastName());
        dto.setGender(entity.getCustomer().getGender());

        dto.setPointCardId(entity.getId());
        dto.setRegistrationDate(entity.getRegistrationDate());
        dto.setPointCardType(entity.getCategory().getType());
        dto.setPoints(entity.getPoints());

        return dto;
    }
}
