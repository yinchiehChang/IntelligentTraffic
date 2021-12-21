package com.tjm.pojo.quality;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FinalScore implements Serializable {
    //序号
    Integer uid;
    //任务编号
    String quality_id;
    //软件名称
    String software_name;
    //软件版本
    String software_version;
    //生产单位
    String production_unit;
    //软件类别
    String software_category;


    //综合得分
    Double final_score;
    //基础模型
    String basic_model;

}
