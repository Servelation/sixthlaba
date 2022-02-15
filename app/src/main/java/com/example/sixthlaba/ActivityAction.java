package com.example.sixthlaba;

import android.annotation.SuppressLint;
import android.content.Intent;

/**
 * @author anechaev
 * @since 07.02.2022
 */
public interface ActivityAction {

    void apply( Intent intent, int requestCode);
}
