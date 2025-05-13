package com.khk.mgt.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LabelValue {
    private String label;
    private Long value;

    public LabelValue() {
    }
}
