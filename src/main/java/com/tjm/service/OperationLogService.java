package com.tjm.service;

import com.tjm.pojo.OperationLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OperationLogService {

    int insertOperLog(OperationLog operationLog);

    List<OperationLog> queryLogList();

    List<OperationLog> findByRequired(OperationLog operationLog);
}
