package com.tjm.mapper;

import com.tjm.pojo.Project;
import com.tjm.pojo.Task_issued;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ProjectMapper {

    //查找所有角色
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
