package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Document_audit {
    String uid;
    String start_date;
    String finish_date;
    String auditor;
    String audit_info;
    int result;
    String create_date;
    int task_uid;
}
