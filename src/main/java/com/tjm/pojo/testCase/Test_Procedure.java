package com.tjm.pojo.testCase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test_Procedure {

    //测试过程序号
    int uid ;
    //测试过程步骤
    String steps;
    //预期结果
    String exp_result;
    //评估准则
    String evaluation;
    //实际结果
    String act_result;
    //执行结果
    String exe_result;
    //用例测试编号
    int testCase_id;
}
