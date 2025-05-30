package com.khk.mgt.dto.common;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrderDetailDto {


    private long productId;
    private String productName;
    private long productQty;
    private double productPrice;

    public OrderDetailDto() {
    }
}
