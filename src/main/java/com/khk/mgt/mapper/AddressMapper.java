package com.khk.mgt.mapper;

import com.khk.mgt.ds.Address;
import com.khk.mgt.dto.AddressDto;

public class AddressMapper {

    public static Address toEntity(AddressDto dto) {
        if (dto == null) return null;

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setCountryCode(dto.getCountryCode());
        address.setApartment(dto.getApartment());

        return address;
    }

    public static AddressDto toDto(Address address) {
        if (address == null) return null;

        AddressDto addressDto = new AddressDto();
        addressDto.setStreet(address.getStreet());
        addressDto.setCity(address.getCity());
        addressDto.setCountryCode(address.getCountryCode());
        addressDto.setApartment(address.getApartment());

        return addressDto;

    }
}
