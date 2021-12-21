package com.tjm.service;

import com.tjm.pojo.quality.CodeParseInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CodeParseInfoService {
    int insertCodeParseInfoMapper(CodeParseInfo info);

    //根据id删除
    int deleteById(int uid);

    //查询
    List<CodeParseInfo> findInfoById(int uid);
}
