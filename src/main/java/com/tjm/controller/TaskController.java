package com.tjm.controller;

import com.tjm.pojo.*;
import com.tjm.service.*;
import com.tjm.utils.FileUtil;
import com.tjm.utils.MathUtil;
import org.apache.commons.io.IOUtils;
//import org.jodconverter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.tjm.utils.FileUtil.formateString;
import static com.tjm.utils.WordUtil.word2Pdf;


@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private DocumentAuditService documentAuditService;

    @GetMapping("/add")
    public String toAddpage() {
        return "task/add";
    }

    @GetMapping("/audit/{uid}")
    public String toAudit(@PathVariable("uid") int uid, Model model) {
        if (documentAuditService.findByTaskId(uid) == null) {
            Task task = taskService.findTaskById(uid);
            model.addAttribute("task", task);
            return "task/audit";
        } else {
            Task task = taskService.findTaskById(uid);
            model.addAttribute("task", task);
            Document_audit document_audit = documentAuditService.findByTaskId(uid);
            model.addAttribute("document_audit", document_audit);
            return "task/Editaudit";
        }
    }

    /**
     * 查询所有任务
     *
     * @return
     */
    @PostMapping("/taskList")
    @ResponseBody
    public List<Task> list(@RequestParam(value = "task_id", required = false) String task_id, @RequestParam(value = "product_name", required = false) String product_name,
                           @RequestParam(value = "contact_name", required = false) String contact_name) {
        System.out.println("task_id" + task_id);
        System.out.println("product_name" + product_name);
        System.out.println("contact_name" + contact_name);
        if ((task_id != null && !"".equals(task_id)) || (product_name != null && !"".equals(product_name)) || (contact_name != null && !"".equals(contact_name))) {
            Task task = new Task();
            task.setTask_id(task_id);
            task.setProduct_name(product_name);
            task.setContact_name(contact_name);
            System.out.println("task=====" + task);
            List<Task> tasks = taskService.findTask(task);
            System.out.println("tasks==" + tasks);
            return tasks;
        } else {
            List<Task> taskList = taskService.selectTaskList();
            System.out.println("2" + taskList);
            return taskList;
        }
    }

    /**
     * 查询所有审核通过任务
     *
     * @return
     */
    @GetMapping("/ListSuccess")
    @ResponseBody
    public List<Task> listAll() {
        List<Task> taskList = new ArrayList<>();
        List<Document_audit> document_auditList = documentAuditService.findAllSuccess();
        for (Document_audit document_audit : document_auditList) {
            int task_uid = document_audit.getTask_uid();
            taskList.add(taskService.findTaskById(task_uid));
        }
        return taskList;
    }

    //审核信息提交
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> submitAudit(Document_audit document_audit) {
        String uid = MathUtil.getRandom620(8);
        document_audit.setUid(uid);
        String date = formateString(new Date());
        document_audit.setCreate_date(date);
        documentAuditService.insertDocumentAudit(document_audit);
        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        res.put("message", "新增成功");
        return res;
    }

    //审核信息编辑后提交
    @RequestMapping(value = "/submitEditaudit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> submitEditAudit(Document_audit document_audit) {
        documentAuditService.updateAudit(document_audit);
        Map<String, String> res = new HashMap<>();
        res.put("code", "1");
        res.put("message", "新增成功");
        return res;
    }

    //添加任务信息
    @RequestMapping(value = "/addTask", method = RequestMethod.POST)
    @ResponseBody
    public int add(Task task) {
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        String task_id = time.format(date);
        task.setTask_id(task_id);
        taskService.insertTask(task);
        return 0;
    }

    @RequestMapping(value = "/downloadDoc/{uid}", method = RequestMethod.GET)
    public String toDownload(@PathVariable("uid") int uid, HttpServletRequest request, HttpServletResponse response) {
        Task task = taskService.findTaskById(uid);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String interface_sensitive = task.getInterface_sensitive();
        if (interface_sensitive.equals("本地敏感接口")) {
            dataMap.put("interface_sensitive", "非敏感接口   全国敏感接口   ☑︎本地敏感接口");
        } else if (interface_sensitive.equals("非敏感接口")) {
            dataMap.put("interface_sensitive", "☑非敏感接口   全国敏感接口   本地敏感接口");
        } else if (interface_sensitive.equals("全国敏感接口")) {
            dataMap.put("interface_sensitive", "︎非敏感接口   ☑全国敏感接口   本地敏感接口");
        }
        Date date = new Date();
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy.MM.dd");
        String datef = dateFormater.format(date);
        dataMap.put("datef", datef);
        dataMap.put("task_id", task.getTask_id());
        dataMap.put("network", task.getNetwork());
        dataMap.put("platform_name", task.getPlatform_name());
        dataMap.put("product_name", task.getProduct_name());
        dataMap.put("contact_name", task.getContact_name());
        dataMap.put("name", task.getName());
        dataMap.put("version", task.getVersion());
        dataMap.put("creditCode", task.getCreditCode());
        dataMap.put("telephone", task.getTelephone());
        dataMap.put("info", task.getInfo());
        System.out.println(dataMap);
        String filename = task.getTask_id() + ".docx";
        FileUtil.exportWord("word/外挂软件安全测试受理核办表.docx",
                "src/main/resources/word/materials/", filename, dataMap, request, response);
        return "material_audit";
    }

    @PostMapping("/find_task_required")
    //多条件查询审核结果
    public String find_task_required(@RequestParam(value = "network", required = false) String network, @RequestParam(value = "product_name", required = false) String product_name,
                                     @RequestParam(value = "version", required = false) String version, @RequestParam(value = "name", required = false) String name,
                                     Model model) {
        Task task = new Task();
        task.setNetwork(network);
        task.setProduct_name(product_name);
        task.setVersion(version);
        task.setName(name);
        List<Task> tasks = taskService.findByRequired(task);
        List<Task> tasksAudited = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            int uid = tasks.get(i).getUid();
            if (documentAuditService.findByTaskId(uid).getResult() == 0) {
                tasksAudited.add(tasks.get(i));
            }
        }
        System.out.println(tasksAudited);
        model.addAttribute("tasks", tasksAudited);
        return "mywork";
    }

    /**
     * 查询核办单
     *
     * @param task_id 送检材料编号
     * @param model
     * @return
     */
    @GetMapping("/auditPage/{task_id}")
    public String toAuditPage(@PathVariable("task_id") String task_id, Model model) {
        int uid = taskService.findByTaskId(task_id);
        Document_audit document_audit = documentAuditService.findByTaskId(uid);
        model.addAttribute("document_audit", document_audit);
        model.addAttribute("task_id", task_id);
        return "task/auditPage";
    }

    /*
    将word文档转换为pdf导出
     */

//    // 第一步：转换器直接注入
//    @Autowired
//    private DocumentConverter converter;

    @Autowired
    private HttpServletResponse response;

    //打开核办单
    @GetMapping("/toPdfFile/{task_id}")
    public String toPdfFile(@PathVariable("task_id") String task_id) throws IOException {
        System.out.println(task_id);
        String inputPath = "src/main/resources/word/materials/" + task_id + ".docx";
        System.out.println(inputPath);
        String fileName = task_id + ".pdf";
        word2Pdf(inputPath, fileName);
        //使用response,将pdf文件以流的方式发送的前端
        ServletOutputStream outputStream = response.getOutputStream();
        String newFilename = "src/main/resources/pdf/" + task_id + ".pdf";

        System.out.println(new File(newFilename).getAbsolutePath());
        InputStream in = new FileInputStream(new File(newFilename));// 读取文件
        // copy文件
        int i = IOUtils.copy(in, outputStream);
        System.out.println(i);
        in.close();
        outputStream.close();
        return "task/auditPage";
    }

    /**
     * 多条件查询任务信息
     *
     * @param task_id      材料编号
     * @param product_name 产品名称
     * @param contact_name 负责人
     * @param model
     * @return
     */
    @PostMapping("/find_task")
    public List<Task> find_task(@RequestParam(value = "task_id", required = false) String task_id, @RequestParam(value = "product_name", required = false) String product_name,
                                @RequestParam(value = "contact_name", required = false) String contact_name,
                                Model model) {
        Task task = new Task();
        task.setTask_id(task_id);
        task.setProduct_name(product_name);
        task.setContact_name(contact_name);
        List<Task> tasks = taskService.findTask(task);
        System.out.println("1" + tasks);
//        model.addAttribute("tasks", tasks);
        return tasks;
    }

    //删除任务
    @GetMapping("/delTask/{id}")
    public String deleteTask(@PathVariable("id") int id) {
        taskService.delTask(id);
        documentAuditService.delDocument(id);
        return "redirect:/material_audit.html";
    }

    //检查任务是否已被审核
    @RequestMapping(value = "/check_if_audit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> checkAudit(int uid) {
        List<Document_audit> document_audits = documentAuditService.findDocuments(uid);
        Map<String, String> res = new HashMap<>();
        if (document_audits.isEmpty()) {
            res.put("code", "0");
        } else {
            for (Document_audit document_audit : document_audits) {
                System.out.println(document_audit);
                if (document_audit.getResult() == 0) {
                    //审核通过
                    res.put("code", "1");
                } else {
                    //审核未通过，请重新提交材料
                    res.put("code", "500");
                }
            }
        }
        return res;
    }

    /**
     * 返回审核通过的任务到审核任务查询界面
     */
    @GetMapping("/auditSuccess")
    public String auditSuccess(Model model) {
        List<Task> taskList = new ArrayList<>();
        List<Document_audit> document_auditList = documentAuditService.findAllSuccess();
        for (Document_audit document_audit : document_auditList) {
            int task_uid = document_audit.getTask_uid();
            taskList.add(taskService.findTaskById(task_uid));
        }
        model.addAttribute("tasks", taskList);
        return "mywork";
    }
}
