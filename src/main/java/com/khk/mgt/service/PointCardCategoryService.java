package com.khk.mgt.service;

import com.khk.mgt.dao.PointCardCategoryDao;
import com.khk.mgt.ds.PointCardCategory;
import com.khk.mgt.dto.chart.LabelValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointCardCategoryService {

    @Autowired
    private PointCardCategoryDao pointCardCategoryDao;

    public List<PointCardCategory> getTypeAll() {
        return pointCardCategoryDao.findAll();
    }
}
