package com.tjm.service;

import com.tjm.pojo.testCase.TestCase;
import com.tjm.pojo.testCase.Test_Procedure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestCaseService {

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
}
