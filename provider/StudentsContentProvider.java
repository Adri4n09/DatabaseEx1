package com.example.adri4n.databaseex1.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.example.adri4n.databaseex1.database.StudentDatabaseHelper;
import com.example.adri4n.databaseex1.database.StudentsTable;
import com.example.adri4n.databaseex1.model.Student;

import java.sql.SQLException;

/**
 * Created by adri4n on 17.03.2015.
 */
public class StudentsContentProvider extends ContentProvider {

    private StudentDatabaseHelper studentDatabaseHelper;

    // Create the constants used to differentiate between the different URI
    // requests.
    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;

    private static final String AUTHORITY = "com.example.adri4n.databaseex1.provider";
    private static final String BASE_PATH = "students";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, BASE_PATH, ALLROWS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", SINGLE_ROW);
    }

    @Override
    public boolean onCreate() {

        studentDatabaseHelper = new StudentDatabaseHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db;

        try {
            db = studentDatabaseHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = studentDatabaseHelper.getReadableDatabase();
        }

        //Replace these with valid sql statments if necessary.
        String groupBy = null;
        String having = null;

        // Use an SQLite Query Builder to simplify constructing the
        // database query.
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        //If this is a row query, limit the result set to the passed in row.
        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(StudentsTable.COLUMN_ID + "=" + rowID);
            default:
                break;
        }

        // Specify the table on which to perform the query. This can
        // be a specific table or a join as required.
        queryBuilder.setTables(StudentsTable.TABLE_STUDENTS);

        // Execute query.
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);

        return cursor;

    }

    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        //open DB
        SQLiteDatabase db = studentDatabaseHelper.getWritableDatabase();

        //To add empty rows to your database by passing in an empty
        //Content Values object you must use the null column hack
        //parameter to specify the name of the column that can be
        //set to null.
        String nullColumnHack = null;

        //Insert the values into the table
        long id = db.insert(StudentsTable.TABLE_STUDENTS, nullColumnHack, values);

        //Consturct and return the URI of the newly inserted row.
        if (id > -1) {
            // Construct and return the URI of the newly inserted row.
            Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
            // Notify any observers of the change in the data set.
            getContext().getContentResolver().notifyChange(insertedId, null);
            return insertedId;
        } else
            return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //open DB
        SQLiteDatabase db = studentDatabaseHelper.getWritableDatabase();

        // If this is a row URI, limit the deletion to the specifide row.
        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                selection = StudentsTable.COLUMN_ID + "=" + rowID + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
            default:break;
        }

        // To return the number of deleted items you must specify a where
        // clause. To delete all rows and return a value pass in "1".
        if (selection == null)
            selection = "1";

        // Perform the deletion.
        int deleteCount = db.delete(StudentsTable.TABLE_STUDENTS, selection, selectionArgs);

        // Notify any obserers
        getContext().getContentResolver().notifyChange(uri, null);

        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //open DB
        SQLiteDatabase db = studentDatabaseHelper.getWritableDatabase();

        // If this is a row URI, limit the deletion to the specifide row.
        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:
                String rowID = uri.getPathSegments().get(1);
                selection = StudentsTable.COLUMN_ID + "=" + rowID + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
            default:break;
        }

        //Perform the update.
        int updateCount = db.update(StudentsTable.TABLE_STUDENTS, values, selection, selectionArgs);

        // Notify any obserers
        getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;

    }
}
