package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Present_record {

    //检查任务编号
    String check_id;
    //安全等级
    String security_level;
    //委托单位名
    String unit_name;
    //软件名称
    String software_name;
    //安卓端用户名
    String name;
    //测试项完全合格数
    int qualifiedNum;
    //测试项部分合格数
    int midqualifiedNum;
    //测试项不合格数
    int noqualifiedNum;
}
