package com.khk.mgt.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BarChartDataDetail extends ChartDataDetail {
    private List<String> borderColor;
    private int borderWidth;

    public BarChartDataDetail() {
        super();
        this.borderColor = new ArrayList<String>();
    }

}
