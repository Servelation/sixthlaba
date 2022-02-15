package com.example.sixthlaba;

import static java.util.Objects.requireNonNull;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sixthlaba.domain.Task;
import com.example.sixthlaba.domain.TaskRepo;

import java.util.List;

/**
 * @author anechaev
 * @since 22.01.2022
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private final Context context;
    private final TaskRepo taskRepo;
    private final ActivityAction action;
    private List<Task> tasks;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TaskAdapter(@NonNull Context context, @NonNull TaskRepo taskRepo, @NonNull ActivityAction action) {
        this.context = requireNonNull(context, "context");
        this.taskRepo = requireNonNull(taskRepo, "taskRepo");
        this.action = requireNonNull(action, "action");
        tasks = taskRepo.getTasks();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private final View.OnClickListener getRemoveListener(int position) {
        return v -> {
            Task taskToRemove = tasks.get(position);
            taskRepo.deleteTask(taskToRemove.getId());
            tasks.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, tasks.size());
        };
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new TaskViewHolder(view, context, action);
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bindTask(task, getRemoveListener(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

}
