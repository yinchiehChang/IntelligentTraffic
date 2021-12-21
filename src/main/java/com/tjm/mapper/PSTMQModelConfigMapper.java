package com.tjm.mapper;

import com.tjm.pojo.quality.PSTMQModelConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
@Deprecated
public interface PSTMQModelConfigMapper {
    //插入
    int insertModelConfig(PSTMQModelConfig model);

    //根据id删除
    int deleteById(int uid);
}
