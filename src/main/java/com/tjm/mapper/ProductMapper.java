package com.tjm.mapper;

import com.tjm.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProductMapper {

    int insertProduct(Product product);

    List<Product> queryProductList();
}
