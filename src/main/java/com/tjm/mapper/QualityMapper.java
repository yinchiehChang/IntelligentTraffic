package com.tjm.mapper;

import com.tjm.pojo.quality.Quality;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QualityMapper {
    int insertQuality(Quality quality);

    List<Quality> queryQualityList();

    Quality findQualityById(int uid);
    //查询
    List<Quality> findQualityByQid(String quality_id);
    List<Quality> findQualityByName(String software_name);
    List<Quality> findQualityByUnit(String production_unit);
    List<Quality> findQualityByAll(String quality_id, String software_name, String production_unit);

    //根据status查询
    List<Quality> findQualityByStatus(Integer status);
    //根据status和日期查询
    List<Quality> findQualityByStatusAndTime(Integer status, Long startTime, Long endTime);
    //根据cate, model, status查询
    List<Quality> findQualityByCateModelStatus(String software_category, String model, Integer status);
    //根据cate查询
    List<Quality> findQualityByCate(String software_category);
    //根据model查询
    List<Quality> findQualityByModel(String model);

    //根据id删除
    int deleteQualityById(int uid);

    //更新
    int updateQuality(Quality quality);
    //更新status
    int updatesStatus(Quality quality);
    //更新得分结果
    int updateFinalScore(int uid, double final_score);
    //更新模型
    int updateModel(int uid, String model);
    //更新修改时间
    int updateGmt_modified(int uid, Long gmt_modified);

    //根据时间和状态查询质量任务是否符合条件
    boolean isStatusAndTime(int uid, Long startTime, Long endTime);

}
