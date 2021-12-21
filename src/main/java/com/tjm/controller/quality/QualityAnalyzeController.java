package com.tjm.controller.quality;

import com.tjm.pojo.base.BaseNode;
import com.tjm.pojo.quality.*;
import com.tjm.service.BaseService;
import com.tjm.service.ModelConfigService;
import com.tjm.service.QualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sound.midi.Soundbank;
import java.util.*;

import static java.util.Arrays.asList;

@Controller
@RequestMapping("/qualityAnalyze")
public class QualityAnalyzeController {

    @Autowired
    private QualityService qualityService;
    @Autowired
    private ModelConfigService modelConfigService;
    @Autowired
    private BaseService baseService;

    @RequestMapping("/Analysis")
    public String list(Model model) {
//        PSTMQModel pstmqModel = PSTMQModel.P_S_T_M_S_Q_M();
        PSTMQModel pstmqModel = getModel("公安交通管理软件质量模型");
        ArrayList<Indicator> indicatorList = pstmqModel.getFirstIndicator();
        model.addAttribute("indicatorList", indicatorList);
        List<Quality> qualityList = qualityService.findQualityByStatus(7);
        model.addAttribute("qualities", qualityList);


        return "qualityEvaluation/qualityAnalysisPage";
    }

    @RequestMapping("/testResTrend")
    @ResponseBody
    public Map<String, Object> testResTrend(DateChoose dateChoose) {
        Map<String, Object> map = new HashMap<>();

        Date startDate = dateChoose.getStartDate();
        Date endDate = dateChoose.getEndDate();
        System.out.println("===================");
        System.out.println(startDate);
        System.out.println(endDate);

        map.put("sDate", startDate.getTime());
        map.put("eDate", endDate.getTime());

        List<String> names = new ArrayList<>();
        List<Double> scores = new ArrayList<>();
        List<Quality> qualityList = qualityService.findQualityByStatusAndTime(7,
                startDate.getTime(), endDate.getTime());
        for (Quality quality : qualityList) {
            names.add(quality.getUid() + "#" + quality.getSoftware_name());
            scores.add(quality.getFinal_score());
        }
        map.put("nameList", names);
        map.put("scoreList", scores);


        return map;
    }

    @RequestMapping("/getData")
    @ResponseBody
    public Map<String, Object> getData() {
        Map<String, Object> map = new HashMap<>();

        List<String> names = new ArrayList<>();
        List<Double> scores = new ArrayList<>();
        List<Quality> qualityList = qualityService.findQualityByStatus(7);
        /*for (int i = 0; i < 10; i++) {
            names.add(qualityList.get(i).getUid() + "#" + qualityList.get(i).getSoftware_name());
            scores.add(qualityList.get(i).getFinal_score());
        }*/
/*        for (int i = 0; i < 5; i++) {
            for (Quality quality : qualityList) {
                names.add(quality.getUid().toString());
                scores.add(quality.getFinal_score());
            }
        }*/
        for (Quality quality : qualityList) {
            names.add(quality.getUid().toString());
            scores.add(quality.getFinal_score());
        }

        map.put("nameList", names);
        map.put("scoreList", scores);

        return map;
    }

    @RequestMapping("/getData2")
    @ResponseBody
    public Map<String, Object> getData2() {
        Map<String, Object> map = new HashMap<>();

        List<Map<String, Object>> list = new ArrayList<>();
        List<String> cateList = Arrays.asList(
                "嵌入式软件",
                "桌面应用软件",
                "中间件软件",
                "移动应用软件",
                "大数据与云计算软件",
                "人工智能软件",
                "其他");

        for (String cate : cateList) {
            List<Quality> qualityByCate = qualityService.findQualityByCate(cate);
            Map<String, Object> newMap = new HashMap<>();
            newMap.put("value", qualityByCate.size());
            newMap.put("name", cate);
            list.add(newMap);
        }

        System.out.println(list);

        map.put("list", list);

        return map;
    }

    @RequestMapping("/getData3")
    @ResponseBody
    public Map<String, Object> getData3() {
        Map<String, Object> map = new HashMap<>();
        List<String> names = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();

        List<String> modelList = Arrays.asList(
                "公安交管软件质量模型",
                "McCall模型",
                "Boehm模型",
                "ISO模型");

        for (String model : modelList) {
            List<Quality> qualityByModel = qualityService.findQualityByModel(model);
            names.add(model);
            nums.add(qualityByModel.size());
        }

        map.put("nameList", names);
        map.put("numList", nums);

        return map;
    }


    @RequestMapping("/setModelIndicator")
    @ResponseBody
    public Map<String, Object> setModelIndicator(AnaInfo anaInfo) {
        System.out.println(anaInfo);
        Map<String, Object> map = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        list.add("全部指标");


        if (anaInfo.getModel().equals("公安交管软件质量模型")) {
//            PSTMQModel pstmqModel = PSTMQModel.P_S_T_M_S_Q_M();
            PSTMQModel pstmqModel = getModel("公安交通管理软件质量模型");
            ArrayList<Indicator> firstIndicator = pstmqModel.getFirstIndicator();
            for (Indicator indicator : firstIndicator) {
                list.add(indicator.getIndicatorName());
            }
        } else if (anaInfo.getModel().equals("McCall软件质量模型")) {
            list.add("McCall软件质量模型");
        } else if (anaInfo.getModel().equals("Boehm软件质量模型")) {
            list.add("Boehm软件质量模型");
        } else if (anaInfo.getModel().equals("ISO软件质量模型")) {
            list.add("ISO软件质量模型");
        }

        map.put("indicatorList", list);

        return map;
    }

    @RequestMapping("/getLevelData")
    @ResponseBody
    public Map<String, Object> getLevelData(AnaInfo anaInfo) {

        System.out.println(anaInfo);
//        List<Quality> qualityList = qualityService.findQualityByStatus(7);
        List<Quality> qualityList = null;
        qualityList = qualityService.findQualityByCateModelStatus(
                anaInfo.getCategory(), anaInfo.getModel(), 7);


        Map<String, Object> map = new HashMap<>();
        List<String> names = new ArrayList<>();

        PSTMQModel pstmqModel = null;

        if (anaInfo.getModel().equals("公安交管软件质量模型")) {
//            pstmqModel = PSTMQModel.P_S_T_M_S_Q_M();
            pstmqModel = getModel("公安交通管理软件质量模型");
        }


        ArrayList<Indicator> firstIndicator = pstmqModel.getFirstIndicator();

        List<List<Double>> scores = new ArrayList<>();
        for (int i = 0; i < firstIndicator.size(); i++) {
            scores.add(new ArrayList<>());
        }

        List<Double> finalScore = new ArrayList<>();
        int count = 0;
        for (Quality quality : qualityList) {
            names.add(quality.getSoftware_name());
            List<ModelConfig> modelList = modelConfigService.findConfiguredModelByIdAndSuper(quality.getUid(),
                    "null");

            for (int i = 0; i < modelList.size(); i++) {
                String indicator_name = modelList.get(i).getIndicator_name();
                for (int j = 0; j < firstIndicator.size(); j++) {
                    if (indicator_name.equals(firstIndicator.get(j).getIndicatorName())) {
                        scores.get(j).add(modelList.get(i).getIndex_result());
                    }
                }
            }

            for (int j = 0; j < firstIndicator.size(); j++) {
                try {
                    scores.get(j).get(count);
                } catch (IndexOutOfBoundsException e) {
                    scores.get(j).add(null);
                }
            }
            count++;

            finalScore.add(quality.getFinal_score());
        }

        map.put("nameList", names);
        String index = anaInfo.getIndex();
        if (!index.equals("全部指标")) {
            int i = Integer.parseInt(index.substring(index.length() - 1));
            map.put("scoreList", scores.get(i - 1));
            map.put("indicatorList", firstIndicator.get(i - 1).getIndicatorName());
        } else {
            List<String> indicatorList = new ArrayList<>();
            for (int i = 1; i <= firstIndicator.size(); i++) {
                indicatorList.add(firstIndicator.get(i - 1).getIndicatorName());
            }
            map.put("indicatorList", indicatorList);
            map.put("scores", scores);
        }
        map.put("finalScore", finalScore);

        return map;
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
