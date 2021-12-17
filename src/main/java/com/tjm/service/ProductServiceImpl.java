package com.tjm.service;

import com.tjm.mapper.ProductMapper;
import com.tjm.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductMapper productMapper;

    @Override
    public int insertProduct(Product product) {
        return productMapper.insertProduct(product);
    }

    @Override
    public List<Product> queryProductList() {
        return productMapper.queryProductList();
    }
}
