package com.tjm.service;

import com.tjm.mapper.QualityMapper;
import com.tjm.pojo.quality.Quality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualityServiceImpl implements QualityService {

    @Autowired
    QualityMapper qualityMapper;

    @Override
    public int insertQuality(Quality quality) {
        //设置时间
        quality.setGmt_created(System.currentTimeMillis());
        quality.setGmt_modified(System.currentTimeMillis());
        return qualityMapper.insertQuality(quality);
    }

    @Override
    public List<Quality> queryQualityList() {
        return qualityMapper.queryQualityList();
    }

    @Override
    public Quality findQualityById(int uid) {
        return qualityMapper.findQualityById(uid);
    }

    @Override
    public List<Quality> findQualityByQid(String quality_id) {
        return qualityMapper.findQualityByQid(quality_id);
    }

    @Override
    public List<Quality> findQualityByName(String software_name) {
        return qualityMapper.findQualityByName(software_name);
    }

    @Override
    public List<Quality> findQualityByUnit(String production_unit) {
        return qualityMapper.findQualityByUnit(production_unit);
    }

    @Override
    public List<Quality> findQualityByAll(String quality_id, String software_name, String production_unit) {
        return qualityMapper.findQualityByAll(quality_id, software_name, production_unit);
    }

    @Override
    public List<Quality> findQualityByStatus(Integer status) {
        return qualityMapper.findQualityByStatus(status);
    }

    @Override
    public List<Quality> findQualityByStatusAndTime(Integer status, Long startTime, Long endTime) {
        return qualityMapper.findQualityByStatusAndTime(status, startTime, endTime);
    }

    @Override
    public List<Quality> findQualityByCateModelStatus(String software_category, String model, Integer status) {
        return qualityMapper.findQualityByCateModelStatus(software_category, model, status);
    }

    @Override
    public List<Quality> findQualityByCate(String software_category) {
        return qualityMapper.findQualityByCate(software_category);
    }

    @Override
    public List<Quality> findQualityByModel(String model) {
        return qualityMapper.findQualityByModel(model);
    }

    @Override
    public int deleteQualityById(int uid) {
        return qualityMapper.deleteQualityById(uid);
    }

    @Override
    public int updateQuality(Quality quality) {
        quality.setGmt_modified(System.currentTimeMillis());
        return qualityMapper.updateQuality(quality);
    }

    @Override
    public int updatesStatus(Quality quality) {
        qualityMapper.updateGmt_modified(quality.getUid(), System.currentTimeMillis());
        return qualityMapper.updatesStatus(quality);
    }

    @Override
    public int updateFinalScore(int uid, double final_score) {
        qualityMapper.updateGmt_modified(uid, System.currentTimeMillis());
        return qualityMapper.updateFinalScore(uid, final_score);
    }

    @Override
    public int updateModel(int uid, String model) {
        qualityMapper.updateGmt_modified(uid, System.currentTimeMillis());
        return qualityMapper.updateModel(uid, model);
    }

    @Override
    public int updateGmt_modified(int uid) {
        return qualityMapper.updateGmt_modified(uid, System.currentTimeMillis());
    }

    @Override
    public boolean isStatusAndTime(int uid, Long startTime, Long endTime) {
        return qualityMapper.isStatusAndTime(uid,startTime,endTime);
    }
}
