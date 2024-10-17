package com.example.todo;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> implements View.OnClickListener {
    private List<Task> taskList;
    private List<Task> filteredList;
    private Context context;
    private OnItemClickListerner listerner;

    public TaskAdapter(Context context, List<Task> taskList, OnItemClickListerner listerner) {
        this.taskList = taskList;
        this.filteredList = new ArrayList<>(taskList);
        this.context = context;
        this.listerner = listerner;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.taskTextView.setText(task.getTasktext());
        holder.checkBox.setChecked(false);
        if (task.getIsCompleted()) {
            holder.checkBox.setChecked(true);
            SpannableString spannableString = new SpannableString(task.getTasktext());
            spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), 0);
            holder.taskTextView.setText(spannableString);
        }

        holder.checkBox.setOnClickListener(v -> listerner.onItemClick(holder.getAdapterPosition(), 1));
        holder.itemView.setOnClickListener(v -> listerner.onItemClick(holder.getAdapterPosition(), 1));
        holder.deleteTaskButton.setOnClickListener(v -> listerner.onItemClick(holder.getAdapterPosition(), 0));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setTaskList(List<Task> taskList){
        this.taskList = taskList;
        notifyDataSetChanged();
    }


    public void filter(int id) {

        Log.i("Cherozo", "Tamanho da lista: " + filteredList.size());
        filteredList.clear();
        Log.i("Cherozo", "Tamanho da lista: " + filteredList.size());

        /*
        for (Task task : taskList) {

            if (id == 0) {

                if (!task.getIsCompleted()) {
                    Log.i("Cherozo", "Task: " + task.getTasktext() + " " + task.getIsCompleted());
                    filteredList.add(task);
                }

            } else if (id == 1) {
                if (task.getIsCompleted()) {
                    Log.i("Cherozo", "Task: " + task.getTasktext() + " " + task.getIsCompleted());
                    filteredList.add(task);
                }

            } else {
                Log.i("Cherozo", "Task: " + task.getTasktext() + " " + task.getIsCompleted());
                filteredList.add(task);
            }
        }
        notifyDataSetChanged();
        */
    }

    @Override
    public void onClick(View v) {

    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView taskTextView;
        public CheckBox checkBox;
        public Button deleteTaskButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.taskCheckBox);
            taskTextView = itemView.findViewById(R.id.taskTextView);
            deleteTaskButton = itemView.findViewById(R.id.taskDeleteButton);
        }
    }

    public interface OnItemClickListerner {
        void onItemClick(int position, int action);
    }
}
