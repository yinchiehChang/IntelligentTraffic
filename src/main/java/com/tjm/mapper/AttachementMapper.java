package com.tjm.mapper;

import com.tjm.pojo.Attachement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AttachementMapper {

    int insertAttachement(Attachement attachement);

    List<Attachement> queryFileByPath(@Param("path") String path);

    Attachement queryFileByUid(@Param("uid") String uid);

    int deleteFileByUid(@Param("uid") String uid);
}
