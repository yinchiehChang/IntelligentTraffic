package com.tjm.mapper;

import com.tjm.pojo.Attachement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AttachementMapper {

    int insertAttachement(Attachement attachement);
}
