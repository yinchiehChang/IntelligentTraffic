package com.tjm.service;

import com.tjm.pojo.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    int insertProduct(Product product);

    List<Product> queryProductList();
}
