package com.khk.mgt.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GroupedLabelValue {
    private String group;   // e.g., "Male", "Female"
    private String label;   // e.g., "20~30", "30~40"
    private Long value;     // e.g., 15L

    public GroupedLabelValue() {
    }
}
