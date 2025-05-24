package com.khk.mgt.dto.common;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class EmployeeDto extends PersonDto {

    @NotNull(message = "{input.field.validation.employee.employmentdate}",groups = {OnCreate.class, OnUpdate.class})
    @FutureOrPresent(groups = {OnCreate.class})
    private Date employmentDate;

    @NotBlank(message = "{input.field.validation.employee.employmenttype}",groups = {OnCreate.class, OnUpdate.class})
    private String employmentType;

    @NotBlank(message = "{input.field.validation.employee.department}",groups = {OnCreate.class, OnUpdate.class})
    private String departmentName;

    @NotNull(message = "{input.field.validation.employee.salary.notblack}",groups = {OnCreate.class, OnUpdate.class})
    @DecimalMin(value = "0.0",message = "{input.field.validation.employee.salary.negative}",groups = {OnCreate.class, OnUpdate.class})
    private double salary;

    public EmployeeDto() {
        super();
        setAddress(new AddressDto());
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "personDto=" + super.toString() +
                "employmentDate=" + employmentDate +
                ", employmentType='" + employmentType + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", salary=" + salary +
                '}';
    }
}
