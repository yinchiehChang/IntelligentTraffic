package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    int article_id;
    String article_name;
    int security_id;
    int zt;


    List<Point> points;
}
