package com.example.todo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;


@Entity(tableName = "task")
public class Task implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "description")
    private String tasktext;

    @ColumnInfo(name = "is_completed")
    private boolean IsCompleted;

    public Task(String tasktext){
        this.id = UUID.randomUUID().toString();
        this.tasktext = tasktext;
        this.IsCompleted = true;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTasktext() {
        return tasktext;
    }

    public void setTasktext(String tasktext) {
        this.tasktext = tasktext;
    }

    public boolean getIsCompleted() {
        return IsCompleted;
    }

    public void setIsCompleted(boolean completed) {
        IsCompleted = completed;
    }
}
