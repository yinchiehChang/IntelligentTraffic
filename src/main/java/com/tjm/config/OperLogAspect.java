package com.tjm.config;

import com.tjm.pojo.OperationLog;
import com.tjm.pojo.Sys_User;
import com.tjm.service.OperationLogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;

@Aspect
@Component
public class OperLogAspect {
    private final static Logger log = org.slf4j.LoggerFactory.getLogger(LogAsPect.class);

    @Autowired
    private OperationLogService operationLogService;

    //表示匹配带有自定义注解的方法
    @Pointcut("@annotation(com.tjm.config.OperLog)")
    public void operLogPoinCut()
    {
    }

    @AfterReturning(value = "operLogPoinCut()",returning = "keys")
    public void saveOperLog(JoinPoint joinPoint, Object keys) {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        OperationLog operlog = new OperationLog();
        try{
            // 从切面织入点处通过反射机制获取织入点处的方法
                 MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
                 Method method = signature.getMethod();
           // 获取操作
                 OperLog opLog = method.getAnnotation(OperLog.class);
                 if (opLog != null) {
                     String operModul = opLog.operModul();
                     String operType = opLog.operType();
                     String operDesc = opLog.operDesc();
                     operlog.setOper_modul(operModul); // 操作模块
                     operlog.setOper_type(operType); // 操作类型
                     operlog.setOper_desc(operDesc); // 操作描述
                      }

            //从session中获取当前登录的人id
            Sys_User user = (Sys_User) SecurityUtils.getSubject().getPrincipal();
            String username = user.getUser_name();
            operlog.setUsername(username);

            //设置时间
            Timestamp creat_time = new java.sql.Timestamp(new Date().getTime());
            operlog.setOper_creat_time(creat_time);

            operationLogService.insertOperLog(operlog);
        }catch (Exception e){
        }
    }
}
