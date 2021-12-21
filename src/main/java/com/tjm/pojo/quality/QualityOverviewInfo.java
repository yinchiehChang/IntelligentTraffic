package com.tjm.pojo.quality;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Deprecated
public class QualityOverviewInfo implements Serializable {
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

    //基础模型
    String basic_model;
    //综合得分
    Double final_score = 100.00;

    //一级指标
    //功能性
    String feature = "功能性";
    Double feature_score = 91.50;
    //可靠性
    String reliable = "可靠性";
    Double reliable_score = 99.50;
    //易用性
    String usable = "易用性";
    Double usable_score = 79.60;
    //效率
    String efficiency = "效率";
    Double efficiency_score = 95.30;
    //可维护性
    String maintainability = "可维护性";
    Double maintainability_score = 88.20;
    //可移植性
    String portability = "可移植性";
    Double portability_score = 94.30;
    //安全性
    String security = "安全性";
    Double security_score = 75.60;
    //自主性
    String independence = "自主性";
    Double independence_score = 80.50;

    //功能性二级指标
    //标准符合性
    String standard_compliance = "标准符合性";
    Double standard_compliance_score = 100.00;
    //适合性
    String suitability = "适合性";
    Double suitability_score = 100.00;
    //准确性
    String accuracy = "准确性";
    Double accuracy_score = 100.00;
    //互操作性
    String interoperability = "互操作性";
    Double interoperability_score = 100.00;

    //可靠性二级指标
    //成熟性
    String maturity = "成熟性";
    Double maturity_score = 100.00;
    //容错性
    String fault_tolerance = "容错性";
    Double fault_tolerance_score = 100.00;
    //可恢复性
    String recoverability = "可恢复性";
    Double recoverability_score = 100.00;
    //稳定性
    String stability = "稳定性";
    Double stability_score = 100.00;

    //易用性二级指标
    //易操作性
    String easy_operation = "易操作性";
    Double easy_operation_score = 100.00;
    //易学习性
    String easy_learning = "易学习性";
    Double easy_learning_score = 100.00;
    //易理解性
    String understandability = "易理解性";
    Double understandability_score = 100.00;
    //错误防御
    String fault_defence = "错误防御";
    Double fault_defence_score = 100.00;
    //界面美观
    String good_ui = "界面美观";
    Double good_ui_score = 100.00;

    //效率二级指标
    //有效性
    String effectiveness = "有效性";
    Double effectiveness_score = 100.00;
    //资源利用性
    String resource_use = "资源利用性";
    Double resource_use_score = 100.00;
    //时间特性
    String time_feature = "时间特性";
    Double time_feature_score = 100.00;
    //容量
    String capacity = "容量";
    Double capacity_score = 100.00;
    //流畅度
    String fluency = "流畅度";
    Double fluency_score = 100.00;

    //可维护性二级指标
    //软件复杂度
    String software_complexity = "软件复杂度";
    Double software_complexity_score = 100.00;
    //易用性
    String easy_use = "易用性";
    Double easy_use_score = 100.00;
    //模块性
    String modularity = "模块性";
    Double modularity_score = 100.00;
    //易修改性
    String easy_revise = "易修改性";
    Double easy_revise_score = 100.00;
    //易测试性
    String easy_test = "易测试性";
    Double easy_test_score = 100.00;

    //可移植性二级指标
    //适用性
    String applicability = "适用性";
    Double applicability_score = 100.00;
    //易安装性
    String easy_install = "易安装性";
    Double easy_install_score = 100.00;

    //安全性二级指标
    //保密性
    String confidentiality = "保密性";
    Double confidentiality_score = 100.00;
    //完整性
    String completeness = "完整性";
    Double completeness_score = 100.00;
    //不可抵赖性
    String non_repudiation = "不可抵赖性";
    Double non_repudiation_score = 100.00;
    //漏洞情况
    String vulnerability_situation = "漏洞情况";
    Double vulnerability_situation_score = 100.00;
    //真实性
    String authenticity = "真实性";
    Double authenticity_score = 100.00;

    //自主性二级指标
    //技术自主
    String technology_independence = "技术自主";
    Double technology_independence_score = 100.00;
    //产权自主
    String property_independence = "产权自主";
    Double property_independence_score = 100.00;
    //组织管理
    String organization_management = "组织管理";
    Double organization_management_score = 100.00;
    //持续发展
    String continuous_development = "持续发展";
    Double continuous_development_score = 100.00;

}
