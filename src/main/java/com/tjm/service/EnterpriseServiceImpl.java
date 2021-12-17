package com.tjm.service;

import com.tjm.mapper.EnterpriseMapper;
import com.tjm.pojo.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseServiceImpl implements EnterpriseService{

    @Autowired
    EnterpriseMapper enterpriseMapper;

    @Override
    public int insertEnterprise(Enterprise enterprise) {
        return enterpriseMapper.insertEnterprise(enterprise);
    }
}
