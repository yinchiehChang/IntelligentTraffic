package com.tjm.pojo.quality;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Deprecated
public class QualityModel implements Serializable {
    //一级指标
    //功能性
    boolean feature;
    //可靠性
    boolean reliable;
    //易用性
    boolean usable;
    //效率
    boolean efficiency;
    //可维护性
    boolean maintainability;
    //可移植性
    boolean portability;
    //安全性
    boolean security;
    //自主性
    boolean independence;

    //功能性二级指标
    //标准符合性
    boolean standard_compliance;
    //适合性
    boolean suitability;
    //准确性
    boolean accuracy;
    //互操作性
    boolean interoperability;

    //可靠性二级指标
    //成熟性
    boolean maturity;
    //容错性
    boolean fault_tolerance;
    //可恢复性
    boolean recoverability;
    //稳定性
    boolean stability;

    //易用性二级指标
    //易操作性
    boolean easy_operation;
    //易学习性
    boolean easy_learning;
    //易理解性
    boolean understandability;
    //错误防御
    boolean fault_defence;
    //界面美观
    boolean good_ui;

    //效率二级指标
    //有效性
    boolean effectiveness;
    //资源利用性
    boolean resource_use;
    //时间特性
    boolean time_feature;
    //容量
    boolean capacity;
    //流畅度
    boolean fluency;

    //可维护性二级指标
    //软件复杂度
    boolean software_complexity;
    //易用性
    boolean easy_use;
    //模块性
    boolean modularity;
    //易修改性
    boolean easy_revise;
    //易测试性
    boolean easy_test;

    //可移植性二级指标
    //适用性
    boolean applicability;
    //易安装性
    boolean easy_install;

    //安全性二级指标
    //保密性
    boolean confidentiality;
    //完整性
    boolean completeness;
    //不可抵赖性
    boolean non_repudiation;
    //漏洞情况
    boolean vulnerability_situation;
    //真实性
    boolean authenticity;

    //自主性二级指标
    //技术自主
    boolean technology_independence;
    //产权自主
    boolean property_independence;
    //组织管理
    boolean organization_management;
    //持续发展
    boolean continuous_development;

}
