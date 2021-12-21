package com.tjm.controller;
import com.tjm.pojo.Attachement;
import com.tjm.pojo.ReceivedTask.TestRecord;
import com.tjm.pojo.Record_item;
import com.tjm.pojo.SysLog;
import com.tjm.pojo.Task_issued;
import com.tjm.service.AttachementService;
import com.tjm.service.ProjectService;
import com.tjm.service.RecordService;
import com.tjm.utils.ExcelUtils;
import com.tjm.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

@Controller
@RequestMapping("/Records")

/**
 * 包括接受安卓端数据接口以及查询记录数据
 */
public class RecordController {

    @Autowired
    AttachementService attachementService;

    @Autowired
    RecordService recordService;

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/getTheRecords",method = RequestMethod.POST)
    @ResponseBody
    public String getAll(@RequestBody List<TestRecord> testRecords) {
        System.out.println("testRecords=="+testRecords);
        for(TestRecord test_Test_record : testRecords){
            String url = "/images/"+test_Test_record.getUrl();
            test_Test_record.setUrl(url);
            recordService.insertRecord(test_Test_record);
        }
        return "0";
    }

    @RequestMapping(value = "/getTheRecordItems",method = RequestMethod.POST)
    @ResponseBody
    public String getAllPoints(@RequestBody List<Record_item> record_items) {
        System.out.println("record_items=="+record_items);
        for(Record_item record_item : record_items){
            recordService.insertRecordItems(record_item);
        }
        return "0";
    }


@RequestMapping(value = "/upload/picture",method = RequestMethod.POST)
@ResponseBody
    public Map<String, Object> uploadPicture(HttpServletRequest request,@RequestParam("file") MultipartFile file) {
        Attachement attachement = FileUtil.uploadImage(request, file,true);
        attachementService.insertAttachement(attachement);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("msg","success");
        return dataMap;
    }

    @RequestMapping("/test_records")
    public String getAllTask_issued(Model model) {
        Collection<Task_issued> task_issueds = projectService.queryTask_issued();
//     将结果放在请求中
        model.addAttribute("task_issueds",task_issueds);
        return "data_collection";
    }

    @RequestMapping("/home_records")
    public String getHomeTask_issued(Model model) {
        Collection<Task_issued> task_issueds = projectService.queryTask_issued();
//     将结果放在请求中
        model.addAttribute("task_issueds",task_issueds);
        return "home";
    }

    @RequestMapping("/seeAll/{check_id}/{item_id}")
    public String getAllRecords(@PathVariable("check_id")String check_id,@PathVariable("item_id")int item_id,Model model) {
        System.out.println(check_id);
        System.out.println(item_id);
        List<TestRecord> testRecordList = recordService.findByItemCheck(check_id,item_id);
//     将结果放在请求中
        model.addAttribute("testRecordList",testRecordList);
        return "detail_records";
    }

    @RequestMapping("/seeAllItems/{check_id}")
    public String getAllItems(@PathVariable("check_id")String check_id,Model model) {
        List<Record_item> record_items = recordService.findAllItems(check_id);
        System.out.println("record_items=="+record_items);
        for(Record_item record_item:record_items){
//            check_id =  check_id.substring(8,16);
//            System.out.println("subCheck"+check_id);
            int item_id = record_item.getItem_id();
            List<TestRecord> testRecordList = recordService.findByItemCheck(check_id,item_id);
            int suit = 0;
            int Nosuit = 0;
            for(TestRecord testRecord :testRecordList){
                if(testRecord.getIsSuitable()==1){
                    suit++;
                }else {
                    Nosuit++;
                }
            }
            if(suit == testRecordList.size()){
                record_item.setItem_status(2);
            }else if(Nosuit == testRecordList.size()){
                record_item.setItem_status(0);
            }else {
                record_item.setItem_status(1);
            }
        }
//     将结果放在请求中
        model.addAttribute("record_items",record_items);
        return "detail_points";
    }

    @RequestMapping("/seeDetail/{record_id}")
    public String getRecordsDetail(@PathVariable("record_id")int record_id,Model model) {
        TestRecord testRecord = recordService.findDetail(record_id);
//     将结果放在请求中
        model.addAttribute("testRecord",testRecord);
        return "SaveRecord";
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String,String> updateRecords(@RequestParam("record_id")int record_id,@RequestParam("text")String text,@RequestParam("isSuitable")int isSuitable) {
        Map<String,String> res = new HashMap<>();
        try {
            recordService.updateRecord(record_id,text,isSuitable);
        }catch (Exception e){
            String erromsg = "更新测试记录失败！";
            res.put("erromsg",erromsg);
        }
        //将结果放在请求中
        res.put("code", "1");
        res.put("message", "新增成功");
        return res;
    }

    /**
     * 测评该检查项目符合与不符合项数目
     * @param check_id 下发的检查项目id
     * @return
     */
    @PostMapping("/evaluation")
    @ResponseBody
    public Map<String,Integer> evaRecords(@RequestParam("check_id")String check_id) {
        Map<String,Integer> res = new HashMap<>();
        //总记录数
//        int num = recordService.findAll(check_id).size();
//        int NotqualifiedNum = recordService.sumNotQualified(check_id);
//        int qualifiedNum = recordService.sumQualified(check_id);
//        res.put("num",num);
//        res.put("qualifiedNum",qualifiedNum);
//        res.put("NotqualifiedNum",NotqualifiedNum);
        List<Record_item> record_items = recordService.findAllItems(check_id);
        //符合项
        int qualifiedNum = 0;
        //不符合项
        int NotqualifiedNum = 0;
        //部分符合项
        int midqualifiedNum = 0;
        int num = record_items.size();
        for(Record_item record_item:record_items){
            if(record_item.getItem_status()==0){
                NotqualifiedNum++;
            }else if(record_item.getItem_status()==1){
                midqualifiedNum++;
            }else{
                qualifiedNum++;
            }
        }
        res.put("num",num);
        res.put("qualifiedNum",qualifiedNum);
        res.put("NotqualifiedNum",NotqualifiedNum);
        res.put("midqualifiedNum",midqualifiedNum);
        return res;
    }

    //将测试记录导出到excle中
    @RequestMapping(value = "/exportExcel/{check_id}", method = RequestMethod.GET)
    public void exportExcel(@PathVariable("check_id")String check_id)  throws IOException {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();;
        List<TestRecord> testRecordList = recordService.findAll(check_id);
        System.out.println(testRecordList);
        long t1 = System.currentTimeMillis();
        ExcelUtils.exportExcel(response, testRecordList, TestRecord.class);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
    }

    //将错误的测试记录导出到excle中
    @RequestMapping(value = "/exportErrorExcel/{check_id}", method = RequestMethod.GET)
    public void exportErrorExcel(@PathVariable("check_id")String check_id)  throws IOException {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();;
        List<TestRecord> testRecordList = recordService.findAllError(check_id);
        System.out.println(testRecordList);
        long t1 = System.currentTimeMillis();
        ExcelUtils.exportExcel(response, testRecordList, TestRecord.class);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
    }
}
