package com.tjm.service;

import com.tjm.pojo.quality.Quality;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QualityService {
    int insertQuality(Quality quality);

    List<Quality> queryQualityList();

    Quality findQualityById(int uid);

    List<Quality> findQualityByQid(String quality_id);
    List<Quality> findQualityByName(String software_name);
    List<Quality> findQualityByUnit(String production_unit);
    List<Quality> findQualityByAll(String quality_id, String software_name, String production_unit);

    //根据status查询
    List<Quality> findQualityByStatus(Integer status);


    //根据id删除
    int deleteQualityById(int uid);

    //更新
    int updateQuality(Quality quality);

    //更新status
    int updatesStatus(Quality quality);
}
