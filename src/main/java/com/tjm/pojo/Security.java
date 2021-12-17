package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Security {

    int security_id;
    int base_id;
    String security_level;
    int zt;

    List<Article> articles;
}
