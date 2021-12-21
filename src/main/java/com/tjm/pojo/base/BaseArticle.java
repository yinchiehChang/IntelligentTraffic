package com.tjm.pojo.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseArticle {
    private int article_id;
    private int security_id;
    private String article_name;
    private int zt;

    List<BasePoint> points;

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public int getSecurity_id() {
        return security_id;
    }

    public void setSecurity_id(int security_id) {
        this.security_id = security_id;
    }

    public String getArticle_name() {
        return article_name;
    }

    public void setArticle_name(String article_name) {
        this.article_name = article_name;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }
}
