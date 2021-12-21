package com.tjm.service;

import com.tjm.mapper.BaseMapper;
import com.tjm.pojo.*;
import com.tjm.pojo.base.*;
import com.tjm.pojo.base.Base;
import com.tjm.pojo.base.BaseNode;
import com.tjm.pojo.quality.Indicator;
import com.tjm.pojo.testCase.FunctionPoint;
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

    @Override
    public List<com.tjm.pojo.base.Base> queryBaseByClass(int baseClass) {
        return baseMapper.queryBaseByClass(baseClass);
    }
    @Override
    public List<com.tjm.pojo.base.Base> queryBaseById(int baseId) {
        return baseMapper.queryBaseById(baseId);
    }
    @Override
    public List<com.tjm.pojo.base.Base> queryBaseByName(String baseName) { return baseMapper.queryBaseByName(baseName); }
    @Override
    public int insertBase(com.tjm.pojo.base.Base base){ return baseMapper.insertBase(base); }
    @Override
    public int deleteBase(int baseId){ return baseMapper.deleteBase(baseId); }

    @Override
    public List<Base> queryFunctionPointBase() {
        return baseMapper.queryFunctionPointBase();
    }

    @Override
    public List<BaseClass> queryAllBaseClass(){ return baseMapper.queryAllBaseClass(); }
    @Override
    public BaseClass queryBaseClassByClassNum(int classNum){ return baseMapper.queryBaseClassByClassNum(classNum); }
    @Override
    public int insertBaseClass(BaseClass baseClass){ return baseMapper.insertBaseClass(baseClass); }
    @Override
    public int deleteBaseClassByClassNum(int classNum){ return baseMapper.deleteBaseClassByClassNum(classNum); }

    @Override
    public List<BaseSecurity> querySecurityByBaseId(int baseId) { return baseMapper.querySecurityByBaseId(baseId); }
    @Override
    public List<BaseSecurity> querySecurityById(int securityId) { return  baseMapper.querySecurityById(securityId); }
    @Override
    public  List<BaseSecurity> querySecurityByLevel(String securityLevel, String baseId) {return baseMapper.querySecurityByLevel(securityLevel, baseId); }
    @Override
    public int insertSecurity(BaseSecurity security) { return baseMapper.insertSecurity(security); }

    @Override
    public List<BaseArticle> queryArticleList() {
        return baseMapper.queryArticleList();
    }
    @Override
    public List<BaseArticle> queryArticleById(int articleId){ return baseMapper.queryArticleById(articleId); }
    @Override
    public List<BaseArticle> queryArticleByBaseId(int baseId){ return baseMapper.queryArticleByBaseId(baseId); }
    @Override
    public List<BaseArticle> queryArticleByName(String articleName, String securityId){ return baseMapper.queryArticleByName(articleName, securityId); }
    @Override
    public int insertArticle(BaseArticle article){ return baseMapper.insertArticle(article); }
    @Override
    public int deleteArticle(int articleId){ return baseMapper.deleteArticle(articleId); }

    @Override
    public List<BasePoint> queryPointList() {return baseMapper.queryPointList(); }
    @Override
    public List<BasePoint> queryPointById(int pointId){ return baseMapper.queryPointById(pointId); }
    @Override
    public List<BasePoint> queryPointByArticleId(int articleId){ return baseMapper.queryPointByArticleId(articleId); }
    @Override
    public List<BasePoint> queryPointByBaseId(int baseId){ return baseMapper.queryPointByBaseId(baseId); }
    @Override
    public List<BasePoint> queryPointByName(String pointName, String articleId){ return baseMapper.queryPointByName(pointName, articleId); }
    @Override
    public int insertPoint(BasePoint point){ return baseMapper.insertPoint(point); }
    @Override
    public int deletePoint(int pointId){ return baseMapper.deletePoint(pointId); }

    @Override
    public List<BaseItem> queryItemList() {return baseMapper.queryItemList();}
    @Override
    public List<BaseItem> queryItemById(int itemId){ return baseMapper.queryItemById(itemId); }
    @Override
    public List<BaseItem> queryItemByPointId(int pointId){ return baseMapper.queryItemByPointId(pointId); }
    @Override
    public List<BaseItem> queryItemByBaseId(int baseId){ return baseMapper.queryItemByBaseId(baseId); }
    @Override
    public List<BaseItem> queryItemByInput(String dx, String nr, String aqjb){ return baseMapper.queryItemByInput(dx, nr, aqjb); }
    @Override
    public int insertItem(BaseItem item){ return baseMapper.insertItem(item); }
    @Override
    public int updateItem(BaseItem item){ return baseMapper.updateItem(item); }
    @Override
    public int deleteItem(int itemId){ return baseMapper.deleteItem(itemId); }

    @Override
    public List<Indicator> query1stIndicatorByBaseName(String baseName){ return baseMapper.query1stIndicatorByBaseName(baseName); }
    @Override
    public List<Indicator> query2ndIndicatorBySuperId(int superId){ return baseMapper.query2ndIndicatorBySuperId(superId); }
    @Override
    public List<Indicator> query3rdIndicatorBySuperId(int superId){ return baseMapper.query3rdIndicatorBySuperId(superId); }
    @Override
    public Indicator queryIndicatorById(int indicatorId){ return baseMapper.queryIndicatorById(indicatorId); }
    @Override
    public int updateIndicator(Indicator indicator){ return baseMapper.updateIndicator(indicator); }
    @Override
    public int insertIndicator(Indicator indicator){ return baseMapper.insertIndicator(indicator); }
    @Override
    public int deleteIndicatorById(int indicatorId){ return baseMapper.deleteIndicatorById(indicatorId); }


    public List<com.tjm.pojo.base.BaseNode> queryNodeLv3ByInput(String lv1, String lv2, String lv3){ return baseMapper.queryNodeLv3ByInput(lv1, lv2, lv3); }
    @Override
    public List<com.tjm.pojo.base.BaseNode> queryNodeLv2ByInput(String lv1, String lv2){ return baseMapper.queryNodeLv2ByInput(lv1, lv2); }
    @Override
    public com.tjm.pojo.base.BaseNode findSuperNodeByLv3(int superId){ return baseMapper.findSuperNodeByLv3(superId); }
    @Override
    public com.tjm.pojo.base.BaseNode findSuperNodeByLv2(int superId){ return baseMapper.findSuperNodeByLv2(superId); }
    @Override
    public List<com.tjm.pojo.base.BaseNode> queryNodeWithSameSuperId(String level, int superId){ return baseMapper.queryNodeWithSameSuperId(level, superId); }
    @Override
    public com.tjm.pojo.base.BaseNode queryNodeByInfo(String level, String infoTable, int infoId){ return baseMapper.queryNodeByInfo(level, infoTable, infoId); }
    @Override
    public com.tjm.pojo.base.BaseNode queryNodeById(String level, int nodeId){ return baseMapper.queryNodeById(level, nodeId);}
    @Override
    public com.tjm.pojo.base.BaseNode findNodeByNameAndSuperId(String level, String nodeName, int superId){ return baseMapper.findNodeByNameAndSuperId(level, nodeName, superId); }
    @Override
    public int insertNode(String level, int superId, String nodeName, String description, int leaf, String infoTable, int infoId){ return baseMapper.insertNode(level, superId, nodeName, description, leaf, infoTable, infoId);}
    @Override
    public int deleteNodeById(String level, int nodeId){ return baseMapper.deleteNodeById(level, nodeId); }

    @Override
    public int updateFunctionPoint(FunctionPoint functionPoint){ return baseMapper.updateFunctionPoint(functionPoint); }
    @Override
    public int insertFunctionPoint(FunctionPoint functionPoint){ return baseMapper.insertFunctionPoint(functionPoint); }
    @Override
    public int deleteFunctionPointById(int fid){ return baseMapper.deleteFunctionPointById(fid); }

    @Override
    public CheckItem queryCheckItemById(int cid){ return baseMapper.queryCheckItemById(cid); }
    @Override
    public int updateCheckItem(CheckItem checkItem){ return baseMapper.updateCheckItem(checkItem); }
    @Override
    public int insertCheckItem(CheckItem checkItem){ return baseMapper.insertCheckItem(checkItem); }
    @Override
    public int deleteCheckItemById(int cid){ return baseMapper.deleteCheckItemById(cid); }

    @Override
    public List<BugLib> queryBugLibByBaseName(String baseName){ return baseMapper.queryBugLibByBaseName(baseName); }
    @Override
    public BugLib queryBugLibById(int bid){ return baseMapper.queryBugLibById(bid); }
    @Override
    public int updateBugLib(BugLib bugLib){ return baseMapper.updateBugLib(bugLib); }
    @Override
    public int deleteBugLibById(int bid){ return baseMapper.deleteBugLibById(bid); }
    @Override
    public int insertBugLib(BugLib bugLib){ return baseMapper.insertBugLib(bugLib); }
}
