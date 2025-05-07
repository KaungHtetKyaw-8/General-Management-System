package com.khk.mgt.dto;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@Component
@ApplicationScope
@Getter
public class TableHeaderList {
    private final List<String> personHeader = List.of("First Name","Last Name","Date of Birth","Gender","Email","Phone Number");
    private final List<String> addressHeader = List.of("Country","City","Street","Apartment");
    private final List<String> employeeHeader = List.of("Employment Date","Employment Type","Salary");
    private final List<String> departmentHeader = List.of("Department Name");
//    private final List<String> employeeHeader = List.of();

}
