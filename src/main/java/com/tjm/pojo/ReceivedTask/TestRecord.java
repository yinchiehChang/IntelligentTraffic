package com.tjm.pojo.ReceivedTask;

import com.tjm.config.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRecord {

    @ExcelColumn(value = "记录编号",col = 1)
    int record_id;
    @ExcelColumn(value = "检查任务编号",col = 2)
    String pointName;//检查任务编号对应,check_id
    int itemId;  //实施步骤对应的测试点id
    @ExcelColumn(value = "实施步骤",col = 3)
    String conSSBZ;  //具体实施步骤
    @ExcelColumn(value = "文字记录",col = 4)
    String text;  //存放实施步骤的文字记录
    String url;  //存放实施步骤的照片路径
    @ExcelColumn(value = "是否合格 0没及格 1及格",col = 5)
    int isSuitable;  //判断是否合格 0没及格 1及格
    @ExcelColumn(value = "实施场景",col = 6)
    String pointPlace;//实施场景
}
