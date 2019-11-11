package com.rysl.unigradejav.src.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "unigrade.db";
    public static final String TABLE_NAME1 = "subject";
    public static final String TABLE_NAME2 = "link_sub_mod";
    public static final String TABLE_NAME3 = "module";
    public static final String TABLE_NAME4 = "link_mod_asg";
    public static final String TABLE_NAME5 = "assignment";
    Context context;
    SQLiteDatabase database;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        this.database = this.getWritableDatabase();
        //onUpgrade(this.getWritableDatabase(), 1, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME1 + " (ID INTEGER PRIMARY KEY NOT NULL, NAME TEXT NOT NULL)");
        db.execSQL("create table " + TABLE_NAME2 + " (subID INTEGER NOT NULL, modID INTEGER NOT NULL)");
        db.execSQL("create table " + TABLE_NAME3 + " (ID INTEGER PRIMARY KEY NOT NULL, NAME TEXT NOT NULL)");
        db.execSQL("create table " + TABLE_NAME4 + " (modID NOT NULL, asgID NOT NULL)");
        db.execSQL("create table " + TABLE_NAME5 + " (ID INTEGER PRIMARY KEY NOT NULL, NAME TEXT NOT NULL, PERCENTAGE INTEGER NOT NULL, TYPE BOOLEAN NOT NULL, RESULT INTEGER, DESCRIPTION TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);
        onCreate(db);
    }

    public void insertData(String query){
        System.out.println(query);
        this.database.execSQL(query);
    }

    public Cursor getData(String query){
        System.out.println(query);
        Cursor res = this.database.rawQuery(query, null);
        return res;
    }

    public void deleteData(String query){
        System.out.println(query);
        this.database.execSQL(query);
    }

    public int getMaxKey(String query){
        int key = 0;
        Cursor result = this.database.rawQuery(query, null);
        result.moveToNext();
        key = result.getInt(0);
        return key;
    }

    public ArrayList<Integer> getKeys(String query){
        ArrayList<Integer> keys = new ArrayList<>();
        Cursor results = this.database.rawQuery(query, null);
        while(results.moveToNext()){
            keys.add(results.getInt(0));
        }
        return keys;
    }

    public void close(){
        this.database.close();
    }

    public void reopen(){
        close();
        this.database = this.getWritableDatabase();
    }
}
