package com.example.todo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnItemClickListerner {

    private List<Task> taskList = new ArrayList<>();
    TaskViewModel taskViewModel;
    private int currentFilter = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TaskAdapter taskAdapter;
        EditText editText;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.purple));


        ChipGroup chipGroup = findViewById(R.id.chipGroup);
        TextInputLayout inputLayout = findViewById(R.id.textInputLayout);
        FloatingActionButton fab = findViewById(R.id.createTask);
        editText = findViewById(R.id.etTask);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EditText etFilter = findViewById(R.id.etFilter);

        taskAdapter = new TaskAdapter(this, taskList, this);
        recyclerView.setAdapter(taskAdapter);





        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getTaskList().observe(this, tasks -> {
            taskList = tasks;
            taskAdapter.setTaskList(tasks);
            Log.d("MainActivity", "Total de produtos: " + tasks.size());
        });

        inputLayout.setStartIconOnClickListener(v -> {
            String text = etFilter.getText().toString().trim();
            if (!text.isEmpty()){
                taskAdapter.filter(currentFilter, text);
                etFilter.setText("");
            }
            hideKeyboad(v);
        });

        etFilter.setOnEditorActionListener((v, actionId, event) -> {
            String text = etFilter.getText().toString().trim();
            if (!text.isEmpty()){
                taskAdapter.filter(currentFilter, text);
                etFilter.setText("");
                hideKeyboad(v);
                return true;
            }
            hideKeyboad(v);
            return false;
        });

        fab.setOnClickListener(v -> {
            String text = editText.getText().toString().trim();
            if (!text.isEmpty()) {
                taskViewModel.insertTask(new Task(text));
                taskAdapter.filter(currentFilter, "");
                editText.setText("");
                hideKeyboad(v);
                Toast.makeText(this, "Tarefa adicionada !!!", Toast.LENGTH_SHORT).show();
            }
        });

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                String text = editText.getText().toString().trim();
                if (!text.isEmpty()) {
                    taskViewModel.insertTask(new Task(text));
                    taskAdapter.filter(currentFilter, "");
                    editText.setText("");
                    hideKeyboad(v);
                    Toast.makeText(this, "Tarefa adicionada !!!", Toast.LENGTH_SHORT).show();
                    return true;
                }

            }
            return false;
        });

        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            int selectedChipId = checkedIds.get(0);
            Chip selectedChip = findViewById(selectedChipId);

            if (selectedChip.getText().equals("In progress")) {
                currentFilter = 0;
                taskAdapter.filter(currentFilter, "");

            } else if (selectedChip.getText().equals("Done")) {
                currentFilter = 1;
                taskAdapter.filter(currentFilter, "");
            } else {
                currentFilter = 2;
                taskAdapter.filter(currentFilter, "");
            }
        });
    }

    @Override
    public void onItemClick(int position, int action) {

        Task task = taskList.get(position);
        if (action == 1) {

            task.setIsCompleted(!task.getIsCompleted());
            taskViewModel.updateTask(task);

        } else {
            Toast.makeText(this, "Tarefa removida com sucesso!" , Toast.LENGTH_SHORT).show();
            taskViewModel.deleteTask(task);
        }

        }

    public void hideKeyboad(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    }