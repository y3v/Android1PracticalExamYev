package com.example.yev.android1_practical_project_yev;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Yev on 2018-03-26.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "notes.db";
    public static final String TABLE_NAME = "notes";
    public static final String ID = "id";
    public static final String NOTE = "note";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_NAME + "(ID integer primary key autoincrement, note text)";
        Log.i("DATABASE:::", "CREATED");
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS" + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean insertNote(String note){
        boolean ret = false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE, note);
        long res = db.insert(TABLE_NAME, null, contentValues);

        if (res!=-1){
            ret=true;
        }

        return ret;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);

        return result;
    }

    public boolean deleteEntry(String note){
        boolean ret = false;

        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.delete(TABLE_NAME, "note = '" + note + "';", null);

        if (res!=-1){
            ret=true;
        }

        return ret;
    }

    public boolean updateEntry(String note, String newNote){
        boolean ret = false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(NOTE, newNote);
        long res = db.update(TABLE_NAME, content, "note ='" + note + "';", null);

        if (res!=-1){
            ret=true;
        }

        return ret;
    }
}
