package com.tjm.mapper;

import com.tjm.pojo.testCase.TestCase;
import com.tjm.pojo.testCase.Test_Procedure;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface TestCaseMapper {

    int insertTestCase(TestCase testCase);

    int insertTestProcedure(Test_Procedure test_procedure);

    List<TestCase> findTestCases(int uid);

    TestCase findByCaseId(int testCase_id);

    int delCases(int testCase_id);

    int editTestCase(TestCase testCase);

    int editTestProcedure(Test_Procedure test_procedure);

    int delTestProcedure(int testCase_id);

    List<TestCase> findAllCases(int uid);

    List<Test_Procedure> queryProcedure(int testCase_id);

    int updateStatus(int testCase_id,int status);

    List<Integer> queryQualityIds();

    int countCases(int uid);

    int countPassCases(int uid);

    List<TestCase> find_RequiredCase(@Param("case_name")String case_name, @Param("identification")String identification,int uid);
}
