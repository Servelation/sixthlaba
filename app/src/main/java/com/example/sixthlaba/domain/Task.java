package com.example.sixthlaba.domain;

import static java.util.Objects.requireNonNull;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author anechaev
 * @since 22.01.2022
 */
public class Task {
    public static final String TABLE_NAME = "tasks";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TEXT = "text";
    public static final String DATE = "date";

    private final int id;
    private final String name;
    private final String text;
    private final LocalDate date;

    public Task(int id, @NonNull String name, @NonNull String text, @NonNull LocalDate date) {
        this.id = id;
        this.name = requireNonNull(name, "name");
        this.text = requireNonNull(text, "text");
        this.date = requireNonNull(date, "date");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public LocalDate getDate() {
        return date;
    }


    public static class Builder {
        private int id;
        private String name;
        private String text;
        private LocalDate date;

        public Builder() {}

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public Builder setDate(int epochDay) {
            this.date = LocalDate.ofEpochDay(epochDay);
            return this;
        }

        public Task build() {
            return new Task(id, name, text, date);
        }
    }
}
