package com.tjm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task_issued {

    //检查任务编号
    String check_id;
    //安全等级
    String security_level;
    //委托单位名
    String unit_name;
    //联系人姓名
    String contact_name;
    //联系人电话
    String boss_contact;
    //接入类型
    String platform_name;
    //数据库及版本
    String database;
    //系统框架
    String architecture;
    //软件版本
    String version;
    //软件名称
    String software_name;
    //安卓端用户名
    String name;
    //关联项目id
    int project_id;
    //查看该任务是否已下发给安卓端
    int check_status;

    List<Issued_item> items;
}
