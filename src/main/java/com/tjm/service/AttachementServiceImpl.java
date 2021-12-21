package com.tjm.service;

import com.tjm.mapper.AttachementMapper;
import com.tjm.pojo.Attachement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachementServiceImpl implements AttachementService{

    @Autowired
    AttachementMapper attachementMapper;


    @Override
    public int insertAttachement(Attachement attachement) {
        return attachementMapper.insertAttachement(attachement);
    }

    @Override
    public List<Attachement> queryFileByPath(String path) { return  attachementMapper.queryFileByPath(path); }

    @Override
    public Attachement queryFileByUid(String uid) { return attachementMapper.queryFileByUid(uid); }

    @Override
    public int deleteFileByUid(String uid) { return attachementMapper.deleteFileByUid(uid); }
}
