package com.example.todo;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("SELECT * FROM task ORDER BY time_stamp DESC")
    LiveData<List<Task>> getAllTasks();

}
