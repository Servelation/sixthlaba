package com.example.sixthlaba;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.format.DateTimeFormatter;

/**
 * @author anechaev
 * @since 17.01.2022
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class Constants {
    private Constants() {
    }

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;
    public static final String RESULT_TYPE = "resultType";
    public static final String ADD = "add";
    public static final String TEXT_PLAIN = "text/plain";
}