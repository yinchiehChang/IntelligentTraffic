package com.tjm.service;

import com.tjm.pojo.Attachement;
import org.springframework.stereotype.Service;

@Service
public interface AttachementService {

    int insertAttachement(Attachement attachement);

}
