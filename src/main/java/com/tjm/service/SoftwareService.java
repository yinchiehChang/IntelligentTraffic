package com.tjm.service;

import com.tjm.pojo.Software;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SoftwareService {

    List<Software> getSoftwareList(int id);

    Software getSoftware(String software_uid);

    int insertSoftware(Software software);

    int delSoftware(String software_uid);

    int updateSoftware(Software software);

}
