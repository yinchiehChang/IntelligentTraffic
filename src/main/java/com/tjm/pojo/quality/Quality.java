package com.tjm.pojo.quality;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Quality implements Serializable {
    //序号
    Integer uid;
    //任务编号
    String quality_id;
    //软件名称
    String software_name;
    //软件版本
    String software_version;
    //软件类别
    String software_category;
    //生产单位
    String production_unit;
    //软件说明
    String software_desc;
    //联系人
    String contact_person;
    //联系电话
    String contact_number;
    //规格型号
    String specification_model;
    //软件架构
    String software_arch;
    //数据库信息
    String db_info;
    //软件需求分析文档地址
    String require_doc_url;
    //软件设计文档地址
    String design_doc_url;
    //软件编码地址
    String coding_url;
    //软件测试文档地址
    String test_doc_url;
    //状态
    Integer status;

    //是否参考“公安交通集成指挥平台通用技术条件”
    String isGuideLine;
    //软件需求规格说明地址
    String specification_url;
    //软件说明书地址
    String manual_url;
    //代码文件地址
    String code_file_url;


}
