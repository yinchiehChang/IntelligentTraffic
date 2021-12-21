package com.tjm.service;

import com.tjm.mapper.ModelConfigMapper;
import com.tjm.pojo.quality.ModelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelConfigServiceImpl implements ModelConfigService {

    @Autowired
    ModelConfigMapper modelConfigMapper;

    @Override
    public int insert(ModelConfig model) {
        return modelConfigMapper.insert(model);
    }

    @Override
    public int deleteById(int uid) {
        return modelConfigMapper.deleteById(uid);
    }

    @Override
    public List<ModelConfig> findConfiguredModelById(int uid) {
        return modelConfigMapper.findConfiguredModelById(uid);
    }

    @Override
    public List<ModelConfig> findConfiguredModelByIdAndSuper(int uid, String super_indicator_name) {
        return modelConfigMapper.findConfiguredModelByIdAndSuper(uid, super_indicator_name);
    }

    @Override
    public int updateWeight(int uid, String indicator_name, double index_weight) {
        return modelConfigMapper.updateWeight(uid, indicator_name, index_weight);
    }

    @Override
    public int updateResult(int uid, String indicator_name, double molecular_result, double denominator_result, double index_result, String cal_mode) {
        return modelConfigMapper.updateResult(uid, indicator_name, molecular_result, denominator_result, index_result, cal_mode);
    }


    @Override
    public int updateIndexResult(int uid, String indicator_name, double index_result) {
        return modelConfigMapper.updateIndexResult(uid, indicator_name, index_result);
    }

}
