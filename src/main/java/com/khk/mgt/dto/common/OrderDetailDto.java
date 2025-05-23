package com.khk.mgt.dto.common;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrderDetailDto {

    @NotNull
    private long productId;
    @NotNull
    private long productQty;

    public OrderDetailDto() {
    }
}
