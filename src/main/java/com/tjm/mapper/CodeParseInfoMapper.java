package com.tjm.mapper;

import com.tjm.pojo.quality.CodeParseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CodeParseInfoMapper {
    //插入
    int insertCodeParseInfoMapper(CodeParseInfo info);

    //根据id删除
    int deleteById(int uid);

    //查询
    List<CodeParseInfo> findInfoById(int uid);

}
