package com.khk.mgt.util;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateTimeUtil {
    public static List<String> getMonthNames() {
        return Stream.of(Month.values())
                .map(Month::name) // Gives "JANUARY", "FEBRUARY", ...
                .collect(Collectors.toList());
    }

    public static List<String> getWeekNames() {
        return Stream.of(DayOfWeek.values())
                .map(DayOfWeek::name) // Gives "MONDAY", "TUESDAY", ...
                .collect(Collectors.toList());
    }

    public static List<LocalDate> toLocalDate(List<Date> dates) {
        return dates.stream()
                .map(Date::toLocalDate)
                .collect(Collectors.toList());
    }

}
