package com.tjm.pojo.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasePoint {
    private int point_id;
    private int article_id;
    private String point_name;
    private int zt;

    List<BaseItem> items;

    public int getPoint_id() {
        return point_id;
    }

    public void setPoint_id(int point_id) {
        this.point_id = point_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }
}
