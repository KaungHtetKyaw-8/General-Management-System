package com.khk.mgt.dto.common;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDto {

    @NotBlank
    private String searchType;
    @NotBlank
    private String value;

    public SearchDto() {
    }
}
