package com.tjm.controller.quality;

import com.tjm.pojo.quality.*;
import com.tjm.pojo.testCase.TestCase;
import com.tjm.service.CodeParseInfoService;
import com.tjm.service.ModelConfigService;
import com.tjm.service.QualityService;
import com.tjm.service.TestCaseService;
import com.tjm.utils.AHPUtils;
import com.tjm.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/qualityIndex")
public class QualityIndexController {

    Integer uid;
    int maxL1;
    int maxL2;
    int maxL3;

    @Autowired
    private QualityService qualityService;
    @Autowired
    private ModelConfigService modelConfigService;
    @Autowired
    private CodeParseInfoService codeParseInfoService;
    @Autowired
    private TestCaseService testCaseService;

    @RequestMapping("/Indexes")
    public String list(Model model) {
        List<Quality> qualities = qualityService.findQualityByStatus(4);
        qualities.addAll(qualityService.findQualityByStatus(5));
        qualities.addAll(qualityService.findQualityByStatus(6));
        qualities.addAll(qualityService.findQualityByStatus(7));
        model.addAttribute("qualities", qualities);
        return "qualityEvaluation/qualityIndexPage";
    }

    //指标计算界面
    @RequestMapping("/IndexesCalculation/{qualityUid}")
    public String indexCal(@PathVariable("qualityUid") int uid, Model model) {
        this.uid = uid;
        model.addAttribute("uid", uid);

        PSTMQModel pstmqModel = modelForIndexCal(uid);
        model.addAttribute("QualityModel", pstmqModel);
//        System.out.println(maxL1 + " " + maxL2 + " " + maxL3);
        model.addAttribute("maxL1", maxL1);
        model.addAttribute("maxL2", maxL2);
        model.addAttribute("maxL3", maxL3);


        return "qualityEvaluation/qualityIndexCalculation";
    }

    //计算指标
    @RequestMapping("/calculateIndex")
    @ResponseBody
    public Map<String, Object> calculateIndex(Indicator indicator) {
        System.out.println(indicator);

        double v = 0.0;
        Map<String, Object> map = new HashMap<>();
        double denominatorResult = indicator.getDenominatorResult();
        if (denominatorResult == 0.0) {
            indicator.setDenominatorResult(100);
        }

        switch (indicator.getCalMode()) {
            case "模式一": {
                double measurementValue = indicator.getMolecularResult() / indicator.getDenominatorResult();
                v = (measurementValue) * 100;
                break;
            }
            case "模式二": {
                double measurementValue = indicator.getMolecularResult() / indicator.getDenominatorResult();
                v = (1.0 - measurementValue) * 100;
                break;
            }
            case "模式三": {
                double measurementValue = indicator.getMolecularResult() / indicator.getDenominatorResult();
                v = (1.0 / measurementValue) * 100;
                break;
            }
            case "模式四": {
                double measurementValue = indicator.getMolecularResult() / indicator.getDenominatorResult();
                v = (1.0 - (1.0 / measurementValue)) * 100;
                break;
            }
            case "模式五": {
                double measurementValue = indicator.getMolecularResult() / indicator.getDenominatorResult();
                v = (1.0 / (1.0 + measurementValue)) * 100;
                break;
            }
            case "模式六": {
                double measurementValue = indicator.getMolecularResult() / indicator.getDenominatorResult();
                v = (1.0 - (1.0 / (1.0 + measurementValue))) * 100;
                break;
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        double parseValue = Double.parseDouble(df.format(v));

        map.put("v", parseValue);
        modelConfigService.updateResult(indicator.getIndicatorId(), indicator.getIndicatorName(),
                indicator.getMolecularResult(), indicator.getDenominatorResult(),
                parseValue, indicator.getCalMode());

        return map;
    }

    //完成指标计算
    @RequestMapping("/finishCalIndex")
    public String finishCalIndex() {
        Quality quality = qualityService.findQualityById(uid);
        if (quality.getStatus() == 4) {
            quality.setStatus(5);
        } else if (quality.getStatus() == 5) {
            quality.setStatus(6);
        }

        qualityService.updatesStatus(quality);

        return "redirect:/qualityIndex/Indexes";
    }


    //权重设置界面
    @RequestMapping("/WeightSetting/{qualityUid}")
    public String weightSetting(@PathVariable("qualityUid") int uid, Model model) {
        this.uid = uid;
        model.addAttribute("uid", uid);

        PSTMQModel pstmqModel = getModel(uid);
        model.addAttribute("QualityModel", pstmqModel);

        model.addAttribute("maxL1", maxL1);
        model.addAttribute("maxL2", maxL2);


        return "qualityEvaluation/indexWeightSet";
    }

    //设置权重
    @RequestMapping("/setWeight")
    @ResponseBody
    public Map<String, Object> setWeight(Weight weight) {
        Map<String, Object> map = new HashMap<>();

        System.out.println(uid);
        String[] name = weight.getName();
        System.out.println(Arrays.toString(name));

        Double[] weights = weight.getWeight();
        double[][] matrix = to2DArray(weights);
        for (double[] line : matrix) {
            System.out.println(Arrays.toString(line));
        }

        double[] result = AHPUtils.matrixCompute(matrix, matrix.length);
        System.out.println(Arrays.toString(result));

        for (int i = 0; i < name.length; i++) {
            modelConfigService.updateWeight(uid, name[i], result[i]);
        }

        map.put("weight", result);
        return map;

    }

    //设置三级指标权重
    @RequestMapping("/setWeightLevel3")
    @ResponseBody
    public Map<String, Object> setWeightLevel3(Weight weight) {
        Map<String, Object> map = new HashMap<>();

        System.out.println(uid);
        String[] name = weight.getName();
        System.out.println(Arrays.toString(name));

        Double[] weights = weight.getWeight();
        System.out.println(Arrays.toString(weights));

        for (int i = 0; i < name.length; i++) {
            modelConfigService.updateWeight(uid, name[i], weights[i]);
        }

        map.put("weight", weights);
        return map;
    }

    //完成权重配置
    @RequestMapping("/finishSetWeight")
    public String finishSetWeight() {

        Quality quality = qualityService.findQualityById(uid);
        if (quality.getStatus() == 4) {
            quality.setStatus(5);
        } else if (quality.getStatus() == 5) {
            quality.setStatus(6);
        }

        qualityService.updatesStatus(quality);

        return "redirect:/qualityIndex/Indexes";
    }

    //转为二维数组
    private double[][] to2DArray(Double[] weights) {
        int length = weights.length;
        int d = (int) Math.sqrt(length);
        double[][] matrix = new double[d][d];
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                matrix[i][j] = weights[i * d + j];
            }
        }

        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (i < j) {
                    matrix[i][j] = 1.0 / matrix[j][i];
                }
            }
        }

        return matrix;
    }

    //综合结果计算界面
    @RequestMapping("/ResultCal/{qualityUid}")
    public String ResultCal(@PathVariable("qualityUid") int uid, Model model) {
        this.uid = uid;
        model.addAttribute("uid", uid);

        calLevel2Result(uid);
        calLevel1Result(uid);
        double finalResult = calFinalResult(uid);
        qualityService.updateFinalScore(uid, finalResult);

        model.addAttribute("finalResult", finalResult);

        PSTMQModel pstmqModel = getModel(uid);
        model.addAttribute("QualityModel", pstmqModel);

        Quality quality = qualityService.findQualityById(uid);
        quality.setStatus(7);
        qualityService.updatesStatus(quality);

        return "qualityEvaluation/indexResultCalculation";
    }

    //计算二级指标得分结果
    private void calLevel2Result(int uid) {
        List<ModelConfig> level1Indicator = modelConfigService.findConfiguredModelByIdAndSuper(uid,
                "null");
        for (ModelConfig l1indicator : level1Indicator) {
            String l1indicator_name = l1indicator.getIndicator_name();
            List<ModelConfig> level2Indicator = modelConfigService.findConfiguredModelByIdAndSuper(uid,
                    l1indicator_name);
            for (ModelConfig l2indicator : level2Indicator) {
                double l2Result = 0.0;
                String l2indicator_name = l2indicator.getIndicator_name();
                List<ModelConfig> level3Indicator = modelConfigService.findConfiguredModelByIdAndSuper(uid,
                        l2indicator_name);
                for (ModelConfig l3indicator : level3Indicator) {
                    double index_result = l3indicator.getIndex_result();
                    double index_weight = l3indicator.getIndex_weight();
                    l2Result += index_result * index_weight;
                }
                DecimalFormat df = new DecimalFormat("0.00");
                modelConfigService.updateIndexResult(uid, l2indicator_name,
                        Double.parseDouble(df.format(l2Result)));
            }
        }
    }

    //计算一级指标得分结果
    private void calLevel1Result(int uid) {
        List<ModelConfig> level1Indicator = modelConfigService.findConfiguredModelByIdAndSuper(uid,
                "null");
        for (ModelConfig l1indicator : level1Indicator) {
            double l1Result = 0.0;
            String l1indicator_name = l1indicator.getIndicator_name();
            List<ModelConfig> level2Indicator = modelConfigService.findConfiguredModelByIdAndSuper(uid,
                    l1indicator_name);
            for (ModelConfig l2indicator : level2Indicator) {
                double index_result = l2indicator.getIndex_result();
                double index_weight = l2indicator.getIndex_weight();
                l1Result += index_result * index_weight;
                DecimalFormat df = new DecimalFormat("0.00");
                modelConfigService.updateIndexResult(uid, l1indicator_name,
                        Double.parseDouble(df.format(l1Result)));
            }
        }
    }

    //计算最终得分结果
    private double calFinalResult(int uid) {
        List<ModelConfig> level1Indicator = modelConfigService.findConfiguredModelByIdAndSuper(uid,
                "null");
        double finalResult = 0.0;
        for (ModelConfig l1indicator : level1Indicator) {
            double index_result = l1indicator.getIndex_result();
            double index_weight = l1indicator.getIndex_weight();
            finalResult += index_result * index_weight;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.parseDouble(df.format(finalResult));
    }

    //封装PSTMQModel
    private PSTMQModel getModel(int uid) {
        PSTMQModel pstmqModel = new PSTMQModel();

        maxL1 = 0;
        maxL2 = 0;
        maxL3 = 0;

        List<ModelConfig> level1Indicator = modelConfigService.findConfiguredModelByIdAndSuper(uid,
                "null");
        maxL1 = level1Indicator.size();

        for (ModelConfig l1indicator : level1Indicator) {
//            System.out.println(l1indicator);
            String l1indicator_name = l1indicator.getIndicator_name();
            double index_result = l1indicator.getIndex_result();
            double index_weight = l1indicator.getIndex_weight();
//            System.out.println(l1indicator_name);
            pstmqModel.insertFirstIndicator(l1indicator_name, "",
                    index_result, index_weight);
            List<ModelConfig> level2Indicator = modelConfigService.findConfiguredModelByIdAndSuper(uid,
                    l1indicator_name);

            if (level2Indicator.size() >= maxL2) {
                maxL2 = level2Indicator.size();
            }

            for (ModelConfig l2indicator : level2Indicator) {
                String l2indicator_name = l2indicator.getIndicator_name();
                double l2index_result = l2indicator.getIndex_result();
                double l2index_weight = l2indicator.getIndex_weight();
//                System.out.println(l2indicator_name);
                pstmqModel.insertSecondAndThirdIndicator(l1indicator_name,
                        l2indicator_name, "",
                        l2index_result, l2index_weight);
                List<ModelConfig> level3Indicator = modelConfigService.findConfiguredModelByIdAndSuper(uid,
                        l2indicator_name);

                for (ModelConfig l3indicator : level3Indicator) {
                    String l3indicator_name = l3indicator.getIndicator_name();
                    double l3index_result = l3indicator.getIndex_result();
                    double l3index_weight = l3indicator.getIndex_weight();
                    String molecular = l3indicator.getMolecular();
                    String denominator = l3indicator.getDenominator();
                    double molecular_result = l3indicator.getMolecular_result();
                    double denominator_result = l3indicator.getDenominator_result();

                    String cal_mode = l3indicator.getCal_mode();
//                    System.out.println(l3indicator_name);
                    pstmqModel.insertThirdIndicator(l2indicator_name,
                            l3indicator_name, "",
                            l3index_result, l3index_weight,
                            molecular, denominator,
                            molecular_result, denominator_result, cal_mode);

                }
            }
        }
        return pstmqModel;
    }

    //封装PSTMQModel
    private PSTMQModel modelForIndexCal(int uid) {
        PSTMQModel pstmqModel = new PSTMQModel();

        maxL1 = 0;
        maxL2 = 0;
        maxL3 = 0;

        List<ModelConfig> level1Indicator = modelConfigService.findConfiguredModelByIdAndSuper(uid,
                "null");
        CodeParseInfo info = codeParseInfoService.findInfoById(uid).get(0);
        maxL1 = level1Indicator.size();

        for (ModelConfig l1indicator : level1Indicator) {
//            System.out.println(l1indicator);
            String l1indicator_name = l1indicator.getIndicator_name();
            double index_result = l1indicator.getIndex_result();
            double index_weight = l1indicator.getIndex_weight();
//            System.out.println(l1indicator_name);
            pstmqModel.insertFirstIndicator(l1indicator_name, "",
                    index_result, index_weight);
            List<ModelConfig> level2Indicator = modelConfigService.findConfiguredModelByIdAndSuper(uid,
                    l1indicator_name);
//            System.out.println(l1indicator_name + " " + level2Indicator.size());
            if (level2Indicator.size() >= maxL2) {
                maxL2 = level2Indicator.size();
            }

            for (ModelConfig l2indicator : level2Indicator) {
                String l2indicator_name = l2indicator.getIndicator_name();
                double l2index_result = l2indicator.getIndex_result();
                double l2index_weight = l2indicator.getIndex_weight();
//                System.out.println(l2indicator_name);
                pstmqModel.insertSecondAndThirdIndicator(l1indicator_name,
                        l2indicator_name, "",
                        l2index_result, l2index_weight);
                List<ModelConfig> level3Indicator = modelConfigService.findConfiguredModelByIdAndSuper(uid,
                        l2indicator_name);
//                System.out.println(l2indicator_name + " " + level3Indicator.size());
                if (level3Indicator.size() >= maxL3) {
                    maxL3 = level3Indicator.size();
//                    System.out.println(maxL3);
                }

                for (ModelConfig l3indicator : level3Indicator) {
                    String l3indicator_name = l3indicator.getIndicator_name();
                    double l3index_result = l3indicator.getIndex_result();
                    double l3index_weight = l3indicator.getIndex_weight();
                    String molecular = l3indicator.getMolecular();
                    String denominator = l3indicator.getDenominator();
                    double molecular_result = l3indicator.getMolecular_result();
                    molecular_result = setParseInfoToResult(info, molecular, molecular_result);
                    molecular_result = setParseInfoToResult(info, l3indicator_name, molecular_result);
                    double denominator_result = l3indicator.getDenominator_result();
                    denominator_result = setParseInfoToResult(info, denominator, denominator_result);

                    //测试用例执行
                    List<TestCase> testCaseList = testCaseService.findAllCases(uid);
                    int totalTest = testCaseList.size();
                    int passTest = 0;
                    int wrongTest = 0;
                    int calTest = 0;
                    int calPassTest = 0;
                    int accuCalTest = 0;
                    int accuCalPassTest = 0;
                    int completeTest = 0;
                    for (TestCase testCase : testCaseList) {
                        if (testCase.getCase_result() == 3) {
                            wrongTest++;
                        } else if (testCase.getCase_result() == 4) {
                            passTest++;
                        } else if (testCase.getCase_id().contains("JS")) {
                            calTest++;
                            if (testCase.getCase_result() == 4) {
                                calPassTest++;
                            }
                        } else if (testCase.getCase_id().contains("JD")) {
                            accuCalTest++;
                            if (testCase.getCase_result() == 4) {
                                accuCalPassTest++;
                            }
                        } else if (testCase.getCase_status() != 0) {
                            completeTest++;
                        }
                    }

                    double[] testCaseResult = setTestCaseToResult(totalTest, passTest, calTest, calPassTest, accuCalTest,
                            accuCalPassTest, l3indicator_name, molecular_result,
                            denominator_result);
                    molecular_result = testCaseResult[0];
                    denominator_result = testCaseResult[1];

                    String cal_mode = l3indicator.getCal_mode();
//                    System.out.println(l3indicator_name);
                    pstmqModel.insertThirdIndicator(l2indicator_name,
                            l3indicator_name, "",
                            l3index_result, l3index_weight,
                            molecular, denominator,
                            molecular_result, denominator_result, cal_mode);

                }
            }
        }
        return pstmqModel;
    }

    //代码信息解析
    private double setParseInfoToResult(CodeParseInfo info, String molDe, double molDe_result) {
        if (molDe != null && !(molDe_result > 0)) {
            if (molDe.contains("千行代码") || molDe.contains("总行数") || molDe.contains("所有代码行数")) {
                molDe_result = info.getCodeLine();
            } else if (molDe.contains("注释行") || molDe.contains("注释率")) {
                molDe_result = info.getCommentLine();
            } else if (molDe.contains("有效代码")) {
                molDe_result = info.getValidCodeLine();
            } else if (molDe.contains("扇入扇出")) {
                molDe_result = Math.max(info.getMaxFanIn(), info.getMaxFanOut());
            }
        }
        return molDe_result;
    }

    //测试用例执行
    private double[] setTestCaseToResult(int totalTest, int passTest, int calTest, int calPassTest, int accuCalTest,
                                         int accuCalPassTest, String molDe, double mol_result, double de_result) {

        if (molDe != null && (!(mol_result > 0) || !(de_result > 0))) {
            if (molDe.contains("标准的依从性")) {
                mol_result = passTest;
                de_result = totalTest;
            } else if (molDe.contains("功能实现的覆盖率")) {
                mol_result = totalTest - passTest;
                de_result = totalTest;
            } else if (molDe.contains("计算的准确性")) {
                mol_result = calPassTest;
                de_result = calTest;
            } else if (molDe.contains("数据精度的满足性")) {
                mol_result = accuCalPassTest;
                de_result = accuCalTest;
            }
        }

        double[] result = {mol_result, de_result};
        return result;
    }

    //查询
    @RequestMapping("/queryAll")
    public String queryAll(String queryId, String queryName, String queryUnit, Model model) {
        /*System.out.println(queryId);
        System.out.println(queryName);
        System.out.println(queryUnit);*/
        List<Quality> qualityByQid;
        List<Quality> qualityByName;
        List<Quality> qualityByUnit;
        List<Quality> lists;

//        if (queryId.isEmpty() && queryName.isEmpty() && queryUnit.isEmpty()) {
        if (StrUtils.isEmpty(queryId) && StrUtils.isEmpty(queryName) && StrUtils.isEmpty(queryUnit)) {
//            return "redirect:/quality/AllQualities";
            lists = qualityService.queryQualityList();
            model.addAttribute("error", "输入为空！");
            model.addAttribute("qualities", lists);
            return "qualityEvaluation/qualityModelConf";
        }

        if (!StrUtils.isEmpty(queryId)) {
            qualityByQid = qualityService.findQualityByQid(queryId);
        } else {
            qualityByQid = qualityService.queryQualityList();
        }
        if (!StrUtils.isEmpty(queryName)) {
            qualityByName = qualityService.findQualityByName(queryName);
        } else {
            qualityByName = qualityService.queryQualityList();
        }
        if (!StrUtils.isEmpty(queryUnit)) {
            qualityByUnit = qualityService.findQualityByUnit(queryUnit);
        } else {
            qualityByUnit = qualityService.queryQualityList();
        }

        lists = qualityByQid;
        lists.retainAll(qualityByName);
        lists.retainAll(qualityByUnit);
        if (lists.isEmpty()) {
            lists = qualityService.queryQualityList();
            model.addAttribute("error", "没有符合条件的对象！");
        }
        model.addAttribute("qualities", lists);
        return "qualityEvaluation/qualityIndexPage";
    }

}
