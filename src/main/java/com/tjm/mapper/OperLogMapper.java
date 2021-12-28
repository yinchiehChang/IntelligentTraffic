package com.tjm.mapper;

import com.tjm.pojo.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OperLogMapper {
    int insertOperLog(OperationLog operationLog);

    List<OperationLog> queryLogList();

    List<OperationLog> findByRequired(OperationLog operationLog);
}
