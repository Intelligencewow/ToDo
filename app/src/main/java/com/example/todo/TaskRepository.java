package com.example.todo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private TaskDao taskDao;
    private ExecutorService executor;

    public TaskRepository(Application application){
        TaskDatabase db = TaskDatabase.getDatabase(application);
        taskDao = db.taskDao();
        executor = Executors.newSingleThreadExecutor();
    }

    public void insertTask(final Task task){
        executor.execute(() -> taskDao.insertTask(task));
    }

    public void deleteTask(final Task task){
        executor.execute(() -> taskDao.deleteTask(task));
    }

    public LiveData<List<Task>> getAllTasks(){
        return taskDao.getAllTasks();
    }

}
