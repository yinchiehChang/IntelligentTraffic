package com.tjm.controller;

import com.tjm.pojo.quality.*;
import com.tjm.service.QualityService;
import com.tjm.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/qualityIndex")
public class QualityIndexController {

    Integer uid;

    @Autowired
    private QualityService qualityService;

    @RequestMapping("/Indexes")
    public String list(Model model) {
        List<Quality> qualities = qualityService.findQualityByStatus(3);
        model.addAttribute("qualities", qualities);
        return "qualityEvaluation/qualityIndexPage";
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
