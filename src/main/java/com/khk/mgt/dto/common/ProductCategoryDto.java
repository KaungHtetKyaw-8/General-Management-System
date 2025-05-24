package com.khk.mgt.dto.common;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProductCategoryDto {

    private Long id;

    @NotBlank(message = "{input.field.validation.product.category.name.notblank}",groups = {OnCreate.class, OnUpdate.class})
    private String name;

    private Long count;
    private Double totalPrice;

    public ProductCategoryDto() {
    }

    public ProductCategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductCategoryDto(String name, Long count, Double totalPrice) {
        this.name = name;
        this.count = count;
        this.totalPrice = totalPrice;
    }
}
