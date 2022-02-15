package com.example.sixthlaba;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.sixthlaba.domain.Task;

/**
 * @author anechaev
 * @since 22.01.2022
 */
public class TaskCursorWrapper extends CursorWrapper {
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Task getTask() {
        int id = Integer.parseInt(getString(getColumnIndex(Task.ID)));
        String name = getString(getColumnIndex(Task.NAME));
        String text = getString(getColumnIndex(Task.TEXT));
        int date = Integer.parseInt(getString(getColumnIndex(Task.DATE)));
        return new Task.Builder()
            .setId(id)
            .setName(name)
            .setText(text)
            .setDate(date).build();
    }


}
