package com.khk.mgt.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TableHeaderDto {

    private List<String> header;

    public TableHeaderDto() {
        this.header = new ArrayList<String>();
    }
}
