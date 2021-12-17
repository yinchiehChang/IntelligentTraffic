package com.tjm.pojo.testCase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCase {

    //用例标识
    String case_id;
    //用例名称
    String case_name;
    //需求追踪
    String case_needsTrace;
    //测试说明
    String case_Description;
    //测试用例初始化
    String case_init;
    //终止条件
    String case_endCondition;
    //执行状态
    int case_status;
    //质量检测项目id
    int uid;
    //用例测试编号
    int testCase_id;
    //执行结果
    int case_result;
}
