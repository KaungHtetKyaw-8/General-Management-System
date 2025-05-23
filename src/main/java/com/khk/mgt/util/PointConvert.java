package com.khk.mgt.util;

public class PointConvert{
    private static final Integer per = 100;
    private double point;

    public void setAndCalculatePoint(double Qty,double price){
        point += (Qty * price)/per;
    }

    public Long getPoint(){
        return Math.round(point);
    }
}