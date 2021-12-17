package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    int point_id;
    int article_id;
    String point_name;
    int zt;

    List<Item> items;
}



