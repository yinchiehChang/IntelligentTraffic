package com.tjm.service;

import com.tjm.pojo.Attachement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttachementService {

    int insertAttachement(Attachement attachement);

    List<Attachement> queryFileByPath(@Param("path") String path);

    Attachement queryFileByUid(@Param("uid") String uid);

    int deleteFileByUid(@Param("uid") String uid);

}
