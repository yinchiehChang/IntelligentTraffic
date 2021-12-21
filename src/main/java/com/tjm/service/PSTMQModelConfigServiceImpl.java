package com.tjm.service;

import com.tjm.mapper.PSTMQModelConfigMapper;
import com.tjm.pojo.quality.PSTMQModelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Deprecated
public class PSTMQModelConfigServiceImpl implements PSTMQModelConfigService{

    @Autowired
    PSTMQModelConfigMapper pstmqModelConfigMapper;

    @Override
    public int insertModelConfig(PSTMQModelConfig model) {
        return pstmqModelConfigMapper.insertModelConfig(model);
    }

    @Override
    public int deleteById(int uid) {
        return pstmqModelConfigMapper.deleteById(uid);
    }
}
