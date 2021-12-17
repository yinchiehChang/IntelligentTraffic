package com.tjm.mapper;

import com.tjm.pojo.Platform;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PlatformMapper {

    int insertPlatform(Platform platform);

    List<Platform> queryPlatformList();
}
