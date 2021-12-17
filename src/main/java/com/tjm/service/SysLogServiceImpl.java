package com.tjm.service;

import com.tjm.mapper.LogMapper;
import com.tjm.pojo.OperationLog;
import com.tjm.pojo.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService{

    @Autowired
    private LogMapper logMapper;

    @Override
    public int insertLog(SysLog log) {
        return logMapper.insertLog(log);
    }

    @Override
    public List<SysLog> queryLogList(){
        return logMapper.queryLogList();
    }

    @Override
    public List<SysLog> findByRequired(SysLog sysLog){
        return logMapper.findByRequired(sysLog);
    }
}
