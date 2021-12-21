package com.tjm.pojo.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseSecurity {
    private int security_id;
    private int base_id;
    private String security_level;
    private int zt;

    List<BaseArticle> articles;

    public int getSecurity_id() {
        return security_id;
    }

    public void setSecurity_id(int security_id) {
        this.security_id = security_id;
    }

    public int getBase_id() {
        return base_id;
    }

    public void setBase_id(int base_id) {
        this.base_id = base_id;
    }

    public String getSecurity_level() {
        return security_level;
    }

    public void setSecurity_level(String security_level) {
        this.security_level = security_level;
    }

    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }
}
