package com.sara_nabil.student_management_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DB_helper extends SQLiteOpenHelper {
    private static final String DB_NAME = "student_info.db";
    private static final String TABLE_NAME = "student";
    private static final int DB_VERSION = 1;


    public DB_helper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE Table student(id INTEGER PRIMARY KEY," +
                "name TEXT,grade INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public boolean insert(int id, String name, int grade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("grade", grade);
        long r = db.insert(TABLE_NAME, null, values);
        if (r == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{Integer.toString(id)});
    }

    public void update(int id, String name, int grade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("grade", grade);
        db.update(TABLE_NAME, values, "id=?", new String[]{Integer.toString(id)});
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from student where id=?", new String[]{Integer.toString(id)});
        return cursor;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from student ", null);
        return cursor;
    }


}
