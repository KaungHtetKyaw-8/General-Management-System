package com.khk.mgt.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter

public class EmployeeDto extends PersonDto {

    @NotNull(message = "EmploymentDate must not blank.")
    @FutureOrPresent
    private Date employmentDate;

    @NotBlank(message = "EmploymentType must not blank.")
    private String employmentType;

    @NotBlank(message = "DepartmentName must not blank.")
    private String departmentName;

    @NotNull(message = "Salary must not blank.")
    @DecimalMin(value = "0.0",message = "Salary must not negative value.")
    private double salary;

    public EmployeeDto() {
        super();
        setAddress(new AddressDto());
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "firstname=" + getFirstName() +
                "lastname=" + getLastName() +
                "gender=" + getGender() +
                "email=" + getEmail() +
                "address=" + getAddress() +
                "employmentDate=" + employmentDate +
                ", employmentType='" + employmentType + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", salary=" + salary +
                '}';
    }
}
