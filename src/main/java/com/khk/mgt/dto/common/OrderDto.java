package com.khk.mgt.dto.common;


import com.khk.mgt.validator.annotations.CustomerId;
import com.khk.mgt.validator.annotations.PointCardId;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrderDto {

    @NotNull
    private List<OrderDetailDto> itemList;

    @CustomerId(message = "{input.field.validation.order.customer.id.notfound}")
    private Long customerId;
    @PointCardId(message = "{input.field.validation.order.pointcard.id.notfound}")
    private Long pointCardId;

    public OrderDto() {
    }
}
