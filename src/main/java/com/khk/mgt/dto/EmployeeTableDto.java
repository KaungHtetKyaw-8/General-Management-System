package com.khk.mgt.dto;

import com.khk.mgt.ds.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmployeeTableDto extends TableHeaderDto{

    private List<Employee> data;

    public EmployeeTableDto() {
        super();
        this.data = new ArrayList<Employee>();
    }
}
