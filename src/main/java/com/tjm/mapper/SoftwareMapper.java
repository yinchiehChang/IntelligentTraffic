package com.tjm.mapper;

import com.tjm.pojo.Software;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SoftwareMapper {

    List<Software> getSoftwareList(int id);

    Software getSoftware(String software_uid);

    int insertSoftware(Software software);

    int delSoftware(String software_uid);

    int updateSoftware(Software software);
}
