package com.khk.mgt.service;

import com.khk.mgt.dto.chart.*;
import com.khk.mgt.mapper.ChartMapper;
import com.khk.mgt.util.ColorUtil;
import com.khk.mgt.util.DateTimeUtil;
import com.khk.mgt.util.GroupExtract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChartsService {

    public static final int EMPLOYEE_GENDER = 1001;
    public static final int EMPLOYEE_DEPARTMENT = 1002;
    public static final int EMPLOYEE_EMPLOYMENT = 1003;
    public static final int EMPLOYEE_AGE_RANGE = 1004;
    public static final int EMPLOYEE_CITY = 1005;

    public static final int CUSTOMER_GENDER = 2001;
    public static final int CUSTOMER_AGE_RANGE = 2002;
    public static final int CUSTOMER_POINT_CARD_CATEGORY = 2003;
    public static final int CUSTOMER_POINTS = 2004;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;


    public ChartDto<PieAndDonutChartDataDetail> getDonutSingleDataChart(int chartName,String dataName,Long limit){
        // Get the data form the DB
        List<LabelValue> rows = selectSingleRepoData(chartName).stream().limit(limit).collect(Collectors.toList());

        if (rows.isEmpty()){
            return null;
        }

        return ChartMapper.toChartDto(rows,dataName,() -> {
            PieAndDonutChartDataDetail dataDetail = new PieAndDonutChartDataDetail();
            List<String> colors = ColorUtil.generateColorList(rows.size());

            dataDetail.setHoverOffset(rows.size() / 2);
            dataDetail.setBackgroundColor(colors);

            return dataDetail;
        });
    }

    public ChartDto<BarChartDataDetail> getBarMultiDataChart(int chartName,Long limit){
        // Get the data form the DB
        List<GroupedLabelValue> rows = selectMultiRepoData(chartName).stream().limit(limit).collect(Collectors.toList());

        if (rows.isEmpty()){
            return null;
        }

        return ChartMapper.toGroupedChartDto(rows,BarChartDataDetail::new);
    }

    private List<LabelValue> selectSingleRepoData(int code){
        return switch (code) {
            // Employee
            case EMPLOYEE_GENDER -> employeeService.getCountByGender();
            case EMPLOYEE_DEPARTMENT -> employeeService.getCountByDepartmentName();
            case EMPLOYEE_EMPLOYMENT -> employeeService.getCountByEmploymentType();
            case EMPLOYEE_AGE_RANGE -> employeeAgeDifference(employeeService.getAllDateOfBirth());
            // Customer
            case CUSTOMER_GENDER -> customerService.getCountByGender();
            case CUSTOMER_AGE_RANGE -> employeeAgeDifference(customerService.getAllDateOfBirth());
            case CUSTOMER_POINT_CARD_CATEGORY -> customerService.getCountByPointCardCategory();
            default -> new ArrayList<>();
        };
    }

    private List<GroupedLabelValue> selectMultiRepoData(int name){
        return switch (name) {
            //Employee
            case EMPLOYEE_CITY -> employeeService.getCountByCity();
            // Customer
            case CUSTOMER_POINTS -> customerService.getPointByGenderAndCardType();

            default -> new ArrayList<>();
        };
    }

    private List<LabelValue> employeeAgeDifference(List<Date> dobs){
        if (dobs.isEmpty()) {
            return new ArrayList<>();
        }

        // SQL Date to Local Date
        List<LocalDate> dates = DateTimeUtil.toLocalDate(dobs);

        // Date of Birth TO Ages
        List<Integer> ages = dates.stream()
                .map(date -> LocalDate.now().getYear() - date.getYear())
                .collect(Collectors.toList());

        // Grouping with age range
        Map<String,Long> ageGroup = GroupExtract.groupAgeRanges(ages,10);

        // Listing for Charts
        List<LabelValue> labelAndData = new ArrayList<>();
        ageGroup.forEach((k,v)->{
            labelAndData.add(new LabelValue(k,v));
        });

        return labelAndData;
    }
}
