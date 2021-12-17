package com.tjm.controller;

import com.tjm.config.Log;
import com.tjm.pojo.OperationLog;
import com.tjm.pojo.SysLog;
import com.tjm.service.SysLogService;
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
public class LogController {

    @Autowired
    private SysLogService sysLogService;

//    private LogDao logDao;
//
//    public LogController(LogDao logDao) {
//        this.logDao = logDao;
//    }

    //查询日志记录
    @RequestMapping("/logs")
    public String list(Model model){
        Collection<SysLog> logs = sysLogService.queryLogList();
        //将结果放在请求中
        model.addAttribute("logs",logs);
        return "record";
    }

    //将审计日志导出到excle中
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel()  throws IOException {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();;
        List<SysLog> resultList = sysLogService.queryLogList();
        long t1 = System.currentTimeMillis();
        ExcelUtils.writeExcel(response, resultList, SysLog.class);
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
    }

    //多条件查询日志记录
    @PostMapping("/find_log_required")
    public String find_log_required(@RequestParam(value="username",required =false) String username, @RequestParam(value="method_name",required =false) String method_name,
                                    Model model){
        SysLog log = new SysLog();
        log.setUsername(username);
        log.setMethod_name(method_name);
        List<SysLog> sysLogs = sysLogService.findByRequired(log);
        System.out.println(sysLogs);
        model.addAttribute("logs",sysLogs);
        return "record";
    }
}
