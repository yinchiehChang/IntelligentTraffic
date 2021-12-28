package com.tjm.controller;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.tjm.pojo.*;
import com.tjm.pojo.base.BaseNode;
import com.tjm.pojo.quality.DateChoose;
import com.tjm.pojo.testCase.FunctionPoint;
import com.tjm.pojo.testCase.GuideLine;
import com.tjm.pojo.testCase.TestCase;
import com.tjm.pojo.testCase.Test_Procedure;
import com.tjm.pojo.quality.Quality;
import com.tjm.service.BaseService;
import com.tjm.service.QualityService;
import com.tjm.service.TestCaseService;
import com.tjm.utils.ParseWordUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;


@Controller
@RequestMapping("/TestCases")
public class TestCaseController {

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private QualityService qualityService;

    @Autowired
    private BaseService baseService;

    @GetMapping("/addCase/{uid}")
    public String toAddMypage(@PathVariable("uid")int uid,Model model) {
        model.addAttribute("uid",uid);
        return "testCase/addCases";
    }

    @RequestMapping("/qualities")
    public String list(Model model) {
        List<Quality> qualities = qualityService.findQualityByStatus(3);
        model.addAttribute("qualities", qualities);
        return "testCase/DesignTestCase";
    }

    @RequestMapping("/exeQualities")
    public String listQuality(Model model) {
        List<Quality> qualities = qualityService.findQualityByStatus(3);
        model.addAttribute("qualities", qualities);
        return "testCase/ExecuteQuality";
    }

    /**
     * 根据配置好模型的任务id，获得该测试任务对应的知识库名（isGuideLine），
     * 再根据知识库名自动生成测试用例
     * @param uid 配置好模型的任务id
     * @return
     */
    @GetMapping("/generateCases/{uid}")
    public String GenerateCases(@PathVariable("uid")int uid,Model model) {
        Quality quality = qualityService.findQualityById(uid);
        //更改质量评价任务状态
//        quality.setStatus(4);
//        qualityService.updatesStatus(quality);
        List<TestCase> testCaseList = new ArrayList<>();
        if(testCaseService.findTestCases(uid).isEmpty()) {
            //获得知识库名
            String baseName = quality.getIsGuideLine();
            List<GuideLine> guideLineList = new ArrayList<>();
            guideLineList.addAll(getGuideLines(baseName));
            for (GuideLine guideLine : guideLineList) {
                TestCase testCase = new TestCase();
                testCase.setCase_id(guideLine.getSerial());
                testCase.setCase_name(guideLine.getCase_name());
                testCase.setCase_Description(guideLine.getDescription());
                testCase.setUid(uid);
//                testCaseList.add(testCase);
                testCaseService.insertTestCase(testCase);
            }
        }
        testCaseList.addAll(testCaseService.findTestCases(uid));
        model.addAttribute("testCaseList",testCaseList);
        model.addAttribute("uid",uid);
        return "testCase/TestCases";
    }

    //编辑测试用例信息
    @GetMapping("/editCases/{testCase_id}")
    public String toEditCases(@PathVariable("testCase_id")int testCase_id, Model model){
        TestCase testCase = testCaseService.findByCaseId(testCase_id);
        int uid = testCase.getUid();
        model.addAttribute("uid",uid);
        model.addAttribute("testCase_id",testCase_id);
        model.addAttribute("testCase",testCase);
        return "testCase/editCases";
    }

    //删除测试用例信息
    @GetMapping("/deletCases/{testCase_id}")
    public String toDelCases(@PathVariable("testCase_id")int testCase_id, Model model){
        TestCase testCase = testCaseService.findByCaseId(testCase_id);
        int uid = testCase.getUid();
        testCaseService.delCases(testCase_id);
        testCaseService.delTestProcedure(testCase_id);
        return "redirect:/TestCases/generateCases/"+uid;
    }

    //解析文件
    @RequestMapping("/parseWord")
    @ResponseBody
    public Map<String, Object> parseWord() {
        Map<String, Object> map = new HashMap<>();
        List<String> tableList = null;
        List<String> headerList = null;
        File file = null;
        try {
            File directory = new File("src/main/resources/word/testCases/template-parse");
            List<File> fileList = (List<File>) FileUtils.listFiles(directory, null, false);//列出该目录下的所有文件，不递归
//          path = fileList.get(0).getAbsolutePath();
            file = fileList.get(0);
            if (file.getAbsolutePath().endsWith(".doc")) {
                // load DOC with an instance of Document
                Document document = new Document(file.getAbsolutePath());
                // call Save method while passing SaveFormat.DOCX
                document.save("src/main/resources/word/quality/template-parse/doc2docx.docx", SaveFormat.DOCX);
                file = new File("src/main/resources/word/quality/template-parse/doc2docx.docx");
            }

        } catch (Exception e) {
            String errormsg = "找不到文件！";
            System.out.println(errormsg);
            map.put("errormsg", errormsg);
            e.printStackTrace();
            return map;
        }

        try {
            tableList = ParseWordUtil.readWord(file);

//            for (int i = 0; i < tableList.size(); i++) {
//                System.out.print("第" + i + "格：");
//                System.out.println(tableList.get(i));
//            }
            map.put("case_name", tableList.get(1));
            map.put("case_id", tableList.get(3));
            map.put("case_needsTrace", tableList.get(5));
            map.put("case_Description", tableList.get(7));
            map.put("case_init", tableList.get(9));
            map.put("case_endCondition", tableList.get(11));
            Map<List<String>, Object> mapList = new HashMap<>();
            int n = (tableList.size()-22)/6;
            List<String> steps = new ArrayList<>();
            List<String> exp_result = new ArrayList<>();
            List<String> evaluation = new ArrayList<>();
            for(int i=0;i<n;i++){
                steps.add(tableList.get(18+i*6+2));
                exp_result.add(tableList.get(18+i*6+3));
                evaluation.add(tableList.get(18+i*6+4));
            }
            map.put("steps",steps);
            map.put("exp_result",exp_result);
            map.put("evaluation",evaluation);
            map.put("size",steps.size());
        } catch (Exception e) {
            String errormsg = "不是Word文件，或者是不支持的Word格式！";
            System.out.println(errormsg);
            map.put("errormsg", errormsg);
            e.printStackTrace();
        }

        return map;
    }

    //测试用例信息提交
    @RequestMapping(value = "/saveTestCase", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> submitTestCase(TestCase testCase) {
        testCaseService.insertTestCase(testCase);
        int testCase_id = testCase.getTestCase_id();
        Map<String, Integer> res = new HashMap<>();
        res.put("testCase_id",testCase_id);
        return res;
    }

    //测试过程信息提交
    @RequestMapping(value = "/saveProcedure", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> submitProcedure(Test_Procedure test_procedure) {
        testCaseService.insertTestProcedure(test_procedure);
        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        res.put("message", "新增成功");
        return res;
    }

    //测试用例信息修改
    @RequestMapping(value = "/editTestCase", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> editTestCase(TestCase testCase) {
        testCaseService.editTestCase(testCase);
        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        res.put("message", "新增成功");
        return res;
    }

    //测试过程信息删除
    @RequestMapping(value = "/delProcedure", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> delProcedure(Test_Procedure test_procedure) {
        testCaseService.delTestProcedure(test_procedure.getTestCase_id());
        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        res.put("message", "新增成功");
        return res;
    }

    //查询对应测试用例的所有测试过程
    @RequestMapping(value = "/queryProcedure", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryProcedure(int testCase_id) {
        List<Test_Procedure> test_procedures = testCaseService.queryProcedure(testCase_id);
        Map<String, Object> res = new HashMap<>();
        res.put("test_procedures",test_procedures);
        return res;
    }

    private List<GuideLine> getGuideLines(String baseName) {
        List<GuideLine> guideLines = new ArrayList<>();

        List<BaseNode> nodeLv1 = baseService.queryNodeLv1ByBaseName(baseName);

        for (BaseNode nLv1: nodeLv1 ) {
            if(nLv1.getLeaf() == 1) {//该节点是叶节点，搜索该节点对应的详细信息
                addGuideLine(nLv1, guideLines);
            } else {//不是叶节点，搜索子节点
                List<BaseNode> nodeLv2 = baseService.queryNodeLv2BySuperId(nLv1.getNode_id());
                for (BaseNode nLv2: nodeLv2) {
                    if(nLv2.getLeaf() == 1) {
                        addGuideLine(nLv2, guideLines);
                    } else {
                        List<BaseNode> nodeLv3 = baseService.queryNodeLv3BySuperId(nLv2.getNode_id());
                        for(BaseNode nLv3: nodeLv3) {//目前最深只有三层，所以一定是叶节点
                            addGuideLine(nLv3, guideLines);
                        }
                    }

                }
            }
        }
        return guideLines;
    }

    private void addGuideLine(BaseNode node, List<GuideLine> guideLines) {
        try {
            FunctionPoint functionPoint = baseService.queryFunctionPointById(node.getInfo_id());
            guideLines.add(new GuideLine(node.getNode_name(), functionPoint.getSerial(), functionPoint.getDescription()));
        }catch (Exception e) {
            System.out.println("插入GuideLine数组时出错：");
            e.printStackTrace();
        }
    }

    //查询所有测试用例
    @GetMapping("/findAllCases/{uid}")
    private String findAllCases(@PathVariable("uid") int uid,Model model) {
        Collection<TestCase> testCases = testCaseService.findAllCases(uid);
        model.addAttribute("testCases",testCases);
        model.addAttribute("uid",uid);
        return "testCase/ExecuteCases";
    }

    //转到测试用例执行页面
    @GetMapping("/exeCases/{testCase_id}")
    private String exeCases(@PathVariable("testCase_id") int testCase_id,Model model) {
        //更新测试用例执行状态
        testCaseService.updateStatus(testCase_id,1);
        TestCase testCase = testCaseService.findByCaseId(testCase_id);
        model.addAttribute("testCase",testCase);
        model.addAttribute("testCase_id",testCase_id);
        return "testCase/exeResult";
    }

    //保存测试用例执行结果
    @RequestMapping(value = "/saveResult", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> saveResult(TestCase testCase) {
        testCaseService.editTestCase(testCase);
        testCaseService.updateStatus(testCase.getTestCase_id(),2);
        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        res.put("message", "新增成功");
        return res;
    }

    /**
     *  通过质量评价任务id获取到测试用例执行状态
     * @param uid 质量评价任务id
     * @return
     */
    @RequestMapping(value = "/getStatus", method = RequestMethod.POST)
    public Map<String,Integer> getCases(int uid){
        List<TestCase> testCaseList = testCaseService.findAllCases(uid);
        System.out.println(testCaseList);
        Map<String, Integer> res = new HashMap<>();
        for(TestCase testCase:testCaseList){
            res.put(testCase.getCase_id(),testCase.getCase_result());
        }
        return res;
    }

    @RequestMapping("/analysisCase")
    public String analysisCase(Model model) {
        List<Quality> qualities = qualityService.findQualityByStatus(3);
        model.addAttribute("qualities", qualities);
        return "testCase/AnalysisCase";
    }

    //测试用例执行完毕后，更新质量检查任务状态
    @PostMapping("/updateQualityStatus")
    @ResponseBody
    private Map<String, Object> updateQualityStatus(@RequestParam("uid") int uid,Model model) {
        Map<String, Object> res = new HashMap<>();
        try{
            Quality quality = qualityService.findQualityById(uid);
            //测试用例执行结束后，将质量检查项目id状态转换为4
            quality.setStatus(4);
            qualityService.updatesStatus(quality);
            res.put("code", "1");
            res.put("message", "状态更新成功！");
        }catch (Exception e){
            String errormsg = "质量检查任务状态更新失败！";
            System.out.println(errormsg);
            res.put("errormsg", errormsg);
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("/echarts")
    public String toAddpage(Model model) {
        List<Quality> qualities = qualityService.findQualityByStatus(3);
        model.addAttribute("qualities", qualities);
        return "testCase/echarts";
    }

    /**
     * 获得测试用例（TestCase）表中所有测试用例的执行结果，
     * 统计分析一项质量检查项目的测试用例通过率
     * @param model
     * @return
     */
    @PostMapping("/getAllData")
    @ResponseBody
    private Map<String, Object> getAllData(Model model) {
        Map<String, Object> res = new HashMap<>();
        try{
            List<Integer> qualityIds = testCaseService.queryQualityIds();
            List<String> names = new ArrayList<>();
            //测试用例通过率
            List<Float> rates = new ArrayList<>();
            System.out.println(qualityIds);
            for(Integer qualityId:qualityIds){
                //对应质量检测项目的测试用例总数
                int caseNum = testCaseService.countCases(qualityId);
                System.out.println(caseNum);
                //对应质量检测项目通过的测试用例总数
                int passNum = testCaseService.countPassCases(qualityId);
                System.out.println(passNum);
                DecimalFormat df = new DecimalFormat("0.####");
                float oldrate = Float.parseFloat(df.format((float)passNum/caseNum));

                float rate = oldrate * 100;
                System.out.println(rate);
                rates.add(rate);
            }
            System.out.println(names);
            System.out.println(rates);
            res.put("code", "1");
//            res.put("names",names);
            res.put("rates",rates);
            //将质量评价任务id作为横坐标
            res.put("qualityIds",qualityIds);
        }catch (Exception e){
            String errormsg = "获取数据失败，请检查数据库中是否存在数据！";
            System.out.println(errormsg);
            res.put("errormsg", errormsg);
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 根据时间选择返回结果
     * @param dateChoose
     * @return
     */
    @RequestMapping("/caseResTrend")
    @ResponseBody
    public Map<String, Object> testResTrend(DateChoose dateChoose) {
        Map<String, Object> res = new HashMap<>();

        Date startDate = dateChoose.getStartDate();
        Date endDate = dateChoose.getEndDate();
        System.out.println("===================");
        System.out.println(startDate);
        System.out.println(endDate);

        res.put("sDate", startDate.getTime());
        res.put("eDate", endDate.getTime());

        try{
            List<Integer> qualityIds = testCaseService.queryQualityIds();
            List<String> names = new ArrayList<>();
            //测试用例通过率
            List<Float> rates = new ArrayList<>();
            System.out.println(qualityIds);
            for(Integer qualityId:qualityIds){
                if(qualityService.isStatusAndTime(qualityId,startDate.getTime(),endDate.getTime())){
                    //对应质量检测项目的测试用例总数
                    int caseNum = testCaseService.countCases(qualityId);
                    System.out.println(caseNum);
                    //对应质量检测项目通过的测试用例总数
                    int passNum = testCaseService.countPassCases(qualityId);
                    System.out.println(passNum);
                    DecimalFormat df = new DecimalFormat("0.####");
                    float oldrate = Float.parseFloat(df.format((float)passNum/caseNum));

                    float rate = oldrate * 100;
                    System.out.println(rate);
                    rates.add(rate);
                }
            }
            System.out.println(names);
            System.out.println(rates);
            res.put("code", "1");
//            res.put("names",names);
            res.put("rates",rates);
            //将质量评价任务id作为横坐标
            res.put("qualityIds",qualityIds);
        }catch (Exception e){
            String errormsg = "获取数据失败，请检查数据库中是否存在数据！";
            System.out.println(errormsg);
            res.put("errormsg", errormsg);
            e.printStackTrace();
        }

//        List<String> names = new ArrayList<>();
//        List<Double> scores = new ArrayList<>();
//        List<Quality> qualityList = qualityService.findQualityByStatusAndTime(7,
//                startDate.getTime(), endDate.getTime());
//        for (Quality quality : qualityList) {
//            names.add(quality.getUid() + "#" + quality.getSoftware_name());
//            scores.add(quality.getFinal_score());
//        }
//        map.put("nameList", names);
//        map.put("scoreList", scores);


        return res;
    }

    //查询制定测试用例信息
    @PostMapping("/queryEditCase/{uid}")
    public String find_RequiredCase(@RequestParam(value="case_name",required =false) String case_name, @RequestParam(value="identification",required =false) String identification,
                                    @PathVariable(value="uid")int uid,
                            Model model){
        List<TestCase> testCaseList = testCaseService.find_RequiredCase(case_name,identification,uid);
        if(null==testCaseList || testCaseList.size()==0){
            model.addAttribute("msg","没有找到匹配记录");
        }
        model.addAttribute("testCaseList",testCaseList);
        return "testCase/TestCases";
    }

    //查询执行测试用例信息
    @PostMapping("/queryExeCase/{uid}")
    public String find_RequiredExeCase(@RequestParam(value="case_name",required =false) String case_name, @RequestParam(value="identification",required =false) String identification,
                                       @PathVariable(value="uid")int uid,
                                    Model model){
        List<TestCase> testCaseList = testCaseService.find_RequiredCase(case_name,identification,uid);
        if(null==testCaseList || testCaseList.size()==0){
            model.addAttribute("msg","没有找到匹配记录");
        }
        model.addAttribute("testCases",testCaseList);
        return "testCase/ExecuteCases";
    }

}
