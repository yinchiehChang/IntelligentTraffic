package com.tjm.config;
import com.tjm.pojo.SysLog;
import com.tjm.pojo.Sys_User;
import com.tjm.service.SysLogService;
import org.apache.shiro.SecurityUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Arrays;

@Aspect
@Component
public class LogAsPect {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(LogAsPect.class);


    @Autowired
    private SysLogService sysLogService;

    //表示匹配带有自定义注解的方法
    @Pointcut("@annotation(com.tjm.config.Log)")
    public void pointcut()
    {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) {
        Object result = null;
        long beginTime = System.currentTimeMillis();

        try {
            log.info("我在目标方法之前执行！");
            result = point.proceed();
            //log.info((String) result);
            long endTime = System.currentTimeMillis();
            insertLog(point,endTime-beginTime);
        } catch (Throwable e) {
        }
        return result;
    }


    private void insertLog(ProceedingJoinPoint point,long time) throws Throwable {
        //获取httpServlet对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature)point.getSignature();

        SysLog sys_log = new SysLog();

//        Log userAction = method.getAnnotation(Log.class);
//        if(userAction != null){
//            //注解上的描述
//            sys_log.setUser_action(userAction.value());
//        }

        //log.info(String.valueOf(userAction));

        //请求的类名
        String className = point.getTarget().getClass().getName();
        //请求的方法名
        //获取切入点所在的方法
        String methodName = signature.getName();
        //请求的方法参数值
        String args = Arrays.toString(point.getArgs());

        //从session中获取当前登录的人id
       Sys_User user = (Sys_User) SecurityUtils.getSubject().getPrincipal();
       String username = user.getUser_name();

        sys_log.setUsername(username);
        sys_log.setClass_name(className);
        sys_log.setMethod_name(methodName);
        sys_log.setArgs(args);

        //设置时间
        Timestamp creat_time = new java.sql.Timestamp(new Date().getTime());
        sys_log.setCreat_time(creat_time);

        //获取主机ip
        String ip = getRemoteHost(request);
        sys_log.setIp(ip);

        log.info("当前登陆人：{},类名:{},方法名:{},参数：{},执行时间：{},ip:{}",username, className, methodName, args, time,ip);
        //将log插入数据库中
        sysLogService.insertLog(sys_log);
    }

    //获取主机ip
    private String getRemoteHost(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String s : ips) {
                if (!("unknown".equalsIgnoreCase((String) s))) {
                    ip = s;
                    break;
                }
            }
        }
        return ip;
    }
}
