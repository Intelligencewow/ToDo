package com.example.todo;

public class Task {

    private String tasktext;
    private boolean completed;

    public Task(String tasktext){
        this.tasktext = tasktext;
        this.completed = false;
    }

    public boolean getStatus() {
        return completed;
    }

    public void setStatus(boolean completed) {
        this.completed = completed;
    }

    public String getTasktext() {
        return tasktext;
    }

    public void setTasktext(String tasktext) {
        this.tasktext = tasktext;
    }
}
