package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Platform {

    String uid;
    String platform_name;
    String network;
    String status;
    String info;
    String platform_comment;
    String interface_sensitive;
}
