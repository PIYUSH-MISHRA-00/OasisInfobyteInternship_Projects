package com.example.todoapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TodoApp.db";
    private static final int DATABASE_VERSION = 1;

    // User authentication table
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    // Todo tasks table
    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK = "task";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the user authentication table
        db.execSQL("CREATE TABLE " + TABLE_USERS +
                " (" + COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT)");

        // Create the todo tasks table
        db.execSQL("CREATE TABLE " + TABLE_TASKS +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade logic if needed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // User authentication methods
    public boolean insertUserData(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_EMAIL};
        String selection = COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Todo tasks methods
    public void insertTask(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, task);
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<String> getAllTasks() {
        List<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TASKS, null);
        if (cursor.moveToFirst()) {
            do {
                taskList.add(cursor.getString(cursor.getColumnIndex(COLUMN_TASK)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taskList;
    }

    public boolean deleteTask(String taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_TASKS, COLUMN_ID + "=?", new String[]{String.valueOf(taskId)});
        db.close();
        return rowsDeleted > 0;
    }

    public boolean updateTask(String taskId, String newTask) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, newTask);
        int rowsUpdated = db.update(TABLE_TASKS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(taskId)});
        db.close();
        return rowsUpdated > 0;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_EMAIL};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    public boolean insertData(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, contentValues);
        db.close();
        return result != -1;
    }
}
