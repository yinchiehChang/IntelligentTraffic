package com.tjm.service;

import com.tjm.mapper.TaskMapper;
import com.tjm.pojo.Task;
import com.tjm.pojo.Task_issued;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    TaskMapper taskMapper;

    @Override
    public int insertTask(Task task) {
        return taskMapper.insertTask(task);
    }


    @Override
    public Task findTaskById(int uid) {
        return taskMapper.findTaskById(uid);
    }

    @Override
    public int findByTaskId(String task_id) {
        return taskMapper.findByTaskId(task_id);
    }

    @Override
    public List<Task> findByRequired(Task task) {
        return taskMapper.findByRequired(task);
    }

    @Override
    public List<Task> findTask(Task task) {
        return taskMapper.findTask(task);
    }

    @Override
    public List<Task> selectTaskList() {
        return taskMapper.selectTaskList();
    }

    @Override
    public int delTask(int id) {
        return taskMapper.delTask(id);
    }

    @Override
    public int insertTaskIssued(Task_issued task_issued) {
        return taskMapper.insertTaskIssued(task_issued);
    }
}
