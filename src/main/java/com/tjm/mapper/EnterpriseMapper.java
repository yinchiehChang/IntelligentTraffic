package com.tjm.mapper;

import com.tjm.pojo.Enterprise;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface EnterpriseMapper {

    int insertEnterprise(Enterprise enterprise);
}
