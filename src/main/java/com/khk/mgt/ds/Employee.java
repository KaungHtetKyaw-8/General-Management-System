package com.khk.mgt.ds;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Employee extends Person{

    private Date employmentDate;
    private String employmentType;
    private String departmentName;
    private double salary;

    public Employee() {
    }

}
