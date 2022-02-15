package com.example.sixthlaba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sixthlaba.domain.Task;

/**
 * @author anechaev
 * @since 22.01.2022
 */
public class TaskBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "tasksBase1.db";

    public TaskBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Task.TABLE_NAME + " (" +
            "_id integer primary key autoincrement, " +
            Task.ID + ", " +
            Task.NAME + ", " +
            Task.TEXT + ", " +
            Task.DATE + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Task.TABLE_NAME);
        onCreate(db);
    }


}
