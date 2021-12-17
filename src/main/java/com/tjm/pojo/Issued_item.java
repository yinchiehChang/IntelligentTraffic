package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Issued_item {

    int item_id;
    String point_name;
    String aqjb;
    String nr;
    String dx;
    String ssbz;
    String fxdj;
    int sxh;
    int zt;
}
