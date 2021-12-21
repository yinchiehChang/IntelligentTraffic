package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record_item {
    //测试项记录编号
    int record_itemNum;
    //测试项内容
    String nr;
    //测试项id
    int item_id;
    //测试项状态
    int item_status;
    //检查任务编号
    String check_id;
}
