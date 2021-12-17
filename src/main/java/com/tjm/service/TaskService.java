package com.tjm.service;

import com.tjm.pojo.Task;
import com.tjm.pojo.Task_issued;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    int insertTask(Task task);

    Task findTaskById(int uid);

    int findByTaskId(String task_id);

    List<Task> findByRequired(Task task);

    List<Task> findTask(Task task);

    /**
     * 查询任务数据
     *
     * @return 任务信息集合
     */
     List<Task> selectTaskList();

     int delTask(int id);

     //将下发的任务插入数据库中
     int insertTaskIssued(Task_issued task_issued);
}
