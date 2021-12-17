package com.tjm.pojo.quality;

import java.util.ArrayList;

public class PSTMQModel {

    ArrayList<Indicator> firstIndicator;

    public PSTMQModel(ArrayList<Indicator> firstIndicator) {
        this.firstIndicator = firstIndicator;
    }

    public PSTMQModel() {
        this.firstIndicator = new ArrayList<>();
    }

    public ArrayList<Indicator> getFirstIndicator() {
        return firstIndicator;
    }

    /**
     * 插入一级指标
     *
     * @param indicatorName        新一级指标名称
     * @param indicatorDescription 新一级指标说明
     */
    public void insertFirstIndicator(String indicatorName, String indicatorDescription) {
        firstIndicator.add(new Indicator(indicatorName, indicatorDescription));
    }

    /**
     * 插入二级指标或三级指标
     *
     * @param superIndicatorName   上一级指标名称
     * @param indicatorName        新指标名称
     * @param indicatorDescription 新指标说明
     */
    public void insertSecondAndThirdIndicator(String superIndicatorName, String indicatorName, String indicatorDescription) {
        for (Indicator i : firstIndicator) {
            insertIndicator(superIndicatorName,
                    new Indicator(indicatorName,
                            indicatorDescription,
                            superIndicatorName, new ArrayList<Indicator>()), i);
        }
    }

    /**
     * 插入三级指标
     *
     * @param superIndicatorName   上一级指标名称
     * @param indicatorName        新指标名称
     * @param indicatorDescription 新指标说明
     */
    public void insertThirdIndicator(String superIndicatorName, String indicatorName, String indicatorDescription) {
        for (Indicator i : firstIndicator) {
            insertIndicator(superIndicatorName,
                    new Indicator(indicatorName,
                            indicatorDescription,
                            superIndicatorName), i);
        }
    }

    public void insertIndicator(String superIndicatorName, Indicator newIndicator, Indicator indicator) {
        if (indicator.indicatorName.equals(superIndicatorName)) {
            indicator.subIndicator.add(newIndicator);
        }
        if (indicator.subIndicator == null) {
            return;
        } else {
            for (Indicator i : indicator.subIndicator) {
                insertIndicator(superIndicatorName, newIndicator, i);
            }
        }

    }

    public void show() {
        for (Indicator indicator : firstIndicator) {
            showIndicator(indicator, 0);
        }
    }

    public void showIndicator(Indicator indicator, int index) {
        for (int i = 0; i < index; i++) {
            System.out.print("  ");
        }
        System.out.print(indicator.indicatorName);
        System.out.println();
        if (indicator.subIndicator == null)
            return;
        for (Indicator i : indicator.subIndicator) {
            showIndicator(i, index + 1);
        }
    }

    public static PSTMQModel P_S_T_M_S_Q_M() {
        PSTMQModel model = new PSTMQModel();
        model.insertFirstIndicator("功能性", "实现的功能达到设计规范并满足用户需求的程度");
        model.insertSecondAndThirdIndicator("功能性", "标准符合性", "");
        model.insertSecondAndThirdIndicator("功能性", "适合性", "");
        model.insertSecondAndThirdIndicator("功能性", "准确性", "");
        model.insertSecondAndThirdIndicator("功能性", "互操作性", "");
        model.insertThirdIndicator("标准符合性", "功能实现的完整性", "");
        model.insertThirdIndicator("标准符合性", "标准的依从性", "");
        model.insertThirdIndicator("适合性", "软件需求项数密度", "");
        model.insertThirdIndicator("适合性", "功能实现的覆盖性", "");
        model.insertThirdIndicator("准确性", "计算的精准性", "");
        model.insertThirdIndicator("准确性", "精度", "");
        model.insertThirdIndicator("互操作性", "数据的可交换性", "");
        model.insertThirdIndicator("互操作性", "接口的一致性", "");

        model.insertFirstIndicator("可靠性", "在规定的时间和条件下,维持其性能水准的程度");
        model.insertSecondAndThirdIndicator("可靠性", "成熟性", "");
        model.insertSecondAndThirdIndicator("可靠性", "容错性", "");
        model.insertSecondAndThirdIndicator("可靠性", "可恢复性", "");
        model.insertSecondAndThirdIndicator("可靠性", "稳定性", "");
        model.insertThirdIndicator("成熟性", "代码缺陷密度", "");
        model.insertThirdIndicator("成熟性", "平均失效间隔时间", "");
        model.insertThirdIndicator("成熟性", "软件安全性需求密度", "");
        model.insertThirdIndicator("成熟性", "服务支持", "");
        model.insertThirdIndicator("成熟性", "文档缺陷密度", "");
        model.insertThirdIndicator("容错性", "避免失效", "");
        model.insertThirdIndicator("容错性", "操作的容错性", "");
        model.insertThirdIndicator("容错性", "数据备份延迟", "");
        model.insertThirdIndicator("可恢复性", "重启恢复率", "");
        model.insertThirdIndicator("可恢复性", "平均恢复时间", "");
        model.insertThirdIndicator("稳定性", "24小时无间断运行", "");

        model.insertFirstIndicator("易用性", "");
        model.insertSecondAndThirdIndicator("易用性", "易操作性", "");
        model.insertSecondAndThirdIndicator("易用性", "易学习性", "");
        model.insertSecondAndThirdIndicator("易用性", "易理解性", "");
        model.insertSecondAndThirdIndicator("易用性", "错误防御", "");
        model.insertSecondAndThirdIndicator("易用性", "界面美观", "");
        model.insertThirdIndicator("易操作性", "人机交互界面", "");
        model.insertThirdIndicator("易操作性", "默认的可用性", "");
        model.insertThirdIndicator("易学习性", "易学习性情况", "");
        model.insertThirdIndicator("易理解性", "功能显见性", "");
        model.insertThirdIndicator("易理解性", "功能易理解性", "");
        model.insertThirdIndicator("错误防御", "输入的有效性检查", "");
        model.insertThirdIndicator("错误防御", "用户操作的易取消性", "");
        model.insertThirdIndicator("错误防御", "用户操作的易还原性", "");
        model.insertThirdIndicator("界面美观", "交互吸引性", "");
        model.insertThirdIndicator("界面美观", "GUI界面的易定制性", "");

        model.insertFirstIndicator("效率", "");
        model.insertSecondAndThirdIndicator("效率", "有效性", "");
        model.insertSecondAndThirdIndicator("效率", "资源利用性", "");
        model.insertSecondAndThirdIndicator("效率", "时间特性", "");
        model.insertSecondAndThirdIndicator("效率", "容量", "");
        model.insertSecondAndThirdIndicator("效率", "流畅度", "");
        model.insertThirdIndicator("有效性", "有效性情况", "");
        model.insertThirdIndicator("资源利用性", "传输利用率", "");
        model.insertThirdIndicator("资源利用性", "内存利用率", "");
        model.insertThirdIndicator("资源利用性", "CPU占用率", "");
        model.insertThirdIndicator("资源利用性", "电量消耗", "");
        model.insertThirdIndicator("资源利用性", "流量消耗", "");
        model.insertThirdIndicator("时间特性", "平均响应时间", "");
        model.insertThirdIndicator("时间特性", "吞吐量", "");
        model.insertThirdIndicator("时间特性", "周转时间", "");
        model.insertThirdIndicator("容量", "存储空间", "");
        model.insertThirdIndicator("容量", "崩溃率", "");
        model.insertThirdIndicator("流畅度", "每秒帧率", "");
        model.insertThirdIndicator("流畅度", "平滑度", "");

        model.insertFirstIndicator("可维护性", "");
        model.insertSecondAndThirdIndicator("可维护性", "软件复杂度", "");
        model.insertSecondAndThirdIndicator("可维护性", "容易使用", "");
        model.insertSecondAndThirdIndicator("可维护性", "模块性", "");
        model.insertSecondAndThirdIndicator("可维护性", "易修改性", "");
        model.insertSecondAndThirdIndicator("可维护性", "易测试性", "");
        model.insertThirdIndicator("软件复杂度", "软件功能单元最大扇入扇出数", "");
        model.insertThirdIndicator("软件复杂度", "软件功能单元最大有效代码行数", "");
        model.insertThirdIndicator("容易使用", "软件注释率", "");
        model.insertThirdIndicator("模块性", "耦合度", "");
        model.insertThirdIndicator("模块性", "内聚度", "");
        model.insertThirdIndicator("易修改性", "修改的复杂性", "");
        model.insertThirdIndicator("易修改性", "修改的有效性", "");
        model.insertThirdIndicator("易测试性", "测试执行率", "");

        model.insertFirstIndicator("可移植性", "");
        model.insertSecondAndThirdIndicator("可移植性", "适用性", "");
        model.insertSecondAndThirdIndicator("可移植性", "易安装性", "");
        model.insertThirdIndicator("适用性", "数据结构的适用性", "");
        model.insertThirdIndicator("适用性", "共存性", "");
        model.insertThirdIndicator("易安装性", "安装和卸载成功率", "");
        model.insertThirdIndicator("易安装性", "硬件适应性", "");
        model.insertThirdIndicator("易安装性", "系统适应性", "");

        model.insertFirstIndicator("安全性", "");
        model.insertSecondAndThirdIndicator("安全性", "保密性", "");
        model.insertSecondAndThirdIndicator("安全性", "完整性", "");
        model.insertSecondAndThirdIndicator("安全性", "不可抵赖性", "");
        model.insertSecondAndThirdIndicator("安全性", "漏洞情况", "");
        model.insertSecondAndThirdIndicator("安全性", "真实性", "");
        model.insertThirdIndicator("保密性", "权限使用询问率", "");
        model.insertThirdIndicator("保密性", "数据加密", "");
        model.insertThirdIndicator("完整性", "访问的可控制率", "");
        model.insertThirdIndicator("不可抵赖性", "日志记录率", "");
        model.insertThirdIndicator("漏洞情况", "漏洞的情况", "");
        model.insertThirdIndicator("真实性", "真实性情况", "");

        model.insertFirstIndicator("自主性", "");
        model.insertSecondAndThirdIndicator("自主性", "技术自主", "");
        model.insertSecondAndThirdIndicator("自主性", "产权自主", "");
        model.insertSecondAndThirdIndicator("自主性", "组织管理", "");
        model.insertSecondAndThirdIndicator("自主性", "持续发展", "");
        model.insertThirdIndicator("技术自主", "自主代码率", "");
        model.insertThirdIndicator("产权自主", "软件著作权", "");
        model.insertThirdIndicator("组织管理", "自主可控率", "");
        model.insertThirdIndicator("持续发展", "预留接口数", "");
        return model;
    }

    public static void main(String[] args) {
        /*PSTMQModel model = new PSTMQModel();
        model.insertFirstIndicator("功能性", "1231313");
        model.insertSecondAndThirdIndicator("功能性", "标准符合性", "4456");
        model.insertSecondAndThirdIndicator("功能性", "适合性", "4456");
        model.insertSecondAndThirdIndicator("标准符合性", "功能实现的完整性", "4456");
        model.insertFirstIndicator("可靠性", "");
        model.show();*/

        PSTMQModel model = PSTMQModel.P_S_T_M_S_Q_M();
//        model.show();
        ArrayList<Indicator> firstIndicator = model.getFirstIndicator();
        for (Indicator level1 : firstIndicator) {
            System.out.println(level1.getIndicatorName());
            ArrayList<Indicator> subIndicator = level1.getSubIndicator();
            for (Indicator level2 : subIndicator) {
                System.out.println("    " + level2.getIndicatorName() + "   "  + level2.getSuperIndicatorName());
                ArrayList<Indicator> level2SubIndicator = level2.getSubIndicator();
                for (Indicator level3 : level2SubIndicator) {
                    System.out.println("        " + level3.getIndicatorName() + "   " + level3.getSuperIndicatorName());
                }
            }
        }

    }

}


