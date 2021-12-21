package com.tjm.pojo.quality;

import lombok.Data;

import java.util.ArrayList;

/**
 * PSTMQModel为存储模型相关信息的类（模型信息包括模型所属的各级指标、指标的计算公式），希望通过类似下方的方法获取模型信息
 * public PSTMQModel getModel(String modelName)
 * modelName为模型的名称（ISO、McCall、公安等），PSTMQModel为该方法返回结果。该方法基于输入的模型名称，生成返回该模型的PSTMQModel
 *
 * PSTMQModel类可通过空构造函数创建，在创建后通过insertFirstIndicator（）方法插入该模型的一级指标，insertSecondAndThirdIndicator（）方法插入二级指标,通过insertSecondIndicator（）方法插入三级指标
 * 知识库部分主要使用的就是insertFirstIndicator（）方法和insertSecondAndThirdIndicator（）方法
 */
@Data
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
     *
     * @param indicatorName         新一级指标名称
     * @param indicatorDescription  新一级指标说明
     * @param indicatorResult       指标结果
     * @param indicatorWeight       指标权重
     */
    public void insertFirstIndicator(String indicatorName, String indicatorDescription, double indicatorResult, double indicatorWeight) {
        firstIndicator.add(new Indicator(indicatorName, indicatorDescription,
                new ArrayList<>(),indicatorResult, indicatorWeight));
    }
    /*public void insertFirstIndicator(String indicatorName, String indicatorDescription, double indicatorResult, double indicatorWeight) {
        firstIndicator.add(new Indicator(indicatorName, indicatorDescription, indicatorResult, indicatorWeight));
    }*/



    /**
     * 插入一级指标
     *
     * @param indicatorId          新一级指标ID
     * @param indicatorName        新一级指标名称
     * @param indicatorDescription 新一级指标说明
     */
    public void insertFirstIndicator(int indicatorId, String indicatorName, String indicatorDescription) {
        firstIndicator.add(new Indicator(indicatorId, indicatorName, indicatorDescription));
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
                            superIndicatorName,
                            new ArrayList<Indicator>()), i);
        }
    }


    /**
     * 插入二级指标或三级指标
     * @param superIndicatorName   上一级指标名称
     * @param indicatorName        新指标名称
     * @param indicatorDescription 新指标说明
     * @param indicatorResult       指标结果
     * @param indicatorWeight       指标权重
     */
    public void insertSecondAndThirdIndicator(String superIndicatorName, String indicatorName, String indicatorDescription
            , double indicatorResult, double indicatorWeight) {
        for (Indicator i : firstIndicator) {
            insertIndicator(superIndicatorName,
                    new Indicator(indicatorName,
                            indicatorDescription,
                            superIndicatorName, new ArrayList<>(),
                            indicatorResult, indicatorWeight), i);
        }
    }

    /**
     * 插入二级指标或三级指标
     *
     * @param superIndicatorName   上一级指标名称
     * @param indicatorName        新指标名称
     * @param indicatorDescription 新指标说明
     */
    public void insertSecondAndThirdIndicator(String superIndicatorName, int indicatorId, String indicatorName,
                                              String indicatorDescription, String molecular, String denominator) {
        for (Indicator i : firstIndicator) {
            insertIndicator(superIndicatorName,
                    new Indicator(indicatorId,
                            indicatorName,
                            indicatorDescription,
                            new ArrayList<Indicator>(),
                            molecular,
                            denominator), i);
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

    /**
     * 插入三级指标
     *
     * @param superIndicatorName   上一级指标名称
     * @param indicatorName        新指标名称
     * @param indicatorDescription 新指标说明
     * @param molecular            三级指标计算公式分子
     * @param denominator          三级指标计算公式分母
     */
    public void insertThirdIndicator(String superIndicatorName, String indicatorName, String indicatorDescription, String molecular,String denominator) {
        Indicator indicator= new Indicator(indicatorName, indicatorDescription, superIndicatorName);
        indicator.setMolecular(molecular);
        indicator.setDenominator(denominator);
        for (Indicator i : firstIndicator) {
            insertIndicator(superIndicatorName, indicator, i);
        }
    }

    /**
     * 插入三级指标
     *
     * @param superIndicatorName   上一级指标名称
     * @param indicatorName        新指标名称
     * @param indicatorDescription 新指标说明
     * @param molecular            三级指标计算公式分子
     * @param denominator          三级指标计算公式分母
     * @param molecularResult
     * @param denominatorResult
     */
    public void insertThirdIndicator(String superIndicatorName, String indicatorName, String indicatorDescription,
                                     double indicatorResult, double indicatorWeight
            , String molecular,String denominator,double molecularResult, double denominatorResult) {
        Indicator indicator= new Indicator(indicatorName,
                indicatorDescription, superIndicatorName,
                indicatorResult,indicatorWeight);
        indicator.setMolecular(molecular);
        indicator.setDenominator(denominator);
        indicator.setMolecularResult(molecularResult);
        indicator.setDenominatorResult(denominatorResult);
        for (Indicator i : firstIndicator) {
            insertIndicator(superIndicatorName, indicator, i);
        }
    }

    /**
     * 插入三级指标
     *
     * @param superIndicatorName   上一级指标名称
     * @param indicatorName        新指标名称
     * @param indicatorDescription 新指标说明
     * @param molecular            三级指标计算公式分子
     * @param denominator          三级指标计算公式分母
     * @param molecularResult
     * @param denominatorResult
     * @param calMode
     */
    public void insertThirdIndicator(String superIndicatorName, String indicatorName, String indicatorDescription,
                                     double indicatorResult, double indicatorWeight
            , String molecular,String denominator,double molecularResult, double denominatorResult, String calMode) {
        Indicator indicator= new Indicator(indicatorName,
                indicatorDescription, superIndicatorName,
                indicatorResult,indicatorWeight);
        indicator.setMolecular(molecular);
        indicator.setDenominator(denominator);
        indicator.setMolecularResult(molecularResult);
        indicator.setDenominatorResult(denominatorResult);
        indicator.setCalMode(calMode);
        for (Indicator i : firstIndicator) {
            insertIndicator(superIndicatorName, indicator, i);
        }
    }

    /**
     * 插入三级指标
     *
     * @param superIndicatorName   上一级指标名称
     * @param indicatorName        新指标名称
     * @param indicatorDescription 新指标说明
     */
    public void insertThirdIndicator(String superIndicatorName, String indicatorName, String indicatorDescription
            , double indicatorResult, double indicatorWeight) {
        for (Indicator i : firstIndicator) {
            insertIndicator(superIndicatorName,
                    new Indicator(indicatorName,
                            indicatorDescription,
                            superIndicatorName
                            , indicatorResult, indicatorWeight), i);
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
        model.insertThirdIndicator("标准符合性", "功能实现的完整性", "","检测到遗漏的功能数","标准中描述的功能数");
        model.insertThirdIndicator("标准符合性", "标准的依从性", "","测试中已证实的按标准正确实现的功能","标准中描述的功能数");
        model.insertThirdIndicator("适合性", "软件需求项数密度", "","软件最终定义的软件需求数","在需求规格说明中描述的功能数");
        model.insertThirdIndicator("适合性", "功能实现的覆盖性", "","检测到不能正确实现或遗漏的功能数","在需求规格说明中描述的功能数");
        model.insertThirdIndicator("准确性", "计算的精准性", "","计算出精确结果的操作次数","用户执行计算操作的次数");
        model.insertThirdIndicator("准确性", "精度", "","在测试中已证实的满足特定级别精度要求的数据项数","需要特定级别精度要求的数据项数");
        model.insertThirdIndicator("互操作性", "数据的可交换性", "","按规格说明正确实现接口数据格式数","规格说明中要交换的数据格式数");
        model.insertThirdIndicator("互操作性", "接口的一致性", "","在测试中已验证正确实现的接口协议数","规格说明中要实现的接口协议数");

        model.insertFirstIndicator("可靠性", "在规定的时间和条件下,维持其性能水准的程度");
        model.insertSecondAndThirdIndicator("可靠性", "成熟性", "");
        model.insertSecondAndThirdIndicator("可靠性", "容错性", "");
        model.insertSecondAndThirdIndicator("可靠性", "可恢复性", "");
        model.insertSecondAndThirdIndicator("可靠性", "稳定性", "");
        model.insertThirdIndicator("成熟性", "代码缺陷密度", "","测试过程中检测到的缺陷数","软件规模(千行代码数)");
        model.insertThirdIndicator("成熟性", "平均失效间隔时间", "","总测试时间（实际进行连续长时间测试用例的时间）","测试过程中发现的失效总数");
        model.insertThirdIndicator("成熟性", "软件安全性需求密度", "","软件安全性需求总数\n","软件规模(千行代码数)");
        model.insertThirdIndicator("成熟性", "服务支持", "","服务需求数","响应支持数");
        model.insertThirdIndicator("成熟性", "文档缺陷密度", "","软件测试过程中所检测到的文档缺陷总数","文档规模(文档页数)");
        model.insertThirdIndicator("容错性", "避免失效", "","测试过程中，软件出现严重失效和关键失效的次数","测试过程中发生失效的总数");
        model.insertThirdIndicator("容错性", "操作的容错性", "","导致系统失效(死机、系统部分功能异常)，且没有提示或没有恢复到正常状态的操作个数","用户错误操作的总数");
        model.insertThirdIndicator("容错性", "数据备份延迟", "","数据备份的频率","");
        model.insertThirdIndicator("可恢复性", "重启恢复率", "","重启软件后能恢复其失效功能的重启次数","规定时间内可以重启次数");
        model.insertThirdIndicator("可恢复性", "平均恢复时间", "","对恢复时间进行测量，时间越短越好","");
        model.insertThirdIndicator("稳定性", "24小时无间断运行", "","是否支持24小时无间断运行（1支持；0不支持）","");

        model.insertFirstIndicator("易用性", "");
        model.insertSecondAndThirdIndicator("易用性", "易操作性", "");
        model.insertSecondAndThirdIndicator("易用性", "易学习性", "");
        model.insertSecondAndThirdIndicator("易用性", "易理解性", "");
        model.insertSecondAndThirdIndicator("易用性", "错误防御", "");
        model.insertSecondAndThirdIndicator("易用性", "界面美观", "");
        model.insertThirdIndicator("易操作性", "人机交互界面", "","是否有人机交互界面，1有；0没有","");
        model.insertThirdIndicator("易操作性", "默认的可用性", "","配置界面是否设置了默认值，1有；0没有","");
        model.insertThirdIndicator("易学习性", "易学习性情况", "","易学习性情况（0-100）","");
        model.insertThirdIndicator("易理解性", "功能显见性", "","对用户是显见的功能（或功能类型）数","功能（或功能类型）的总数");
        model.insertThirdIndicator("易理解性", "功能易理解性", "","能被用户理解的界面功能数","用户界面功能总数");
        model.insertThirdIndicator("错误防御", "输入的有效性检查", "","对有效数据进行检查的输入项数","对有效数据可能进行检查的输入项数");
        model.insertThirdIndicator("错误防御", "用户操作的易取消性", "","已实现的能被用户取消的功能数","要求具备预取消能力的功能总数");
        model.insertThirdIndicator("错误防御", "用户操作的易还原性", "","已实现的能被用户还原的功能数","功能总数");
        model.insertThirdIndicator("界面美观", "交互吸引性", "","界面对用户的吸引程度如何","");
        model.insertThirdIndicator("界面美观", "GUI界面的易定制性", "","能被定制的界面元素的类型数","界面元素类型的总数");

        model.insertFirstIndicator("效率", "");
        model.insertSecondAndThirdIndicator("效率", "有效性", "");
        model.insertSecondAndThirdIndicator("效率", "资源利用性", "");
        model.insertSecondAndThirdIndicator("效率", "时间特性", "");
        model.insertSecondAndThirdIndicator("效率", "容量", "");
        model.insertSecondAndThirdIndicator("效率", "流畅度", "");
        model.insertThirdIndicator("有效性", "有效性情况", "","有效性情况","");
        model.insertThirdIndicator("资源利用性", "传输利用率", "","位","时间");
        model.insertThirdIndicator("资源利用性", "内存利用率", "","与内存相关的错误告警消息数","直接与系统调用相关的代码行数");
        model.insertThirdIndicator("资源利用性", "CPU占用率", "","软件运行过程中的CPU占用率\n","");
        model.insertThirdIndicator("资源利用性", "电量消耗", "","估计APP运行一段时间过程中的耗电量多少","");
        model.insertThirdIndicator("资源利用性", "流量消耗", "","估计App在运行过程中的流量使用情况","");
        model.insertThirdIndicator("时间特性", "平均响应时间", "","平均响应时间","");
        model.insertThirdIndicator("时间特性", "吞吐量", "","每单位时间完成的任务个数","");
        model.insertThirdIndicator("时间特性", "周转时间", "","时间（计算或模拟的）","");
        model.insertThirdIndicator("容量", "存储空间", "","存储空间","");
        model.insertThirdIndicator("容量", "崩溃率", "","崩溃率","");
        model.insertThirdIndicator("流畅度", "每秒帧率", "","每秒帧率","");
        model.insertThirdIndicator("流畅度", "平滑度", "","平滑度","");

        model.insertFirstIndicator("可维护性", "");
        model.insertSecondAndThirdIndicator("可维护性", "软件复杂度", "");
        model.insertSecondAndThirdIndicator("可维护性", "容易使用", "");
        model.insertSecondAndThirdIndicator("可维护性", "模块性", "");
        model.insertSecondAndThirdIndicator("可维护性", "易修改性", "");
        model.insertSecondAndThirdIndicator("可维护性", "易测试性", "");
        model.insertThirdIndicator("软件复杂度", "软件功能单元最大扇入扇出数", "","软件功能单元最大扇入扇出数","");
        model.insertThirdIndicator("软件复杂度", "软件功能单元最大有效代码行数", "","软件功能单元最大有效代码行数","");
        model.insertThirdIndicator("容易使用", "软件注释率", "","软件注释行数","软件总行数");
        model.insertThirdIndicator("模块性", "耦合度", "","耦合度","");
        model.insertThirdIndicator("模块性", "内聚度", "","内聚度","");
        model.insertThirdIndicator("易修改性", "修改的复杂性", "","更改用时","更改总次数");
        model.insertThirdIndicator("易修改性", "修改的有效性", "","修改生效的数目","需要修改的数目");
        model.insertThirdIndicator("易测试性", "测试执行率", "","测试完成的用例数","测试用例总数");

        model.insertFirstIndicator("可移植性", "");
        model.insertSecondAndThirdIndicator("可移植性", "适用性", "");
        model.insertSecondAndThirdIndicator("可移植性", "易安装性", "");
        model.insertThirdIndicator("适用性", "数据结构的适用性", "","因新环境而无法正常显示的数据数","希望能在软件所适应的环境中运行的数据总数");
        model.insertThirdIndicator("适用性", "共存性", "","与其他软件共同运行时，产生的失效次数","与其他软件共同运行的次数");
        model.insertThirdIndicator("易安装性", "安装和卸载成功率", "","安装和卸载成功率","");
        model.insertThirdIndicator("易安装性", "硬件适应性", "","经评审证实已实现的能在指定的多种H/W环境中取得所要求结果的功能数","具有H/W环境适应能力要求的功能总数");
        model.insertThirdIndicator("易安装性", "系统适应性", "","经评审证实已实现的能在指定的多种系统软件环境中获取要求结果的功能数","具有软件系统环境适应能力要求的功能总数");

        model.insertFirstIndicator("安全性", "");
        model.insertSecondAndThirdIndicator("安全性", "保密性", "");
        model.insertSecondAndThirdIndicator("安全性", "完整性", "");
        model.insertSecondAndThirdIndicator("安全性", "不可抵赖性", "");
        model.insertSecondAndThirdIndicator("安全性", "漏洞情况", "");
        model.insertSecondAndThirdIndicator("安全性", "真实性", "");
        model.insertThirdIndicator("保密性", "权限使用询问率", "","软件经用户同意后使用的权限数","软件使用的权限总数");
        model.insertThirdIndicator("保密性", "数据加密", "","在评审中已证实的按规定已实现的可加密/解密的数据项实例数","规格说明中要求加密/解密功能的数据项实例数");
        model.insertThirdIndicator("完整性", "访问的可控制率", "","规定时间内检测出的非法操作数量","规定时间内总共的操作数");
        model.insertThirdIndicator("不可抵赖性", "日志记录率", "","日志记录率","");
        model.insertThirdIndicator("漏洞情况", "漏洞的情况", "","漏洞情况","");
        model.insertThirdIndicator("真实性", "真实性情况", "","真实性情况","");

        model.insertFirstIndicator("自主性", "");
        model.insertSecondAndThirdIndicator("自主性", "技术自主", "");
        model.insertSecondAndThirdIndicator("自主性", "产权自主", "");
        model.insertSecondAndThirdIndicator("自主性", "组织管理", "");
        model.insertSecondAndThirdIndicator("自主性", "持续发展", "");
        model.insertThirdIndicator("技术自主", "自主代码率", "","软件自主代码数","软件总代码数");
        model.insertThirdIndicator("产权自主", "软件著作权", "","有无软件著作权","");
        model.insertThirdIndicator("组织管理", "自主可控率", "","软件运行、维护、管理的自主可控程度","");
        model.insertThirdIndicator("持续发展", "预留接口数", "","有接口的功能数","软件实现的功能数");
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
                    System.out.println("        " + level3.getIndicatorName() + "   " + level3.getSuperIndicatorName() + "   " + level3.getMolecular() + "   " + level3.getDenominator());
                }
            }
        }

    }

}


