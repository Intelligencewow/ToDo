package com.example.todo;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnItemClickListerner {

    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;
    TaskViewModel taskViewModel;
    private EditText editText;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.purple));
        }

        ChipGroup chipGroup = findViewById(R.id.chipGroup);

        fab = findViewById(R.id.createTask);
        editText = findViewById(R.id.etTask);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskAdapter = new TaskAdapter(this, taskList, this);
        recyclerView.setAdapter(taskAdapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getTaskList().observe(this, tasks -> {
            taskList = tasks;
            taskAdapter.setTaskList(tasks);
            Log.d("MainActivity", "Total de produtos: " + tasks.size());
        });

        fab.setOnClickListener(v -> {
            String text = editText.getText().toString().trim();
            if (!text.isEmpty()) {
                taskViewModel.insertTask(new Task(text));
                taskAdapter.notifyItemInserted(taskList.size() - 1);
                editText.setText("");
                //Toast.makeText(this, "Tarefa adicionada !!!", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(this, "Você tentou adicionar uma tarefa vazia", Toast.LENGTH_SHORT).show();
        });

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                String text = editText.getText().toString().trim();
                if (!text.isEmpty()) {
                    taskViewModel.insertTask(new Task(text));
                    taskAdapter.notifyItemInserted(taskList.size() - 1);
                    editText.setText("");
                    Toast.makeText(this, "Tarefa adicionada !!!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                Toast.makeText(this, "Você tentou adicionar uma tarefa vazia", Toast.LENGTH_SHORT).show();

            }
            return false;
        });

        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            int selectedChipId = checkedIds.get(0);
            Chip selectedChip = findViewById(selectedChipId);

            if (selectedChip.getText().equals("In progress")) {
                taskAdapter.filter(0);

            } else if (selectedChip.getText().equals("Done")) {
                taskAdapter.filter(1);
            } else {
                taskAdapter.filter(2);
            }
            Toast.makeText(this, "Selecionado: " + selectedChip.getText(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onItemClick(int position, int action) {

        Task task = taskList.get(position);
        if (action == 1) {

            task.setIsCompleted(!task.getIsCompleted());
            Toast.makeText(this, "Cliquei no : " + task.getTasktext(), Toast.LENGTH_SHORT).show();
            taskAdapter.notifyItemChanged(position);

        } else {
            Toast.makeText(this,"ERA PRA DELETAR", Toast.LENGTH_SHORT).show();
            taskAdapter.notifyItemRemoved(position);
            taskViewModel.deleteTask(task);
        }
    }
}