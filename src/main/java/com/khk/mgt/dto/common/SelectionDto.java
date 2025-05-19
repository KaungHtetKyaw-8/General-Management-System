package com.khk.mgt.dto.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SelectionDto {

    private Long id;
    private String name;

    public SelectionDto() {
    }
}
