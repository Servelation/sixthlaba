package com.example.sixthlaba;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sixthlaba.domain.Task;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

/**
 * @author anechaev
 * @since 17.01.2022
 */
public class ChangingActivity extends AppCompatActivity {
    private DatePickerDialog picker;
    private EditText editName;
    private EditText editText;
    private EditText editDate;
    private TextView dateTitle;
    private Button saveButton;
    private DbOperation operation;
    @Nullable private Integer id;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changing);
        Intent intent = getIntent();
        operation = DbOperation.valueOf(intent.getStringExtra(DbOperation.TYPE));
        String idStr = intent.getStringExtra(Task.ID);
        id = idStr == null ? null : Integer.parseInt(idStr);
        editName = findViewById(R.id.editTextTaskName);
        editText = findViewById(R.id.editTextTaskText);
        editDate = findViewById(R.id.editTextTaskDate);
        editDate.setOnClickListener(this::dateListener);

        dateTitle = findViewById(R.id.textViewDate);
        dateTitle.setOnClickListener(this::dateListener);

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            if (!isInputValid()) {
                return;
            }
            Intent resIntent = new Intent();
            resIntent.putExtra(DbOperation.TYPE, operation.name());
            resIntent.putExtra(Task.NAME, editName.getText().toString());
            resIntent.putExtra(Task.TEXT, editText.getText().toString());
            resIntent.putExtra(Task.DATE, editDate.getText().toString());
            if (operation == DbOperation.UPDATE) {
                resIntent.putExtra(Task.ID, Integer.toString(id));
            }
            setResult(RESULT_OK, resIntent);
            finish();
        });
        if (operation == DbOperation.UPDATE) {
            String name = intent.getStringExtra(Task.NAME);
            editName.setText(name);

            String text = intent.getStringExtra(Task.TEXT);
            editText.setText(text);

            String date = intent.getStringExtra(Task.DATE);
            editDate.setText(date);
        }
    }
    @SuppressLint("SetTextI18n")
    private void dateListener(View v) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(ChangingActivity.this,
            this::setDateText,
            year, month, day);
        picker.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDateText(View view, int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month + 1, day);
        editDate.setText(date.format(Constants.DATE_FORMATTER));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isInputValid() {
        if ("".equals(editName.getText().toString().trim())) {
            Toast.makeText(this, "Please, enter the task name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ("".equals(editText.getText().toString().trim())) {
            Toast.makeText(this, "Please, enter the task text", Toast.LENGTH_SHORT).show();
            return false;
        }
        String dateText = editDate.getText().toString();
        if ("".equals(dateText.trim())) {
            Toast.makeText(this, "Please, enter the task date", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            LocalDate.parse(dateText, Constants.DATE_FORMATTER);
        } catch (Exception e) {
            Toast.makeText(this, "The date should be in ISO format", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}