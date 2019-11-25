package com.rysl.unigradejav.src.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "unigrade.db";
    private static final String TABLE_NAME1 = "subject";
    private static final String TABLE_NAME2 = "link_sub_mod";
    private static final String TABLE_NAME3 = "module";
    private static final String TABLE_NAME4 = "link_mod_asg";
    private static final String TABLE_NAME5 = "assignment";
    Context context;
    SQLiteDatabase database;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        this.database = getWritableDatabase();
        if(!isDatabaseCreated()){
            System.out.println("YES");
            onUpgrade(database, 1, 1);
        }
        System.out.println("NO");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME1 + " (ID INTEGER PRIMARY KEY NOT NULL, NAME TEXT NOT NULL)");
        db.execSQL("create table " + TABLE_NAME2 + " (subID INTEGER NOT NULL, modID INTEGER NOT NULL)");
        db.execSQL("create table " + TABLE_NAME3 + " (ID INTEGER PRIMARY KEY NOT NULL, NAME TEXT NOT NULL)");
        db.execSQL("create table " + TABLE_NAME4 + " (modID NOT NULL, asgID NOT NULL)");
        db.execSQL("create table " + TABLE_NAME5 + " (ID INTEGER PRIMARY KEY NOT NULL, NAME TEXT NOT NULL, PERCENTAGE INTEGER NOT NULL, TYPE BOOLEAN NOT NULL, RESULT INTEGER, DESCRIPTION TEXT)");
        insertData("INSERT INTO subject VALUES (1, \"Tap card to see modules\");");
        insertData("INSERT INTO link_sub_mod VALUES (1, 1);");
        insertData("INSERT INTO link_sub_mod VALUES (1, 2);");
        insertData("INSERT INTO link_sub_mod VALUES (1, 3);");
        insertData("INSERT INTO link_sub_mod VALUES (1, 4);");
        insertData("INSERT INTO module VALUES (1, \"This is a module\");");
        insertData("INSERT INTO module VALUES (2, \"To delete card, swipe it\");");
        insertData("INSERT INTO module VALUES (3, \"To edit a card, hold it\");");
        insertData("INSERT INTO module VALUES (4, \"Tap card to see assignments\");");
        insertData("INSERT INTO link_mod_asg VALUES (4, 1);");
        insertData("INSERT INTO link_mod_asg VALUES (4, 2);");
        insertData("INSERT INTO link_mod_asg VALUES (4, 3);");
        insertData("INSERT INTO link_mod_asg VALUES (4, 4);");
        insertData("INSERT INTO assignment VALUES (1, \"This is an assignment\", 50, 0, -1, null);");
        insertData("INSERT INTO assignment VALUES (2, \"This is a test\", 20, 1, -1, null);");
        insertData("INSERT INTO assignment VALUES (3, \"This is a coursework\", 30, 0, -1, null);");
        insertData("INSERT INTO assignment VALUES (4, \"Click '+' to add a card\", 0, 0, -1, null);");

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

    public boolean isDatabaseCreated(){
        reopen();
        try{
            Cursor cursor = this.database.rawQuery("SELECT RESULT FROM assignment WHERE ID = 1;", null);
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }
}
