package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Contact {

    String  uid;
    String contact_name;
    String pid;
    String boss_name;
    String boss_contact;
    String telephone;
    String fax;
    String post_code;
    String website;
    String email;
    String address;
//    int enterprise_id;
    String type;
    String contact_comment;
}
