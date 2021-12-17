package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    String uid;
    String product_name;
    String version;
    String platform_name;
    String contact_name;
    String product_comment;

}
