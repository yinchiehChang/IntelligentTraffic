package com.tjm.service;

import com.tjm.pojo.Platform;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlatformService {

    int insertPlatform(Platform platform);

    List<Platform> queryPlatformList();
}
