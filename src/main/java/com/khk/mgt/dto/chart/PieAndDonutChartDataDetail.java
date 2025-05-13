package com.khk.mgt.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PieAndDonutChartDataDetail extends ChartDataDetail {

    private int hoverOffset;

    public PieAndDonutChartDataDetail() {
        super();
    }

}
