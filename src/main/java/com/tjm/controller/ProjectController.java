package com.tjm.controller;

import com.alibaba.fastjson.JSON;
import com.tjm.pojo.*;
import com.tjm.service.*;
import com.tjm.utils.FileUtil;
import com.tjm.utils.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SoftwareService softwareService;

    @Autowired
    private UserService userService;

    @Autowired
    private BaseService baseService;

    @Autowired
    private TaskService taskService;

    //查询所有测试点，返回列表界面
    @RequestMapping("/point_names")
    public String list(Model model){
        List<String> point_names = projectService.queryPoint_names();
        //将结果放在请求中
        model.addAttribute("point_names",point_names);
        return "Points";
    }

    //项目信息提交
    @RequestMapping(value = "/project_set",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> projectSet(Project project){
        projectService.addProject(project);
        Map<String,String> res = new HashMap<>();
        res.put("code","1");
        res.put("message","新增成功");
        return res;
    }

    @RequestMapping("/projects")
    public String projectList(Model model){
        Collection<Project> projects = projectService.queryProjectList();
        List<Android_User> android_users = userService.findAndroid_Users();
        System.out.println(android_users);
        model.addAttribute("users",android_users);
        //将结果放在请求中
        model.addAttribute("projects",projects);
        return "check_points";
    }

    //删除项目
    @GetMapping("/delproject/{id}")
    public String deleteProject(@PathVariable("id")int id){
        projectService.delProject(id);
        return "redirect:/projects";
    }

    //删除用户
    @PostMapping("/queryByID")
    public String queryByID(@RequestParam int project_id){
        Project project =  projectService.queryByID(project_id);
        String json = JSON.toJSONString(project);
        return json;
    }

    //保存修改的项目信息
    @RequestMapping(value = "/project_edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> projectEdit(Project project){
        projectService.editProject(project);
        Map<String,String> res = new HashMap<>();
        res.put("code","1");
        res.put("message","新增成功");
        return res;
    }

    //设置检查点
    @RequestMapping (value = "/setPoints/{software_uid}",method = RequestMethod.GET)
    public String toSetPoints(@PathVariable("software_uid") String software_uid,Model model){
        Software software = softwareService.getSoftware(software_uid);
        System.out.println("需要设置的软件为："+software);
        model.addAttribute("software",software);
        return "Points";
    }

    //获得指定project_id的测试项目详细信息
    @GetMapping("/editproject/{project_id}")
    public String toEditProject(@PathVariable("project_id")int project_id, Model model){
        Project project = projectService.queryByID(project_id);
        model.addAttribute("project",project);
        return "EditPoints";
    }


    //解析point_names
    @GetMapping("/remark/{project_id}")
    public String toRemarkPoint(@PathVariable("project_id")int project_id, Model model){
        Project project = projectService.queryByID(project_id);
        model.addAttribute("project",project);
        return "SaveRecord";
    }

    //获得point_names
    @PostMapping("/getPoint_names")
    @ResponseBody
    public String[] toGetPoint(@RequestParam("project_id") int project_id, Model model){
        Project project = projectService.queryByID(project_id);
        String point_names = project.getPoint_names();
        String[] point_name = point_names.split("、");
        return point_name;
    }

//    //获得对应项目id所有记录
//    @PostMapping("/getAllRecords")
//    @ResponseBody
//    public List<Test_record> toGetRecords(@RequestParam("project_id") int project_id, Model model){
//        List<Test_record>  test_records= projectService.getAllRecords(project_id);
//        System.out.println(test_records);
//        return test_records;
//    }

//    //存入记录信息
//    @RequestMapping(value = "/saveRecord",method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String,String> add(Test_record test_record){
//        Map<String,String> res = new HashMap<>();
//        String record_id = MathUtil.getRandom620(8);
//        test_record.setRecord_id(record_id);
//        projectService.addRecord(test_record);
//        res.put("code","1");
//        res.put("message","新增成功");
//        return res;
//    }
//
//    //保存修改后的记录信息
//    @RequestMapping(value = "/EditRecord",method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String,String> EditRecord(Test_record test_record){
//        Map<String,String> res = new HashMap<>();
//        projectService.editRecord(test_record);
//        res.put("code","1");
//        res.put("message","新增成功");
//        return res;
//    }

    //编辑记录信息
    @GetMapping("/editRecord/{project_id}")
    public String toEditRecord(@PathVariable("project_id")int project_id, Model model){
        Project project = projectService.queryByID(project_id);
        model.addAttribute("project",project);
        return "EditRecord";
    }

    /**
     * 将任务下发安卓端
     * @param task_issued 下发的任务
     */
    @RequestMapping(value = "/projectIssued",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> toIssueProject(Task_issued task_issued){
        Map<String,String> res = new HashMap<>();
        Project project = projectService.queryByID(task_issued.getProject_id());
        task_issued.setName(task_issued.getName());
        task_issued.setProject_id(task_issued.getProject_id());
        task_issued.setCheck_id(task_issued.getCheck_id());
        String[] point_ids = project.getPoint_ids().split("、");
        int[] point_ids_toInt = new int[point_ids.length];
        for(int i=0;i<point_ids.length;i++){
            point_ids_toInt[i] = Integer.parseInt(point_ids[i]);
        }
        task_issued.setSecurity_level(baseService.findSecutiryLevelByPointid(point_ids_toInt[0]));
        String software_id = project.getSoftware_uid();
        Software software = softwareService.getSoftware(software_id);
        task_issued.setSoftware_name(software.getSoftware_name());
        task_issued.setVersion(software.getVersion());
        task_issued.setArchitecture(software.getArchitecture());
        task_issued.setDatabase(software.getDatabase());
        Task task = taskService.findTaskById(software.getUid());
        task_issued.setBoss_contact(task.getTelephone());
        task_issued.setContact_name(task.getContact_name());
        task_issued.setPlatform_name(task.getPlatform_name());
        task_issued.setUnit_name(task.getName());
        try{
            taskService.insertTaskIssued(task_issued);
        }catch (Exception e){
            String errormsg = "任务下发失败，请重新下发!";
            res.put("errormsg",errormsg);
        }
        res.put("code","1");
        res.put("message","新增成功");
        return res;
    }

    /**
     * 安卓端接口，通过安卓端用户名查找对应的任务信息
     * @param name 安卓端用户名
     * @return
     */
    @RequestMapping(value = "/queryIssuedtasks",method = RequestMethod.POST)
    @ResponseBody
    public List<Task_issued> queryIssuedtasks(String name){
        List<Task_issued> task_issuedList= projectService.queryByAndroidName(name);
        for(int i=0;i<task_issuedList.size();i++){
            Project project = projectService.queryByID(task_issuedList.get(i).getProject_id());
            String[] point_ids = project.getPoint_ids().split("、");
            int[] point_ids_toInt = new int[point_ids.length];
            List<Issued_item> itemList = new ArrayList<>();
            for(int j=0;j<point_ids.length;j++){
                point_ids_toInt[j] = Integer.parseInt(point_ids[j]);
                itemList.addAll(baseService.queryByPointids(point_ids_toInt[j]));
            }
            task_issuedList.get(i).setItems(itemList);
            System.out.println(task_issuedList.get(i));
            projectService.updateStatus(task_issuedList.get(i));
        }
        System.out.println("安卓端已接受任务！");
        System.out.println("task_issuedList"+task_issuedList);
        return task_issuedList;
    }

    @RequestMapping(value = "/exportdoc/{uid}", method = RequestMethod.GET)
    public String toDownload(@PathVariable("uid")int uid, HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> dataMap = new HashMap<String, Object>();
        String comment = "机房场地没有选择在具有防震、防风和防雨等能力的建筑内。\n" +
                "机房出入口没有配置电子门禁系统，控制、鉴别和记录进入的人员。\n";
        dataMap.put("comment",comment);
        String filename = "test等级保护测评问题汇总（物理部分）.docx";
        FileUtil.exportWord("word/test等级保护测评问题汇总（物理部分）2021-10-11 092633.docx",
                "/Users/tjm/doc",filename,dataMap,request,response);
        return "data_collection";
    }
}


