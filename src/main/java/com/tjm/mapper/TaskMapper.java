package com.tjm.mapper;

import com.tjm.pojo.Task;
import com.tjm.pojo.Task_issued;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TaskMapper {

    int insertTask(Task task);

    Task findTaskById(int uid);

    int findByTaskId(String task_id);

    List<Task> findByRequired(Task task);

    List<Task> findTask(Task task);

    List<Task> selectTaskList();

    int delTask(int id);

    int insertTaskIssued(Task_issued task_issued);
}
