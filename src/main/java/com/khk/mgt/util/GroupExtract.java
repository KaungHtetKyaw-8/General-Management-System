package com.khk.mgt.util;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class GroupExtract {

    public Map<String, Long> groupByMonth(List<LocalDate> dates) {
        return dates.stream()
                .collect(Collectors.groupingBy(
                        date -> date.getMonth().getDisplayName(TextStyle.NARROW, Locale.JAPANESE) + " " + date.getYear(),
                        TreeMap::new, // Keeps keys sorted by month
                        Collectors.counting()
                ));
    }

    public static Map<String, Long> groupAgeRanges(List<Integer> ages, int interval) {
        return ages.stream()
                .collect(Collectors.groupingBy(
                        age -> {
                            int start = (age / interval) * interval;
                            int end = start + interval - 1;
                            return start + "~" + end;
                        },
                        TreeMap::new,
                        Collectors.counting()
                ));
    }
}
