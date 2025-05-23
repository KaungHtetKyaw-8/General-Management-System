package com.khk.mgt.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ChartDataDetail {
    String label;
    List<Number> data;
    List<String> backgroundColor;

    public ChartDataDetail() {
        this.data = new ArrayList<>();
        this.backgroundColor = new ArrayList<>();
    }
}
