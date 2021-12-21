package com.tjm.pojo.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Base {
    private int base_id;
    private String base_name;
    private int zt;
    private int base_class;
    private int base_type;

   List<BaseSecurity> securities;

    public int getBase_id() {
        return base_id;
    }

    public String getBase_name() {
        return base_name;
    }

    public int getZt() {
        return zt;
    }

    public void setBase_id(int base_id) {
        this.base_id = base_id;
    }

    public void setBase_name(String base_name) {
        this.base_name = base_name;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    public int getBase_class() {
        return base_class;
    }

    public void setBase_class(int base_class) {
        this.base_class = base_class;
    }

    public int getBase_type() {
        return base_type;
    }

    public void setBase_type(int base_type) {
        this.base_type = base_type;
    }
}
