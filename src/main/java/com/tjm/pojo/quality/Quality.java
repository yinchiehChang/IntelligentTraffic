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
    //状态
    Integer status;

    //得分
    double final_score;
    //模型
    String model;

    //测试用例参考标准
    String isGuideLine;
    //软件需求规格说明地址
    String specification_url;
    //软件说明书地址
    String manual_url;
    //代码文件地址
    String code_file_url;

    //创建时间
    Long gmt_created;
    //修改时间
    Long gmt_modified;

    public Quality(Integer uid, String quality_id, String software_name, String software_version,
                   String software_category, String production_unit, String software_desc, String contact_person,
                   String contact_number, String specification_model, String software_arch, String db_info,
                   Integer status, String isGuideLine, String specification_url, String manual_url, String code_file_url) {
        this.uid = uid;
        this.quality_id = quality_id;
        this.software_name = software_name;
        this.software_version = software_version;
        this.software_category = software_category;
        this.production_unit = production_unit;
        this.software_desc = software_desc;
        this.contact_person = contact_person;
        this.contact_number = contact_number;
        this.specification_model = specification_model;
        this.software_arch = software_arch;
        this.db_info = db_info;
        this.status = status;
        this.isGuideLine = isGuideLine;
        this.specification_url = specification_url;
        this.manual_url = manual_url;
        this.code_file_url = code_file_url;
    }


}
