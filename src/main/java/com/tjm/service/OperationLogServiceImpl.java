package com.tjm.service;

import com.tjm.mapper.OperLogMapper;
import com.tjm.pojo.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("OperationLogService")
public class OperationLogServiceImpl implements OperationLogService{

    @Autowired
    private OperLogMapper operLogMapper;

    @Override
    public int insertOperLog(OperationLog operationLog) {
        return operLogMapper.insertOperLog(operationLog);
    }

    @Override
    public List<OperationLog> queryLogList(){
        return operLogMapper.queryLogList();
    }

    @Override
    public List<OperationLog> findByRequired(OperationLog operationLog) {
        return operLogMapper.findByRequired(operationLog);
    }
}
