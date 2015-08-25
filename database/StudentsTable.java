package com.example.adri4n.databaseex1.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by adri4n on 17.03.2015.
 */
public class StudentsTable {

    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_GRADE = "grade";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_STUDENTS
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_EMAIL + " text not null, "
            + COLUMN_ADDRESS + " text not null, "
            + COLUMN_GRADE + " real not null);";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(StudentsTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }
}
