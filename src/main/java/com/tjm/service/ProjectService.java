package com.tjm.service;

import com.tjm.pojo.Project;
import com.tjm.pojo.Task_issued;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {

    //查找所有测试点
    List<String> queryPoint_names();

    int addProject(Project project);

    List<Project> queryProjectList();

    List<Task_issued> queryTask_issued();

    int delProject(int id);

    Project queryByID(int id);

    int editProject(Project project);
//
//    int addRecord(Test_record test_record);

//    List<Test_record> getAllRecords(int id);

//    int editRecord(Test_record test_record);

    List<Task_issued> queryByAndroidName(String name);

    int updateStatus(Task_issued task_issued);
}
