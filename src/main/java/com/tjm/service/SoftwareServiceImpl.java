package com.tjm.service;

import com.tjm.mapper.SoftwareMapper;
import com.tjm.pojo.Software;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareServiceImpl implements SoftwareService{

    @Autowired
    SoftwareMapper softwareMapper;

    @Override
    public List<Software> getSoftwareList(int id) {
        return softwareMapper.getSoftwareList(id);
    }

    @Override
    public Software getSoftware(String software_uid) {
        return softwareMapper.getSoftware(software_uid);
    }

    @Override
    public int insertSoftware(Software software) {
        return softwareMapper.insertSoftware(software);
    }

    @Override
    public int delSoftware(String software_uid) {
        return softwareMapper.delSoftware(software_uid);
    }

    @Override
    public int updateSoftware(Software software) {
        return softwareMapper.updateSoftware(software);
    }
}
