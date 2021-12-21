package com.tjm.mapper;

import com.tjm.pojo.*;
import com.tjm.pojo.base.*;
import com.tjm.pojo.base.Base;
import com.tjm.pojo.base.BaseNode;
import com.tjm.pojo.quality.Indicator;
import com.tjm.pojo.testCase.FunctionPoint;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BaseMapper {

    //知识库
    @Select("select base_id,base_class,zt,base_name as name from BASE where base_class = 0")
    @Results({
            @Result(property = "base_id",column = "base_id"),
            @Result(property = "base_name",column = "name"),
            @Result(property = "base_class",column = "base_class"),
            @Result(property = "zt",column = "zt"),
            @Result(property = "securities",column = "base_id",javaType = List.class,many = @Many(select = "querySecurity"))
    })
    List<Base> queryBase();

    //安全等级
    @Select("select security_id,zt,base_id,security_level as name from SECURITY where base_id=#{base_id}")
    @Results({
            @Result(property = "security_id",column = "security_id"),
            @Result(property = "security_level",column = "name"),
            @Result(property = "zt",column = "zt"),
            @Result(property = "base_id",column = "base_id"),
            @Result(property = "articles",column = "security_id",javaType = List.class,many = @Many(select = "queryArticle"))
    })
    List<BaseSecurity> querySecurity(int base_id);

    //项目
    @Select("select article_id,zt,security_id,article_name as name from ARTICLE where security_id=#{security_id}")
    @Results({
            @Result(property = "article_id",column = "article_id"),
            @Result(property = "article_name",column = "name"),
            @Result(property = "zt",column = "zt"),
            @Result(property = "security_id",column = "security_id"),
            @Result(property = "points",column = "article_id",javaType = List.class,many = @Many(select = "queryPoint"))
    })
    List<BaseArticle> queryArticle(int security_id);

    //检测点
    @Select("select point_id as id,zt,article_id,point_name as name from POINT where article_id=#{article_id}")
    @Results({
            @Result(property = "point_id",column = "id"),
            @Result(property = "point_name",column = "name"),
            @Result(property = "zt",column = "zt"),
            @Result(property = "article_id",column = "article_id"),
            @Result(property = "items",column = "id",javaType = List.class,many = @Many(select = "queryItem"))
    })
    List<BasePoint> queryPoint(int article_id);

    //检测项
    @Select("select item_id,point_id,aqjb,nr,dx,ssbz,fxdj,sxh,zt from ITEM where point_id=#{id}")
    @Results({
            @Result(property = "item_id",column = "item_id"),
            @Result(property = "aqjb",column = "aqjb"),
            @Result(property = "nr",column = "nr"),
            @Result(property = "dx",column = "dx"),
            @Result(property = "ssbz",column = "ssbz"),
            @Result(property = "fxdj",column = "fxdj"),
            @Result(property = "sxh",column = "sxh"),
            @Result(property = "zt",column = "zt"),
            @Result(property = "point_id",column = "point_id"),
    })
    List<BaseItem> queryItem(int id);

    List<Issued_item> addPointName(int id);

    String findSecutiryLevelByPointid(int id);

    String  queryByPointId(int id);

    List<BaseNode> queryNodeLv1ByBaseName(@Param("baseName") String baseName);

    List<BaseNode> queryNodeLv2BySuperId(@Param("superId") int superId);

    List<BaseNode> queryNodeLv3BySuperId(@Param("superId") int superId);

    FunctionPoint queryFunctionPointById(@Param("fid") int fid);

    List<com.tjm.pojo.base.Base> queryBaseByClass(@Param("baseClass") int baseClass);

    List<com.tjm.pojo.base.Base> queryBaseById(@Param("baseId") int baseId);

    List<com.tjm.pojo.base.Base> queryBaseByName(@Param("baseName") String baseName);

    int insertBase(com.tjm.pojo.base.Base base);

    int deleteBase(@Param("baseId") int baseId);

    List<com.tjm.pojo.base.Base> queryFunctionPointBase();


    List<BaseClass> queryAllBaseClass();

    BaseClass queryBaseClassByClassNum(@Param("classNum") int classNum);

    int insertBaseClass(BaseClass baseClass);

    int deleteBaseClassByClassNum(@Param("classNum") int classNum);


    List<BaseSecurity> querySecurityByBaseId(@Param("baseId") int baseId);

    List<BaseSecurity> querySecurityById(@Param("securityId") int securityId);

    List<BaseSecurity> querySecurityByLevel(@Param("securityLevel") String securityLevel, @Param("baseId") String baseId);

    int insertSecurity(BaseSecurity security);


    List<BaseArticle> queryArticleList();

    List<BaseArticle> queryArticleById(@Param("articleId") int articleId);

    List<BaseArticle> queryArticleByBaseId(@Param("baseId") int baseId);

    List<BaseArticle> queryArticleByName(@Param("articleName") String articleName, @Param("securityId") String securityId);

    int insertArticle(BaseArticle article);

    int deleteArticle(@Param("articleId") int articleId);


    List<BasePoint> queryPointList();

    List<BasePoint> queryPointById(@Param("pointId") int pointId);

    List<BasePoint> queryPointByArticleId(@Param("articleId") int articleId);

    List<BasePoint> queryPointByBaseId(@Param("baseId") int baseId);

    List<BasePoint> queryPointByName(@Param("pointName") String pointName, @Param("articleId") String articleId);

    int insertPoint(BasePoint point);

    int deletePoint(@Param("pointId") int pointId);


    List<BaseItem> queryItemList();

    List<BaseItem> queryItemById(@Param("itemId") int itemId);

    List<BaseItem> queryItemByPointId(@Param("pointId") int pointId);

    List<BaseItem> queryItemByBaseId(@Param("baseId") int baseId);

    List<BaseItem> queryItemByInput(@Param("dx") String dx, @Param("nr") String nr, @Param("aqjb") String aqjb);

    int insertItem(BaseItem item);

    int updateItem(BaseItem item);

    int deleteItem(@Param("itemId") int itemId);


    List<Indicator> query1stIndicatorByBaseName(@Param("baseName") String baseName);

    List<Indicator> query2ndIndicatorBySuperId(@Param("superId") int superId);

    List<Indicator> query3rdIndicatorBySuperId(@Param("superId") int superId);

    Indicator queryIndicatorById(@Param("indicatorId") int indicatorId);

    int updateIndicator(Indicator indicator);

    int insertIndicator(Indicator indicator);

    int deleteIndicatorById(@Param("indicatorId") int indicatorId);



    List<com.tjm.pojo.base.BaseNode> queryNodeLv3ByInput(@Param("lv1") String lv1, @Param("lv2") String lv2, @Param("lv3") String lv3);

    List<com.tjm.pojo.base.BaseNode> queryNodeLv2ByInput(@Param("lv1") String lv1, @Param("lv2") String lv2);

    com.tjm.pojo.base.BaseNode findSuperNodeByLv3(@Param("superId") int superId);

    com.tjm.pojo.base.BaseNode findSuperNodeByLv2(@Param("superId") int superId);

    List<com.tjm.pojo.base.BaseNode> queryNodeWithSameSuperId(@Param("level") String level, @Param("superId") int superId);

    com.tjm.pojo.base.BaseNode queryNodeByInfo(@Param("level") String level, @Param("infoTable") String infoTable, @Param("infoId") int infoId);

    com.tjm.pojo.base.BaseNode queryNodeById(@Param("level") String level, @Param("nodeId") int nodeId);

    com.tjm.pojo.base.BaseNode findNodeByNameAndSuperId(@Param("level") String level, @Param("nodeName") String nodeName, @Param("superId") int superId);

    int insertNode(@Param("level") String level, @Param("superId") int superId, @Param("nodeName") String nodeName, @Param("description") String description, @Param("leaf") int leaf, @Param("infoTable") String infoTable, @Param("infoId") int infoId);

    int deleteNodeById(@Param("level") String level, @Param("nodeId") int nodeId);


    int updateFunctionPoint(FunctionPoint functionPoint);

    int insertFunctionPoint(FunctionPoint functionPoint);

    int deleteFunctionPointById(@Param("fid") int fid);


    CheckItem queryCheckItemById(@Param("cid") int cid);

    int updateCheckItem(CheckItem checkItem);

    int insertCheckItem(CheckItem checkItem);

    int deleteCheckItemById(@Param("cid") int cid);


    List<BugLib> queryBugLibByBaseName(@Param("baseName") String baseName);

    BugLib queryBugLibById(@Param("bid") int bid);

    int updateBugLib(BugLib bugLib);

    int deleteBugLibById(@Param("bid") int bid);

    int insertBugLib(BugLib bugLib);
}
