package com.tjm.service;

import com.tjm.pojo.OperationLog;
import com.tjm.pojo.SysLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysLogService {

    int insertLog(SysLog log);

    List<SysLog> queryLogList();

    List<SysLog> findByRequired(SysLog sysLog);


}
