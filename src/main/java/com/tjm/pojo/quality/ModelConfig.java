package com.tjm.pojo.quality;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModelConfig implements Serializable {

    Integer primary_id;
    Integer uid;
    String indicator_name;
    String super_indicator_name;
    double index_result;
    double index_weight;

    //三级指标计算公式分子
    String molecular;
    //三级指标计算公式分母
    String denominator;
    //三级指标计算公式分子结果
    double molecular_result;
    //三级指标计算公式分母结果
    double denominator_result;

    //计算模式
    String cal_mode;


    public ModelConfig(Integer uid, String indicator_name, String super_indicator_name, double index_result, double index_weight) {
        this.uid = uid;
        this.indicator_name = indicator_name;
        this.super_indicator_name = super_indicator_name;
        this.index_result = index_result;
        this.index_weight = index_weight;
    }

    public ModelConfig(Integer uid, String indicator_name, String super_indicator_name, double index_result, double index_weight, String molecular, String denominator, double molecular_result, double denominator_result) {
        this.uid = uid;
        this.indicator_name = indicator_name;
        this.super_indicator_name = super_indicator_name;
        this.index_result = index_result;
        this.index_weight = index_weight;
        this.molecular = molecular;
        this.denominator = denominator;
        this.molecular_result = molecular_result;
        this.denominator_result = denominator_result;
    }
}
