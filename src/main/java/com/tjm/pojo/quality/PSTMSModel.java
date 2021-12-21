package com.tjm.pojo.quality;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Deprecated
public class PSTMSModel implements Serializable {

    String model_name = "公安交管软件质量模型";

    //一级指标
    //功能性
    String feature = "功能性";
    String feature_desc = "实现的功能达到设计规范并满足用户需求的程度";
    //可靠性
    String reliable = "可靠性";
    String reliable_desc = "在规定的时间和条件下,维持其性能水准的程度";
    //易用性
    String usable = "易用性";
    String usable_desc = "易用性描述";
    //效率
    String efficiency = "效率";
    String efficiency_desc = "效率描述";
    //可维护性
    String maintainability = "可维护性";
    String maintainability_desc = "可维护性描述";
    //可移植性
    String portability = "可移植性";
    String portability_desc = "可移植性描述";
    //安全性
    String security = "安全性";
    String security_desc = "安全性描述";
    //自主性
    String independence = "自主性";
    String independence_desc = "自主性描述";

    //功能性二级指标
    //标准符合性
    String standard_compliance = "标准符合性";
    String standard_compliance_desc = "标准符合性描述";
    //适合性
    String suitability = "适合性";
    String suitability_desc = "适合性描述";
    //准确性
    String accuracy = "准确性";
    String accuracy_desc = "准确性描述";
    //互操作性
    String interoperability = "互操作性";
    String interoperability_desc = "互操作性描述";

    //可靠性二级指标
    //成熟性
    String maturity = "成熟性";
    String maturity_desc = "成熟性描述";
    //容错性
    String fault_tolerance = "容错性";
    String fault_tolerance_desc = "容错性描述";
    //可恢复性
    String recoverability = "可恢复性";
    String recoverability_desc = "可恢复性描述";
    //稳定性
    String stability = "稳定性";
    String stability_desc = "稳定性描述";

    //易用性二级指标
    //易操作性
    String easy_operation = "易操作性";
    String easy_operation_desc = "易操作性描述";
    //易学习性
    String easy_learning = "易学习性";
    String easy_learning_desc = "易学习性描述";
    //易理解性
    String understandability = "易理解性";
    String understandability_desc = "易理解性描述";
    //错误防御
    String fault_defence = "错误防御";
    String fault_defence_desc = "错误防御描述";
    //界面美观
    String good_ui = "界面美观";
    String good_ui_desc = "界面美观描述";

    //效率二级指标
    //有效性
    String effectiveness = "有效性";
    String effectiveness_desc = "有效性描述描述";
    //资源利用性
    String resource_use = "资源利用性";
    String resource_use_desc = "资源利用性描述";
    //时间特性
    String time_feature = "时间特性";
    String time_feature_desc = "时间特性描述";
    //容量
    String capacity = "容量";
    String capacity_desc = "容量描述";
    //流畅度
    String fluency = "流畅度";
    String fluency_desc = "流畅度描述";

    //可维护性二级指标
    //软件复杂度
    String software_complexity = "软件复杂度";
    String software_complexity_desc = "软件复杂度描述";
    //易用性
    String easy_use = "易用性";
    String easy_use_desc = "易用性描述";
    //模块性
    String modularity = "模块性";
    String modularity_desc = "模块性描述";
    //易修改性
    String easy_revise = "易修改性";
    String easy_revise_desc = "易修改性描述";
    //易测试性
    String easy_test = "易测试性";
    String easy_test_desc = "易测试性描述";

    //可移植性二级指标
    //适用性
    String applicability = "适用性";
    String applicability_desc = "适用性描述";
    //易安装性
    String easy_install = "易安装性";
    String easy_install_desc = "易安装性描述";

    //安全性二级指标
    //保密性
    String confidentiality = "保密性";
    String confidentiality_desc = "保密性描述";
    //完整性
    String completeness = "完整性";
    String completeness_desc = "完整性描述";
    //不可抵赖性
    String non_repudiation = "不可抵赖性";
    String non_repudiation_desc = "不可抵赖性描述";
    //漏洞情况
    String vulnerability_situation = "漏洞情况";
    String vulnerability_situation_desc = "漏洞情况描述";
    //真实性
    String authenticity = "真实性";
    String authenticity_desc = "真实性描述";

    //自主性二级指标
    //技术自主
    String technology_independence = "技术自主";
    String technology_independence_desc = "技术自主描述";
    //产权自主
    String property_independence = "产权自主";
    String property_independence_desc = "产权自主描述";
    //组织管理
    String organization_management = "组织管理";
    String organization_management_desc = "组织管理描述";
    //持续发展
    String continuous_development = "持续发展";
    String continuous_development_desc = "持续发展描述";
}
