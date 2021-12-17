package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Enterprise {
    //企业id
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
