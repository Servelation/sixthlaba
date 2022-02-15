package com.example.sixthlaba;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sixthlaba.domain.Task;


/**
 * @author anechaev
 * @since 22.01.2022
 */
public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView taskName;
    private TextView taskDate;
    private CheckBox checkBox;
    private Context context;
    private Task task;
    private ActivityAction action;

    TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        taskName = itemView.findViewById(R.id.taskName);
        taskDate = itemView.findViewById(R.id.taskDate);
        checkBox = itemView.findViewById(R.id.checkBox);
    }

    public TaskViewHolder(@NonNull View itemView, @NonNull Context context, @NonNull ActivityAction action) {
        this(itemView);
        this.context = context;
        this.action = action;
        itemView.setOnClickListener(this);
    }

    public void bindTask(Task task, View.OnClickListener listener) {
        this.task = task;
        taskName.setText(task.getName());
        taskDate.setText(task.getDate().toString());
        checkBox.setOnClickListener(listener);
        checkBox.setChecked(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, ChangingActivity.class);
        intent.putExtra(Task.ID, Integer.toString(task.getId()));
        intent.putExtra(Task.NAME, task.getName());
        intent.putExtra(Task.TEXT, task.getText());
        intent.putExtra(Task.DATE, task.getDate().format(Constants.DATE_FORMATTER));
        intent.putExtra(DbOperation.TYPE, DbOperation.UPDATE.name());
        action.apply(intent, 1);
    }


}
