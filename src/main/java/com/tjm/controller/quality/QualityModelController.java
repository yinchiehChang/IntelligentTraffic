package com.tjm.controller.quality;

import com.tjm.pojo.base.BaseNode;
import com.tjm.pojo.quality.*;
import com.tjm.service.BaseService;
import com.tjm.service.ModelConfigService;
import com.tjm.service.QualityService;
import com.tjm.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/qualityModel")
public class QualityModelController {

    //    PSTMSModel pstmsModel = new PSTMSModel();
    Integer uid;

    @Autowired
    private QualityService qualityService;

    @Autowired
    private ModelConfigService modelConfigService;

    @Autowired
    private BaseService baseService;

    //查询全部
    @RequestMapping("/AllQualities")
    public String list(Model model) {
        List<Quality> qualities = qualityService.findQualityByStatus(2);
        model.addAttribute("qualities", qualities);
        return "qualityEvaluation/qualityModelConf";
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
        return "qualityEvaluation/qualityModelConf";
    }

    private void getModelToConfig(PSTMQModel model, int uid) {
        System.out.println(model);
        ArrayList<Indicator> firstIndicator = model.getFirstIndicator();
        for (Indicator level1 : firstIndicator) {
//            System.out.println(level1.getIndicatorName());
            ModelConfig l1ModelConfig = new ModelConfig(uid, level1.getIndicatorName(),
                    "null", 0, 0
                    , "", "", 0, 0);
            modelConfigService.insert(l1ModelConfig);
            ArrayList<Indicator> subIndicator = level1.getSubIndicator();
            for (Indicator level2 : subIndicator) {
//                System.out.println("    " + level2.getIndicatorName() + "   "  + level2.getSuperIndicatorName());
                ModelConfig l2ModelConfig = new ModelConfig(uid, level2.getIndicatorName(),
                        level1.getIndicatorName(), 0, 0
                        , "", "", 0, 0);
                modelConfigService.insert(l2ModelConfig);
                ArrayList<Indicator> level2SubIndicator = level2.getSubIndicator();
                for (Indicator level3 : level2SubIndicator) {
//                    System.out.println("        " + level3.getIndicatorName() + "   " + level3.getSuperIndicatorName() + "   " + level3.getMolecular() + "   " + level3.getDenominator());
                    ModelConfig l3ModelConfig = new ModelConfig(uid, level3.getIndicatorName(),
                            level2.getIndicatorName(), 0, 0
                            , level3.getMolecular(), level3.getDenominator(),
                            0, 0);
                    modelConfigService.insert(l3ModelConfig);
                }
            }
        }
    }

    //配置McCall
    @RequestMapping("/McCall/{qualityUid}")
    public String McCall(@PathVariable("qualityUid") int uid) {
        PSTMQModel model = getModel("McCall模型");
//        System.out.println(model);
        getModelToConfig(model, uid);

        qualityService.updateModel(uid, "McCall模型");
        Quality quality = qualityService.findQualityById(uid);
        quality.setStatus(3);
        qualityService.updatesStatus(quality);

        return "redirect:/qualityModel/AllQualities";


    }

    //配置Boehm
    @RequestMapping("/Boehm/{qualityUid}")
    public String Boehm(@PathVariable("qualityUid") int uid) {
        PSTMQModel model = getModel("Boehm模型");
//        System.out.println(model);
        getModelToConfig(model, uid);

        qualityService.updateModel(uid, "Boehm模型");
        Quality quality = qualityService.findQualityById(uid);
        quality.setStatus(3);
        qualityService.updatesStatus(quality);

        return "redirect:/qualityModel/AllQualities";
    }

    //配置ISO
    @RequestMapping("/ISO/{qualityUid}")
    public String ISO(@PathVariable("qualityUid") int uid) {
        PSTMQModel model = getModel("ISO模型");
//        System.out.println(model);
        getModelToConfig(model, uid);

        qualityService.updateModel(uid, "ISO模型");
        Quality quality = qualityService.findQualityById(uid);
        quality.setStatus(3);
        qualityService.updatesStatus(quality);

        return "redirect:/qualityModel/AllQualities";
    }


    //配置公安交管软件质量模型
    @RequestMapping("/conf/{qualityUid}")
    public String editQuality(@PathVariable("qualityUid") int uid, Model model) {
        this.uid = uid;
        model.addAttribute("uid", uid);
//        PSTMQModel pstmsModel = PSTMQModel.P_S_T_M_S_Q_M();
        PSTMQModel pstmsModel = getModel("公安交通管理软件质量模型");
        model.addAttribute("QualityModel", pstmsModel);

        return "qualityEvaluation/configModel";
    }

    //处理质量模型配置
    @RequestMapping("/modelConf")
    public String modelConf(IndicatorConfig qualityModel, Model model) {

        String[] name = qualityModel.getName();
        Set<String> level2NameSet = new HashSet<>();
        List<String> level2NameList = new ArrayList<>();
        Set<String> firstNameSet = new HashSet<>();
        List<String> firstNameList = new ArrayList<>();

//        PSTMQModel pstmqModel = PSTMQModel.P_S_T_M_S_Q_M();
        PSTMQModel pstmqModel = getModel("公安交通管理软件质量模型");
//        pstmqModel.show();
        ArrayList<Indicator> firstIndicator = pstmqModel.getFirstIndicator();
        ArrayList<Indicator> level2Indicator = new ArrayList<>();
        ArrayList<Indicator> level3Indicator = new ArrayList<>();

        for (Indicator level1 : firstIndicator) {
            ArrayList<Indicator> subIndicator = level1.getSubIndicator();
            level2Indicator.addAll(subIndicator);
            for (Indicator level2 : subIndicator) {
                ArrayList<Indicator> level2SubIndicator = level2.getSubIndicator();
                level3Indicator.addAll(level2SubIndicator);
            }
        }

        System.out.println("=====三级指标=====");
        for (String indicatorName : name) {
            for (Indicator l3 : level3Indicator) {
                String l3Name = l3.getIndicatorName();
                if (indicatorName.equals(l3Name)) {

                    String level2Name = l3.getSuperIndicatorName();
                    String molecular = l3.getMolecular();
                    String denominator = l3.getDenominator();
//                    double molecularResult = l3.getMolecularResult();
//                    double denominatorResult = l3.getDenominatorResult();
                    ModelConfig modelConfig = new ModelConfig(uid, indicatorName,
                            level2Name, 0, 0,
                            molecular, denominator,
                            0, 0);
                    System.out.println(modelConfig);
                    modelConfigService.insert(modelConfig);
                    if (level2NameSet.add(level2Name)) {
                        level2NameList.add(level2Name);
                    }
                }
            }
        }

        System.out.println("=====二级指标=====");
        for (String level2Name : level2NameList) {
            for (Indicator l2 : level2Indicator) {
                String l2Name = l2.getIndicatorName();
                if (level2Name.equals(l2Name)) {
                    String firstName = l2.getSuperIndicatorName();
                    ModelConfig modelConfig = new ModelConfig(uid, level2Name,
                            firstName, 0, 0
                            , "", "", 0, 0);
                    System.out.println(modelConfig);
                    modelConfigService.insert(modelConfig);
                    if (firstNameSet.add(firstName)) {
                        firstNameList.add(firstName);
                    }
                }
            }
        }

        System.out.println("=====一级指标=====");
        for (String firstName : firstNameList) {
            ModelConfig modelConfig = new ModelConfig(uid, firstName, "null",
                    0, 0
                    , "", "", 0, 0);
            System.out.println(modelConfig);
            modelConfigService.insert(modelConfig);
        }


        qualityService.updateModel(uid, "公安交管软件质量模型");
        Quality quality = qualityService.findQualityById(uid);
        //应该是3
        quality.setStatus(3);
        qualityService.updatesStatus(quality);

        return "redirect:/qualityModel/AllQualities";
    }

    public PSTMQModel getModel(String modelName) {
        PSTMQModel model = new PSTMQModel();

        List<Indicator> firstIndicator = baseService.query1stIndicatorByBaseName(modelName);
        for (Indicator indicator : firstIndicator) {
            model.insertFirstIndicator(indicator.getIndicatorName(),
                    indicator.indicatorDescription);
            List<Indicator> secondIndicator = baseService.query2ndIndicatorBySuperId(indicator.getIndicatorId());
            for (Indicator secIndicator : secondIndicator) {
                model.insertSecondAndThirdIndicator(indicator.getIndicatorName(),
                        secIndicator.getIndicatorName(), secIndicator.indicatorDescription);
                List<BaseNode> thirdIndicator = baseService.queryNodeLv3BySuperId(secIndicator.getIndicatorId());
                for (BaseNode thiIndicator : thirdIndicator) {
                    Indicator msg = baseService.queryIndicatorById(thiIndicator.getInfo_id());
                    model.insertThirdIndicator(secIndicator.getIndicatorName(),
                            thiIndicator.getNode_name(), "",
                            msg.getMolecular(), msg.getDenominator());
                }
            }
        }
        return model;
    }
}
