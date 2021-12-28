package com.tjm.service;

import com.tjm.mapper.TestCaseMapper;
import com.tjm.pojo.testCase.TestCase;
import com.tjm.pojo.testCase.Test_Procedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseServiceImpl implements TestCaseService {

    @Autowired
    TestCaseMapper testCaseMapper;

    @Override
    public int insertTestCase(TestCase testCase) {
        return testCaseMapper.insertTestCase(testCase);
    }

    @Override
    public int insertTestProcedure(Test_Procedure test_procedure) {
        return testCaseMapper.insertTestProcedure(test_procedure);
    }

    @Override
    public List<TestCase> findTestCases(int uid) {
        return testCaseMapper.findTestCases(uid);
    }

    @Override
    public TestCase findByCaseId(int testCase_id) {
        return testCaseMapper.findByCaseId(testCase_id);
    }

    @Override
    public int delCases(int testCase_id) {
        return testCaseMapper.delCases(testCase_id);
    }

    @Override
    public int editTestCase(TestCase testCase) {
        return testCaseMapper.editTestCase(testCase);
    }

    @Override
    public int editTestProcedure(Test_Procedure test_procedure) {
        return testCaseMapper.editTestProcedure(test_procedure);
    }

    @Override
    public int delTestProcedure(int testCase_id) {
        return testCaseMapper.delTestProcedure(testCase_id);
    }

    @Override
    public List<TestCase> findAllCases(int uid) {
        return testCaseMapper.findAllCases(uid);
    }

    @Override
    public List<Test_Procedure> queryProcedure(int testCase_id) {
        return testCaseMapper.queryProcedure(testCase_id);
    }

    @Override
    public int updateStatus(int testCase_id,int status) {
        return testCaseMapper.updateStatus(testCase_id,status);
    }

    @Override
    public List<Integer> queryQualityIds() {
        return testCaseMapper.queryQualityIds();
    }

    @Override
    public int countCases(int uid) {
        return testCaseMapper.countCases(uid);
    }

    @Override
    public int countPassCases(int uid) {
        return testCaseMapper.countPassCases(uid);
    }

    @Override
    public List<TestCase> find_RequiredCase(String case_name, String identification,int uid) {
        return testCaseMapper.find_RequiredCase(case_name,identification,uid);
    }
}
