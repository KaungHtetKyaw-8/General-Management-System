package com.khk.mgt.ds;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class Employee extends Person{

    private Date employmentDate;
    private String employmentType;
    private String departmentName;
    private double salary;

    public Employee() {
    }

    public Employee init(){
        this.setAddress(new Address());
        return this;
    }
}
