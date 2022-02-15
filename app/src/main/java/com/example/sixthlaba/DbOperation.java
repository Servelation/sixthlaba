package com.example.sixthlaba;

import androidx.annotation.NonNull;

public enum DbOperation {
    ADD("addOperation"),
    UPDATE("updateOperation");

    public static final String TYPE = "dbOperationType";
    private final String arg;

    DbOperation(@NonNull String arg) {
        this.arg = arg;
    }

    public String getArg() {
        return arg;
    }
}
