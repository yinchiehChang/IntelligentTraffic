package com.tjm.mapper;

import com.tjm.pojo.quality.ModelConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ModelConfigMapper {
    //插入
    int insert(ModelConfig model);

    //根据id删除
    int deleteById(int uid);

    //查询
    List<ModelConfig> findConfiguredModelById(int uid);
    List<ModelConfig> findConfiguredModelByIdAndSuper(int uid, String super_indicator_name);

    //更新
    int updateWeight(int uid, String indicator_name, double index_weight);
    int updateResult(int uid, String indicator_name, double molecular_result, double denominator_result, double index_result, String cal_mode);
    int updateIndexResult(int uid, String indicator_name, double index_result);

}
