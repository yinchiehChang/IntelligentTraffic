package com.tjm.controller.quality;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.tjm.pojo.base.Base;
import com.tjm.pojo.quality.Quality;
import com.tjm.service.BaseService;
import com.tjm.service.CodeParseInfoService;
import com.tjm.service.ModelConfigService;
import com.tjm.service.QualityService;
import com.tjm.utils.ParseWordUtil;
import com.tjm.utils.StrUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/quality")
public class QualityController {

    @Autowired
    private QualityService qualityService;
    @Autowired
    private CodeParseInfoService codeParseInfoService;
    @Autowired
    private ModelConfigService modelConfigService;
    @Autowired
    private BaseService baseService;

    //查询全部
    @RequestMapping("/AllQualities")
    public String list(Model model) {
        File directory = new File("src/main/resources/word/quality/template");
        File directory2 = new File("src/main/resources/word/quality/template-parse");
        try {
            FileUtils.cleanDirectory(directory);
            FileUtils.cleanDirectory(directory2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Quality> qualities = qualityService.queryQualityList();
        model.addAttribute("qualities", qualities);
        return "qualityEvaluation/qualityInformationGathering";
    }


    //解析文件
    @RequestMapping("/parseWord")
    @ResponseBody
    public Map<String, Object> parseWord() {
        Map<String, Object> map = new HashMap<>();

        List<String> tableList;
        List<String> headerList;
        File file;
        try {
            File directory = new File("src/main/resources/word/quality/template-parse");
            List<File> fileList = (List<File>) FileUtils.listFiles(directory, null, false);//列出该目录下的所有文件，不递归
//            path = fileList.get(0).getAbsolutePath();
            file = fileList.get(0);
            if (file.getAbsolutePath().endsWith(".doc")) {
                // load DOC with an instance of Document
                Document document = new Document(file.getAbsolutePath());
                // call Save method while passing SaveFormat.DOCX
                document.save("src/main/resources/word/quality/template-parse/doc2docx.docx", SaveFormat.DOCX);
                file = new File("src/main/resources/word/quality/template-parse/doc2docx.docx");
//                System.out.println(file.getAbsolutePath());
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

            /*for (int i = 0; i < tableList.size(); i++) {
                System.out.print("第" + i + "格：");
                System.out.println(tableList.get(i));
            }*/

            map.put("softwareName", tableList.get(33));
            map.put("productionUnit", tableList.get(1));
            map.put("contactPerson", tableList.get(13));
            map.put("contactNumber", tableList.get(15));
            map.put("specificationModel", tableList.get(35));
        } catch (Exception e) {
            String errormsg = "不是Word文件，或者是不支持的Word格式！";
            System.out.println(errormsg);
            map.put("errormsg", errormsg);
            e.printStackTrace();
        }
        try {
            headerList = ParseWordUtil.readHeader(file);

            /*for (int i = 0; i < headerList.size(); i++) {
                System.out.print("第" + i + "行：");
                System.out.println(headerList.get(i));
            }*/

            map.put("qualityId", headerList.get(3).substring(5));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //清空临时文件夹
        /*File directory = new File("src/main/resources/word/quality/template-parse");
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return map;
    }

    //详情
    @RequestMapping("/detail/{qualityUid}")
    public String qualityDetail(@PathVariable("qualityUid") int uid, Model model) {
        Quality qualityById = qualityService.findQualityById(uid);
        model.addAttribute("quality", qualityById);
        String statusParse;
        Integer status = qualityById.getStatus();
        switch (status) {
            case 1:
                statusParse = "已创建质量评价任务";
                break;
            case 2:
                statusParse = "已解析代码信息";
                break;
            case 3:
                statusParse = "已配置质量模型";
                break;
            case 4:
                statusParse = "已生成测试用例，待指标计算";
                break;
            case 5:
                statusParse = "已完成指标计算，待设置权重";
                break;
            case 6:
                statusParse = "已完成指标计算和权重设置，待计算最终结果";
                break;
            case 7:
                statusParse = "已完成综合得分计算";
                break;
            default:
                statusParse = "其他";
        }
        model.addAttribute("status", statusParse);

        //时间
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");
//        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        String gmtCreated = df.format(qualityById.getGmt_created());
        String gmtModified = df.format(qualityById.getGmt_modified());
        model.addAttribute("gmtCreated", gmtCreated);
        model.addAttribute("gmtModified", gmtModified);

        return "qualityEvaluation/detailQuality";
    }

    //删除
    @RequestMapping("/del/{qualityUid}")
    public String deleteQuality(@PathVariable("qualityUid") int uid) {
        String destination = "src/main/resources/word/quality/" + uid + "/";
        File directory = new File(destination);
        org.apache.commons.io.FileUtils.deleteQuietly(directory);
        qualityService.deleteQualityById(uid);
        codeParseInfoService.deleteById(uid);
        modelConfigService.deleteById(uid);
        return "redirect:/quality/AllQualities";
    }

    //修改
    @RequestMapping("/edit/{qualityUid}")
    public String editQuality(@PathVariable("qualityUid") int uid, Model model) {
        File directory = new File("src/main/resources/word/quality/template");
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Quality qualityById = qualityService.findQualityById(uid);
        model.addAttribute("quality", qualityById);
        String statusParse;
        Integer status = qualityById.getStatus();
        switch (status) {
            case 1:
                statusParse = "已创建质量评价任务";
                break;
            case 2:
                statusParse = "已解析代码信息";
                break;
            case 3:
                statusParse = "已配置质量模型";
                break;
            case 4:
                statusParse = "已生成测试用例，待指标计算";
                break;
            case 5:
                statusParse = "已完成指标计算，待设置权重";
                break;
            case 6:
                statusParse = "已完成指标计算和权重设置，待计算最终结果";
                break;
            case 7:
                statusParse = "已完成综合得分计算";
                break;
            default:
                statusParse = "其他";
        }

        //选择测试用例
        List<Base> bases = baseService.queryFunctionPointBase();
        List<String> lists = new ArrayList<>();
        for (Base base : bases) {
            lists.add(base.getBase_name());
        }

        model.addAttribute("lists", lists);
        model.addAttribute("status", statusParse);

        //时间
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");
//        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        String gmtCreated = df.format(qualityById.getGmt_created());
        String gmtModified = df.format(qualityById.getGmt_modified());
        model.addAttribute("gmtCreated", gmtCreated);
        model.addAttribute("gmtModified", gmtModified);

        return "qualityEvaluation/editQuality";
    }

    //更新
    @RequestMapping("/updateQuality")
    public String updateCustomer(Quality quality) {
//        System.out.println(quality);
//        qualityService.updateQuality(quality);

        Integer uid = quality.getUid();

        String source = "src/main/resources/word/quality/template/";
        /*File requireDir = new File(source + "RequireFile/");
        File designDir = new File(source + "DesignFile/");
        File codingDir = new File(source + "CodingFile/");
        File testDir = new File(source + "TestFile/");*/
        File specificationDir = new File(source + "SoftwareRequirementsSpecification/");
        File manualDir = new File(source + "SoftwareManual/");
        File codeFileDir = new File(source + "CodeFile/");

        String destination = "src/main/resources/word/quality/" + uid + "/";
        File destDir = new File(destination);

        if (specificationDir.exists()) {
            try {
                File dir = new File(destination + "SoftwareRequirementsSpecification");
                FileUtils.deleteQuietly(dir);
                FileUtils.moveToDirectory(specificationDir, destDir, true);
                List<File> fileList = (List<File>)FileUtils.listFiles(dir,null,false);//列出该目录下的所有文件，不递归
//                    System.out.println(fileList.get(0).getAbsolutePath());
                quality.setSpecification_url(fileList.get(0).getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (manualDir.exists()) {
            try {
                File dir = new File(destination + "SoftwareManual");
                FileUtils.deleteQuietly(dir);
                FileUtils.moveToDirectory(manualDir, destDir, true);
                List<File> fileList = (List<File>)FileUtils.listFiles(dir,null,false);//列出该目录下的所有文件，不递归
                System.out.println(fileList.get(0).getAbsolutePath());
                quality.setManual_url(fileList.get(0).getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (codeFileDir.exists()) {
            try {
                File dir = new File(destination + "CodeFile");
                FileUtils.deleteQuietly(dir);
                FileUtils.moveToDirectory(codeFileDir, destDir, true);
                List<File> fileList = (List<File>)FileUtils.listFiles(dir,null,false);//列出该目录下的所有文件，不递归
                System.out.println(fileList.get(0).getAbsolutePath());
                quality.setCode_file_url(fileList.get(0).getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(quality);
        qualityService.updateQuality(quality);

        return "redirect:/quality/AllQualities";
    }

    //添加界面
    @RequestMapping("/toAddQuality")
    public String toAddPage(Model model) {
        File directory = new File("src/main/resources/word/quality/template");
        File directory2 = new File("src/main/resources/word/quality/template-parse");

        try {
            FileUtils.cleanDirectory(directory);
            FileUtils.cleanDirectory(directory2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //选择测试用例
        List<Base> bases = baseService.queryFunctionPointBase();
        List<String> lists = new ArrayList<>();
        for (Base base : bases) {
            lists.add(base.getBase_name());
        }
        /*lists.add("公安交通集成指挥平台通用技术条件");
        lists.add("软件需求规格说明文件");*/

        model.addAttribute("lists", lists);
        return "qualityEvaluation/addQuality";
    }

    //添加质量评价任务
    @RequestMapping("/addQuality")
    public String addQuality(Quality quality) {

//        System.out.println(quality);
        qualityService.insertQuality(quality);

        Integer uid = quality.getUid();

        quality.setSpecification_url("");
        quality.setManual_url("");
        quality.setCode_file_url("");

        String source = "src/main/resources/word/quality/template/";
        File srcDir = new File(source);
        File[] subdirs = srcDir.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY);
        String destination = "src/main/resources/word/quality/" + uid + "/";
        File destDir = new File(destination);

        assert subdirs != null;
        for (File dir : subdirs) {
            try {
//                System.out.println(dir.getName());
                if (dir.getName().equals("SoftwareRequirementsSpecification")) {
                    FileUtils.moveToDirectory(dir, destDir, true);
                    String url = destination + "SoftwareRequirementsSpecification/";
                    File urlFile = new File(url);
                    List<File> fileList = (List<File>)FileUtils.listFiles(urlFile,null,false);//列出该目录下的所有文件，不递归
//                    System.out.println(fileList.get(0).getAbsolutePath());
                    quality.setSpecification_url(fileList.get(0).getAbsolutePath());
                }
                if (dir.getName().equals("SoftwareManual")) {
                    FileUtils.moveToDirectory(dir, destDir, true);
                    String url = destination + "SoftwareManual/";
                    File urlFile = new File(url);
                    List<File> fileList = (List<File>)FileUtils.listFiles(urlFile,null,false);//列出该目录下的所有文件，不递归
//                    System.out.println(fileList.get(0).getAbsolutePath());
                    quality.setManual_url(fileList.get(0).getAbsolutePath());
                }
                if (dir.getName().equals("CodeFile")) {
                    FileUtils.moveToDirectory(dir, destDir, true);
                    String url = destination + "CodeFile/";
                    File urlFile = new File(url);
                    List<File> fileList = (List<File>)FileUtils.listFiles(urlFile,null,false);//列出该目录下的所有文件，不递归
//                    System.out.println(fileList.get(0).getAbsolutePath());
                    quality.setCode_file_url(fileList.get(0).getAbsolutePath());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        qualityService.updateQuality(quality);

        return "redirect:/quality/AllQualities";
    }

    //查询任务编号
    @RequestMapping("/queryById")
    public String queryById(String queryId, Model model) {
        List<Quality> qualityByQid = qualityService.findQualityByQid(queryId);

        System.out.println(qualityByQid);
        if (qualityByQid == null || qualityByQid.isEmpty()) {
            qualityByQid = qualityService.queryQualityList();
            model.addAttribute("error", "没有符合条件的对象！");
        }
        model.addAttribute("qualities", qualityByQid);
        return "qualityEvaluation/qualityInformationGathering";
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
            return "qualityEvaluation/qualityInformationGathering";
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
        return "qualityEvaluation/qualityInformationGathering";
    }

}
