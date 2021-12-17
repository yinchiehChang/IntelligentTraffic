package com.tjm.service;

import com.tjm.mapper.AttachementMapper;
import com.tjm.pojo.Attachement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachementServiceImpl implements AttachementService{

    @Autowired
    AttachementMapper attachementMapper;


    @Override
    public int insertAttachement(Attachement attachement) {
        return attachementMapper.insertAttachement(attachement);
    }
}
