package com.khk.mgt.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ChartDto<T extends ChartDataDetail> {
    List<String> labels;
    List<T> datasets;

    public ChartDto() {
        labels = new ArrayList<>();
        datasets = new ArrayList<>();
    }
}
