package com.khk.mgt.dto.common;

import com.khk.mgt.ds.PointCard;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class CustomerDto extends PersonDto {

    @Null(groups = {OnCreate.class, OnUpdate.class})
    private List<PointCard> pointCard;

    public CustomerDto() {
        super();
        setAddress(new AddressDto());
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "firstname=" + getFirstName() +
                "lastname=" + getLastName() +
                "gender=" + getGender() +
                "email=" + getEmail() +
                "address=" + getAddress() +
                '}';
    }
}
