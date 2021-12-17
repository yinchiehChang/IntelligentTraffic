package com.tjm.mapper;

import com.tjm.pojo.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BaseMapper {

    //知识库
    @Select("select base_id,base_class,zt,base_name as name from BASE")
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
    List<Security> querySecurity(int base_id);

    //项目
    @Select("select article_id,zt,security_id,article_name as name from ARTICLE where security_id=#{security_id}")
    @Results({
            @Result(property = "article_id",column = "article_id"),
            @Result(property = "article_name",column = "name"),
            @Result(property = "zt",column = "zt"),
            @Result(property = "security_id",column = "security_id"),
            @Result(property = "points",column = "article_id",javaType = List.class,many = @Many(select = "queryPoint"))
    })
    List<Article> queryArticle(int security_id);

    //检测点
    @Select("select point_id as id,zt,article_id,point_name as name from POINT where article_id=#{article_id}")
    @Results({
            @Result(property = "point_id",column = "id"),
            @Result(property = "point_name",column = "name"),
            @Result(property = "zt",column = "zt"),
            @Result(property = "article_id",column = "article_id"),
            @Result(property = "items",column = "id",javaType = List.class,many = @Many(select = "queryItem"))
    })
    List<Point> queryPoint(int article_id);

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
    List<Item> queryItem(int id);

    List<Issued_item> addPointName(int id);

    String findSecutiryLevelByPointid(int id);

    String  queryByPointId(int id);

    List<BaseNode> queryNodeLv1ByBaseName(@Param("baseName") String baseName);

    List<BaseNode> queryNodeLv2BySuperId(@Param("superId") int superId);

    List<BaseNode> queryNodeLv3BySuperId(@Param("superId") int superId);

    FunctionPoint queryFunctionPointById(@Param("fid") int fid);
}
