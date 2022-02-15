package com.example.sixthlaba;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sixthlaba.domain.Task;
import com.example.sixthlaba.domain.TaskRepo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.List;


/**
 * @author anechaev
 * @since 22.01.2022
 */
public class MainActivity extends AppCompatActivity {

    private FloatingActionButton actionButton;
    private RecyclerView recyclerView;
    private TaskRepo taskRepo;
    private TaskAdapter taskAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskRepo = new TaskRepo(this);
        updateAdapter();

        actionButton = findViewById(R.id.floatingActionButton);
        actionButton.setOnClickListener(e -> {
            Intent intent = new Intent(this, ChangingActivity.class);
            intent.putExtra(DbOperation.TYPE, DbOperation.ADD.name());
            startActivityForResult(intent, 1);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateAdapter() {
        taskAdapter = new TaskAdapter(this, taskRepo, this::startActivityForResult);
        recyclerView.setAdapter(taskAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        List<Task> tasks = taskRepo.applyFromIntent(data);
        taskAdapter.setTasks(tasks);
        taskAdapter.notifyDataSetChanged();
    }
}