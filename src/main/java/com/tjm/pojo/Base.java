package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Base {

    int base_id;
    String base_name;
    int base_class;
    int zt;

    List<Security> securities;
}


