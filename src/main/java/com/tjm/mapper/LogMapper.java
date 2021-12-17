package com.tjm.mapper;


import com.tjm.pojo.OperationLog;
import com.tjm.pojo.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LogMapper {

    int insertLog(SysLog log);

    List<SysLog> queryLogList();

    List<SysLog> findByRequired(SysLog sysLog);
}
