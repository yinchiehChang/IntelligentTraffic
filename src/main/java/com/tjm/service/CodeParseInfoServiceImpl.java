package com.tjm.service;

import com.tjm.mapper.CodeParseInfoMapper;
import com.tjm.pojo.quality.CodeParseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeParseInfoServiceImpl implements CodeParseInfoService{

    @Autowired
    CodeParseInfoMapper codeParseInfoMapper;

    @Override
    public int insertCodeParseInfoMapper(CodeParseInfo info) {
        return codeParseInfoMapper.insertCodeParseInfoMapper(info);
    }

    @Override
    public int deleteById(int uid) {
        return codeParseInfoMapper.deleteById(uid);
    }

    @Override
    public List<CodeParseInfo> findInfoById(int uid) {
        return codeParseInfoMapper.findInfoById(uid);
    }
}
