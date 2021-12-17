package com.tjm.service;

import com.tjm.pojo.Enterprise;
import org.springframework.stereotype.Service;

@Service
public interface EnterpriseService {

    int insertEnterprise(Enterprise enterprise);
}
