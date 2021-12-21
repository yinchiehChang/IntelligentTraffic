package com.tjm.controller.quality;

import com.tjm.pojo.quality.*;
import com.tjm.service.ModelConfigService;
import com.tjm.service.QualityService;
import com.tjm.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/qualityOverView")
public class QualityOverviewController {

    Integer uid;

    @Autowired
    private QualityService qualityService;
    @Autowired
    private ModelConfigService modelConfigService;

    //主界面
    @RequestMapping("/OverViewPage")
    public String list(Model model) {
        List<Quality> qualities = qualityService.findQualityByStatus(7);

        model.addAttribute("qualities", qualities);
        return "qualityEvaluation/qualityOverviewPage";
    }

    //
    @PostMapping("/homeOverView")
    @ResponseBody
    public List<Quality> homeOverView() {
        List<Quality> qualities = qualityService.findQualityByStatus(7);

        return qualities;
    }

    //详情界面
    @RequestMapping("/Overview/{qualityUid}")
    public String overview(@PathVariable("qualityUid") int uid, Model model) {
        this.uid = uid;
        model.addAttribute("uid", uid);
        Quality quality = qualityService.findQualityById(uid);
        model.addAttribute("quality", quality);

        PSTMQModel pstmqModel = getModel(uid);
        model.addAttribute("QualityModel", pstmqModel);

        return "qualityEvaluation/qualityOverviewInfo";
    }

    //封装PSTMQModel
    private PSTMQModel getModel(int uid) {
        PSTMQModel pstmqModel = new PSTMQModel();

        List<ModelConfig> level1Indicator = modelConfigService.findConfiguredModelByIdAndSuper(uid,
                "null");

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
