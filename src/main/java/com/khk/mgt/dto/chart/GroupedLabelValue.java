package com.khk.mgt.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class GroupedLabelValue {
    private String group;   // e.g., "Male", "Female"
    private String label;   // e.g., "20~30", "30~40"
    private Number value;     // e.g., 15L

    public GroupedLabelValue() {
    }

    public GroupedLabelValue(String group, String label, Number value) {
        this.group = group;
        this.label = label;
        this.value = value;
    }

    public GroupedLabelValue(String group, Date label, Number value) {
        this.group = group;
        this.label = label.toString();
        this.value = value;
    }
}
