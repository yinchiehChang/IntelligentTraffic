package com.tjm.service;

import com.tjm.pojo.quality.PSTMQModelConfig;
import org.springframework.stereotype.Service;

@Service
@Deprecated
public interface PSTMQModelConfigService {
    //插入
    int insertModelConfig(PSTMQModelConfig model);

    //根据id删除
    int deleteById(int uid);
}
