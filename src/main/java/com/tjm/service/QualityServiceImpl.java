package com.tjm.service;

import com.tjm.mapper.QualityMapper;
import com.tjm.pojo.quality.Quality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualityServiceImpl implements QualityService{

    @Autowired
    QualityMapper qualityMapper;

    @Override
    public int insertQuality(Quality quality) {
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
    public int deleteQualityById(int uid) {
        return qualityMapper.deleteQualityById(uid);
    }

    @Override
    public int updateQuality(Quality quality) {
        return qualityMapper.updateQuality(quality);
    }

    @Override
    public int updatesStatus(Quality quality) {
        return qualityMapper.updatesStatus(quality);
    }
}
