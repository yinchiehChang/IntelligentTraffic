package com.tjm.pojo.quality;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Indicator {
    //指标ID
    public int indicatorId;
    //指标名称
    public String indicatorName;
    //指标描述
    public String indicatorDescription = "No Description";
    //上一级指标名
    public String superIndicatorName;
    //子指标集合
    public ArrayList<Indicator> subIndicator;

    //指标得分
    public double indicatorResult;
    //指标权重
    public double indicatorWeight;

    //三级指标计算公式分子
    public String molecular;
    //三级指标计算公式分母
    public String denominator;
    //三级指标计算公式分子结果
    public double molecularResult;
    //三级指标计算公式分母结果
    public double denominatorResult;

    //指标计算方式
    String calMode;
    //测量输入，知识库展示用
    public String input;

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

    public Indicator(String indicatorName, String indicatorDescription, double indicatorResult, double indicatorWeight) {
        this.indicatorName = indicatorName;
        this.indicatorDescription = indicatorDescription;
        this.indicatorResult = indicatorResult;
        this.indicatorWeight = indicatorWeight;
    }

    public Indicator(String indicatorName, String indicatorDescription, ArrayList<Indicator> subIndicator, double indicatorResult, double indicatorWeight) {
        this.indicatorName = indicatorName;
        this.indicatorDescription = indicatorDescription;
        this.subIndicator = subIndicator;
        this.indicatorResult = indicatorResult;
        this.indicatorWeight = indicatorWeight;
    }

    public Indicator(String indicatorName, String indicatorDescription, String superIndicatorName) {
        this.indicatorName = indicatorName;
        this.indicatorDescription = indicatorDescription;
        this.superIndicatorName = superIndicatorName;
    }

    public Indicator(String indicatorName, String indicatorDescription, String superIndicatorName, double indicatorResult, double indicatorWeight) {
        this.indicatorName = indicatorName;
        this.indicatorDescription = indicatorDescription;
        this.superIndicatorName = superIndicatorName;
        this.indicatorResult = indicatorResult;
        this.indicatorWeight = indicatorWeight;
    }

    public Indicator(String indicatorName, String indicatorDescription, String superIndicatorName, ArrayList<Indicator> subIndicator) {
        this.indicatorName = indicatorName;
        this.indicatorDescription = indicatorDescription;
        this.superIndicatorName = superIndicatorName;
        this.subIndicator = subIndicator;
    }

    public Indicator(String indicatorName, String indicatorDescription, String superIndicatorName, ArrayList<Indicator> subIndicator, double indicatorResult, double indicatorWeight) {
        this.indicatorName = indicatorName;
        this.indicatorDescription = indicatorDescription;
        this.superIndicatorName = superIndicatorName;
        this.subIndicator = subIndicator;
        this.indicatorResult = indicatorResult;
        this.indicatorWeight = indicatorWeight;
    }

    public Indicator(int indicatorId, String indicatorName, String indicatorDescription) {
        this.indicatorId = indicatorId;
        this.indicatorName = indicatorName;
        this.indicatorDescription = indicatorDescription;
        this.subIndicator = new ArrayList<>();
    }

    public Indicator(int indicatorId, String indicatorName, String indicatorDescription, String molecular, String denominator) {
        this.indicatorId = indicatorId;
        this.indicatorName = indicatorName;
        this.indicatorDescription = indicatorDescription;
        this.molecular = molecular;
        this.denominator = denominator;
    }

    public Indicator(int indicatorId, String indicatorName, String indicatorDescription, ArrayList<Indicator> subIndicator, String molecular, String denominator) {
        this.indicatorId = indicatorId;
        this.indicatorName = indicatorName;
        this.indicatorDescription = indicatorDescription;
        this.subIndicator = subIndicator;
        this.molecular = molecular;
        this.denominator = denominator;
    }

    public int getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(int indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getIndicatorDescription() {
        return indicatorDescription;
    }

    public void setIndicatorDescription(String indicatorDescription) {
        this.indicatorDescription = indicatorDescription;
    }

    public String getSuperIndicatorName() {
        return superIndicatorName;
    }

    public void setSuperIndicatorName(String superIndicatorName) {
        this.superIndicatorName = superIndicatorName;
    }

    public ArrayList<Indicator> getSubIndicator() {
        return subIndicator;
    }

    public void setSubIndicator(ArrayList<Indicator> subIndicator) {
        this.subIndicator = subIndicator;
    }

    public String getMolecular() {
        return molecular;
    }

    public void setMolecular(String molecular) {
        this.molecular = molecular;
    }

    public String getDenominator() {
        return denominator;
    }

    public void setDenominator(String denominator) {
        this.denominator = denominator;
    }
}

