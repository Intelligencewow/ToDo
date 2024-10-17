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
    private final String tasktext;

    @ColumnInfo(name = "is_completed")
    private boolean isCompleted;

    @ColumnInfo(name = "time_stamp")
    private long timeStamp;

    public Task(String tasktext){
        this.id = UUID.randomUUID().toString();
        this.tasktext = tasktext;
        this.isCompleted = false;
        this.timeStamp = System.currentTimeMillis();
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

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean completed) {
        isCompleted = completed;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
