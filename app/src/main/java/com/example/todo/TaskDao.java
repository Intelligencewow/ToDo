package com.example.todo;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllTasks();
}
