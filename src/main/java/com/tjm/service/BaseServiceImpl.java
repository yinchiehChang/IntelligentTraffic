package com.tjm.service;

import com.tjm.mapper.BaseMapper;
import com.tjm.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    BaseMapper baseMapper;

    @Override
    public List<Base> queryBase() {
        return baseMapper.queryBase();
    }

    @Override
    public List<Issued_item> queryByPointids(int id) {
        return baseMapper.addPointName(id);
    }

    @Override
    public String findSecutiryLevelByPointid(int id) {
        return baseMapper.findSecutiryLevelByPointid(id);
    }

    @Override
    public String queryByPointId(int id) {
        return baseMapper.queryByPointId(id);
    }

    @Override
    public List<BaseNode> queryNodeLv1ByBaseName(String baseName){ return baseMapper.queryNodeLv1ByBaseName(baseName); }
    @Override
    public List<BaseNode> queryNodeLv2BySuperId(int superId){ return baseMapper.queryNodeLv2BySuperId(superId); }
    @Override
    public List<BaseNode> queryNodeLv3BySuperId(int superId){ return baseMapper.queryNodeLv3BySuperId(superId); }

    @Override
    public FunctionPoint queryFunctionPointById(int fid) {
        return baseMapper.queryFunctionPointById(fid);
    }
}
