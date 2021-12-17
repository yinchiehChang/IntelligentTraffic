package com.tjm.pojo.quality;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Indicator {
    //指标名称
    public String indicatorName;
    //指标描述
    public String indicatorDescription;
    //上一级指标名
    public String superIndicatorName;
    //子指标集合
    public ArrayList<Indicator> subIndicator;

    //三级指标计算公式分子
    public String molecular;
    //三级指标计算公式分母
    public String denominator;

    private int indicatorId;

    public Indicator(String indicatorName, String indicatorDescription, ArrayList<Indicator> subIndicator) {
        this.indicatorName = indicatorName;
        this.indicatorDescription = indicatorDescription;
        this.subIndicator = subIndicator;
    }

    public Indicator(String indicatorName, String indicatorDescription) {
        this.indicatorName = indicatorName;
        this.indicatorDescription = indicatorDescription;
        this.subIndicator = new ArrayList<>();
    }

    public Indicator(String indicatorName, String indicatorDescription, String superIndicatorName) {
        this.indicatorName = indicatorName;
        this.indicatorDescription = indicatorDescription;
        this.superIndicatorName = superIndicatorName;
    }

    public Indicator(String indicatorName, String indicatorDescription, String superIndicatorName, ArrayList<Indicator> subIndicator) {
        this.indicatorName = indicatorName;
        this.indicatorDescription = indicatorDescription;
        this.superIndicatorName = superIndicatorName;
        this.subIndicator = subIndicator;
    }

    public int getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(int indicatorId) {
        this.indicatorId = indicatorId;
    }
}

