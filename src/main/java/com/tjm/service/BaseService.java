package com.tjm.service;

import com.tjm.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseService {

    List<Base> queryBase();

    List<Issued_item> queryByPointids(int id);

    String findSecutiryLevelByPointid(int id);

    String queryByPointId(int id);

    List<BaseNode> queryNodeLv1ByBaseName(@Param("baseName") String baseName);

    List<BaseNode> queryNodeLv2BySuperId(@Param("superId") int superId);

    List<BaseNode> queryNodeLv3BySuperId(@Param("superId") int superId);

    FunctionPoint queryFunctionPointById(@Param("fid") int fid);
}
