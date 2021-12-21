package com.tjm.pojo.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseItem {
    private int item_id;
    private int point_id;
    private String point_name;
    private String article_name;
    private String base_name;
//    private String security_level;
    private String nr;
    private String dx;
    private String aqjb;
    private String ssbz;
    private String fxdj;
    private int sxh;
    private int zt = 0;

}
