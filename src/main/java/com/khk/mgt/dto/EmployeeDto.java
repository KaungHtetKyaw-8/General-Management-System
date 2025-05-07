package com.khk.mgt.dto;

import com.khk.mgt.ds.Employee;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@SessionScope
public class EmployeeDto extends TableHeaderDto{

    private List<Employee> listData;

    private Employee data;

    public EmployeeDto() {
        super();
        this.listData = new ArrayList<Employee>();
        this.data = new Employee().init();
    }
}
