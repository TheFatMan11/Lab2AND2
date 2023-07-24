package com.thuydev.lab2and2.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper_lab2 extends SQLiteOpenHelper {
    static String nameDB = "TODOLIST";
    static int versionDB = 1;

    public DBHelper_lab2(Context context) {
        super(context, nameDB, null, versionDB);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tb_TOList = "CREATE TABLE tb_lichHoc (     Title   TEXT,     Content TEXT,     Date    TEXT,     Type    TEXT,     id_todo INTEGER PRIMARY KEY AUTOINCREMENT,trangthai INTEGER);";
        db.execSQL(tb_TOList);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table tb_lichHoc ");
    }
}
