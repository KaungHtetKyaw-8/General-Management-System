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

    @NotBlank(message = "Category Name must not be blank",groups = {OnCreate.class, OnUpdate.class})
    private String name;

    public ProductCategoryDto() {
    }
}
