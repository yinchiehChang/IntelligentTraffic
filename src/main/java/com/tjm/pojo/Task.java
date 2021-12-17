package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {

    int uid;
    String task_id;
    String client;
    String worker;
    // Platform
    String platform_uid;
    String platform_name;
    String network;
    String status;
    String info;
    String platform_comment;
    String interface_sensitive;
    //Product
    String product_uid;
    String product_name;
    String version;
    String product_comment;

    //contact
    String contact_uid;
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
    String type;
    String contact_comment;

    //Enterprise
    String enterprise_uid;
    String name;
    String cancel_date;
    String reg_status;
    String reg_capital;
    String city;
    String staff_num_range;
    String bond_num;
    String industry;
    String bond_name;
    String revoke_date;
    String legal_type;
    String update_times;
    String legal_person_name;
    String comment;
    String bondType;
    String creditCode;
}
