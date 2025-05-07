package com.khk.mgt.dto;

import com.khk.mgt.ds.DepartmentCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DepartmentTableDto extends TableHeaderDto{

    private List<DepartmentCategory> departmentCategories;

    public DepartmentTableDto() {
        super();
        this.departmentCategories = new ArrayList<DepartmentCategory>();
    }

}
