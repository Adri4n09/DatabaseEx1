package com.example.adri4n.databaseex1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adri4n on 17.03.2015.
 */
public class StudentDatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;

    public StudentDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StudentsTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        StudentsTable.onUpgrade(db,oldVersion, newVersion);

    }
}
