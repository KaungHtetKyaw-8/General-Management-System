package com.khk.mgt.dto.common;


import com.khk.mgt.validator.annotations.CustomerId;
import com.khk.mgt.validator.annotations.PointCardId;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrderDto {

    private List<OrderDetailDto> itemList;

    private Long orderId;

    private String customerName;
    private Date orderDate;


    public OrderDto() {
    }
}
