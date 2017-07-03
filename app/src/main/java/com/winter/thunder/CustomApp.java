package com.winter.thunder;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.winter.thunder.task.DatabaseFormTask;

public class CustomApp extends Application {

    public static SQLiteDatabase  db;

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
