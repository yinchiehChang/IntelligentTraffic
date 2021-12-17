package com.tjm.service;

import com.tjm.mapper.ProjectMapper;
import com.tjm.pojo.Project;
import com.tjm.pojo.Task_issued;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public List<String> queryPoint_names() {
        return projectMapper.queryPoint_names();
    }

    @Override
    public int addProject(Project project) {
        return projectMapper.addProject(project);
    }

    @Override
    public List<Project> queryProjectList() {
        return projectMapper.queryProjectList();
    }

    @Override
    public List<Task_issued> queryTask_issued() {
        return projectMapper.queryTask_issued();
    }

    @Override
    public int delProject(int id) {
        return projectMapper.delProject(id);
    }

    @Override
    public Project queryByID(int id) {
        return projectMapper.queryByID(id);
    }

    @Override
    public int editProject(Project project) {
        return projectMapper.editProject(project);
    }
//
//    @Override
//    public int addRecord(Test_record test_record) {
//        return projectMapper.addRecord(test_record);
//    }

//    @Override
//    public  List<Test_record> getAllRecords(int id) {
//        return projectMapper.getAllRecords(id);
//    }

//    @Override
//    public int editRecord(Test_record test_record) {
//        return projectMapper.editRecord(test_record);
//    }

    @Override
    public List<Task_issued> queryByAndroidName(String name) {
        return projectMapper.queryByAndroidName(name);
    }

    @Override
    public int updateStatus(Task_issued task_issued) {
        return projectMapper.updateStatus(task_issued);
    }
}
