package com.khk.mgt.dto.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Getter
@Setter
public class LabelValue {
    private String label;
    private Long value;

    public LabelValue() {
    }

    public LabelValue(String label, Long value) {
        this.label = label;
        this.value = value;
    }

    public LabelValue(String label, Double value) {
        this.label = label;
        this.value = Math.round(value);
    }

    public LabelValue(Date date, Long value) {
        this.label = new SimpleDateFormat("yyyy-MM-dd").format(date);
        this.value = value;
    }
}
