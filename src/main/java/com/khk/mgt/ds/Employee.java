package com.khk.mgt.ds;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class Employee extends Person{


    private Date employmentDate;
    private String employmentType;
    private double salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentCategory departmentCategory;

    public Employee() {
    }
}
