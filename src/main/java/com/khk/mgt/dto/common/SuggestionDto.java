package com.khk.mgt.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SuggestionDto {
    private boolean error;
    private String msg;
    private List<String> data;

    public SuggestionDto() {
        this.error = false;
        this.msg = "Suggestion Data Loaded";
    }
}
