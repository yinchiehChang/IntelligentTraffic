package com.tjm.service;

import com.tjm.mapper.PlatformMapper;
import com.tjm.pojo.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformServiceImpl implements PlatformService{

    @Autowired
    PlatformMapper platformMapper;

    @Override
    public int insertPlatform(Platform platform) {
        return platformMapper.insertPlatform(platform);
    }

    @Override
    public List<Platform> queryPlatformList() {
        return platformMapper.queryPlatformList();
    }
}
