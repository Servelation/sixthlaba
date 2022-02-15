package com.example.sixthlaba.domain;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.sixthlaba.Constants;
import com.example.sixthlaba.DbOperation;
import com.example.sixthlaba.TaskBaseHelper;
import com.example.sixthlaba.TaskCursorWrapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author anechaev
 * @since 22.01.2022
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskRepo {
    private Context context;
    private SQLiteDatabase database;

    public TaskRepo(Context context) {
        this.context = context;
        database = new TaskBaseHelper(context).getWritableDatabase();
    }

    public ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(Task.ID, task.getId());
        values.put(Task.NAME, task.getName());
        values.put(Task.TEXT, task.getText());
        values.put(Task.DATE, task.getDate().toEpochDay());
        return values;
    }

    public TaskRepo deleteTask(int id) {
        database.delete(Task.TABLE_NAME, Task.ID + "=" + id, null);
        return this;
    }

    public TaskRepo deleteAllTasks() {
        database.delete(Task.TABLE_NAME, null, null);
        return this;
    }

    public TaskRepo updateTask(@NonNull Task task) {
        ContentValues contentValues = getContentValues(task);
        database.update(Task.TABLE_NAME, contentValues, Task.ID + "=" + task.getId(), null);
        return this;
    }

    public TaskRepo addTask(@NonNull Task task) {
        ContentValues contentValues = getContentValues(task);
        database.insert(Task.TABLE_NAME, null, contentValues);
        return this;
    }

    private TaskCursorWrapper queryTasks(String whereClause, String[] wereArgs) {
        Cursor cursor = database.query(Task.TABLE_NAME, null, whereClause, wereArgs, null, null, null);
        return new TaskCursorWrapper(cursor);
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        try (TaskCursorWrapper cursor = queryTasks(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        }
        return tasks;
    }

    private final List<Task> tasks = new ArrayList<>(Arrays.asList(
        new Task(1,"ПРЕСС КАЧАТ", "3 подхода по 20 рас",
            LocalDate.of(2022, 2, 7)),
        new Task(2,"Т) БЕГИТ", "3 км",
            LocalDate.of(2022, 2, 8)),
        new Task(3,"ТУРНИК", "4 подхода по 12 раз",
            LocalDate.of(2022, 2, 12)),
        new Task(4,"АНЖУМАНЯ", "3 подхода по 50 раз",
            LocalDate.of(2022, 2, 10)),

        new Task(5,"ПРЕСС КАЧАТ", "4 подхода по 30 раз",
            LocalDate.of(2022, 2, 11)),
        new Task(6,"БЕГИТ", "2 подхода, каждый по 1 км",
            LocalDate.of(2022, 2, 12)),
        new Task(7,"ГАНТЕЛИ", "Тяжелые",
            LocalDate.of(2022, 2, 13))
    ));

    @NonNull
    public List<Task> applyFromIntent(Intent data) {
        String name = data.getStringExtra(Task.NAME);
        String text = data.getStringExtra(Task.TEXT);
        String dateStr = data.getStringExtra(Task.DATE);
        LocalDate date = LocalDate.parse(dateStr, Constants.DATE_FORMATTER);
        DbOperation operation = DbOperation.valueOf(data.getStringExtra(DbOperation.TYPE));
        Task.Builder taskBuilder = new Task.Builder()
            .setName(name)
            .setText(text)
            .setDate(date);
        int id;
        List<Task> resultTasks = new ArrayList<>();
        if (operation == DbOperation.ADD) {
            List<Task> tasks = getTasks();
            id = tasks.stream()
                .mapToInt(Task::getId)
                .max().orElse(0) + 1;
            Task task = taskBuilder.setId(id).build();
            addTask(task);
            tasks.add(task);
            return tasks;
        } else if (operation == DbOperation.UPDATE) {
            id = Integer.parseInt(data.getStringExtra(Task.ID));
            Task task = taskBuilder.setId(id).build();
            updateTask(task);
            resultTasks = getTasks();
        }
        return resultTasks;
    }
}
