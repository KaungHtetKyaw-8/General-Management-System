package com.khk.mgt.dto.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PosProductDto {

    private Long productId;
    private String productName;
    private Long productCount;
    private Double productPrice;

}
