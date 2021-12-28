package com.tjm.controller;

import com.tjm.pojo.OperationLog;
import com.tjm.pojo.SysLog;
import com.tjm.service.OperationLogService;
import com.tjm.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Controller
public class OperLogController {

    @Autowired
    private OperationLogService operationLogService;

    @RequestMapping("/operlogs")
    public String list(Model model){
        Collection<OperationLog> logs = operationLogService.queryLogList();
        //将结果放在请求中
        model.addAttribute("logs",logs);
        return "operrecord";
    }
    //将操作日志导出到excle中
    @RequestMapping(value = "/exportOperExcel", method = RequestMethod.GET)
    public void exportOperExcel()  throws IOException {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();;
        List<OperationLog> resultList = operationLogService.queryLogList();
        long t1 = System.currentTimeMillis();
        ExcelUtils.writeExcel(response, resultList, OperationLog.class);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
    }

    //多条件查询日志记录
    @PostMapping("/find_operlog_required")
    public String find_operlog_required(@RequestParam(value="username",required =false) String username, @RequestParam(value="oper_type",required =false) String oper_type,
                                    Model model){
        OperationLog operationLog = new OperationLog();
        operationLog.setUsername(username);
        operationLog.setOper_type(oper_type);
        List<OperationLog> operationLogs = operationLogService.findByRequired(operationLog);
        model.addAttribute("logs",operationLogs);
        return "operrecord";
    }
}
