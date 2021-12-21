package com.tjm.controller.quality;

import com.tjm.pojo.quality.CodeParseInfo;
import com.tjm.pojo.quality.Quality;
import com.tjm.service.CodeParseInfoService;
import com.tjm.service.QualityService;
import com.tjm.utils.JdtCodeParseUtils;
import net.lingala.zip4j.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/qualityCodeParse")
public class QualityCodeParseController {

    @Autowired
    private QualityService qualityService;
    @Autowired
    private CodeParseInfoService codeParseInfoService;

    //查询全部状态为1的项目
    @RequestMapping("/AllParse")
    public String list(Model model) {
        List<Quality> qualities = qualityService.findQualityByStatus(1);
        /*List<Quality> lists = new ArrayList<>();
        for (Quality quality : qualities) {
//            System.out.println(quality.getCode_file_url());
            if (quality.getCode_file_url() != null && !quality.getCode_file_url().isEmpty()) {
                lists.add(quality);
            }
        }*/
        
        model.addAttribute("qualities", qualities);
        return "qualityEvaluation/qualityCodeParsePage";
    }

    @RequestMapping("/parseCode/{qualityUid}")
    public String parseCode(@PathVariable("qualityUid") int uid, Model model) {
        Quality quality = qualityService.findQualityById(uid);
        String codeFileUrl = quality.getCode_file_url();

        if (codeFileUrl == null || codeFileUrl.isEmpty()) {
            model.addAttribute("errormsg","请先上传项目代码！");
            return "forward:/qualityCodeParse/AllParse";
        } else {
            try {
                File file = new File(codeFileUrl);
                String fileName = file.getName();
                String parentPath = file.getParent();
                String dest = parentPath + "/" + fileName.substring(0, fileName.lastIndexOf("."));
                Path p = Paths.get(dest);
                boolean notExists = Files.notExists(p);

                if (notExists) {
                    ZipFile zipFile = new ZipFile(codeFileUrl);
                    zipFile.extractAll(dest);
                }

                File dir = new File(dest);
                Map<String, Integer> info = JdtCodeParseUtils.getCodeInfo(dir);

                Integer codeLine = info.get("LOC");
                Integer commentLine = info.get("CommentLine");
                Integer validCodeLine = info.get("ValidLine");
                Integer maxFanIn = info.get("MaxFanIn");
                Integer maxFanOut = info.get("MaxFanOut");
                CodeParseInfo codeParseInfo = new CodeParseInfo(uid, codeLine,
                        commentLine, validCodeLine,
                        maxFanIn, maxFanOut);
                codeParseInfoService.insertCodeParseInfoMapper(codeParseInfo);

                model.addAttribute("result","result");

                model.addAttribute("codeLine",codeLine);
                model.addAttribute("commentLine",commentLine);
                model.addAttribute("validCodeLine",validCodeLine);
                model.addAttribute("maxFanIn",maxFanIn);
                model.addAttribute("maxFanOut",maxFanOut);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        quality.setStatus(2);
        qualityService.updatesStatus(quality);

        return "forward:/qualityCodeParse/AllParse";
    }


}
