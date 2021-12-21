package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record_point {
    //测试点记录编号
    int record_pointNum;
    //测试点名
    String point_name;
    //测试点id
    int point_id;
    //测试点状态
    int point_status;
    //检查任务编号
    String check_id;
}
