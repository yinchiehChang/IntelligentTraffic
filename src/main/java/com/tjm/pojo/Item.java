package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    int item_id;
    int point_id;
    String aqjb;
    String nr;
    String dx;
    String ssbz;
    String fxdj;
    int sxh;
    int zt;
}
