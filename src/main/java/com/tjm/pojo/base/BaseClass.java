package com.tjm.pojo.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseClass {
    int class_num;
    String name;

    public int getClass_num() {
        return class_num;
    }

    public void setClass_num(int class_num) {
        this.class_num = class_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
