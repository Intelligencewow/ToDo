package com.example.todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private final TaskRepository taskRepository;
    private LiveData<List<Task>> taskList;


    public TaskViewModel(@NonNull Application application) {
        super(application);

        taskRepository = new TaskRepository(application);
        taskList = taskRepository.getAllTasks();
    }

    public void insertTask(Task task){
        taskRepository.insertTask(task);
    }

    public void updateTask(Task task) {taskRepository.updateTask(task);}

    public void deleteTask(Task task){
        taskRepository.deleteTask(task);
    }

    public LiveData<List<Task>> getTaskList() {
        return taskList;
    }

}
