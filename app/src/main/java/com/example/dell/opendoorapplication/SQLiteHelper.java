package com.example.dell.opendoorapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "Login";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "userList";

    //构造函数，创建数据库
    public SQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //建表
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + "(_id INTEGER PRIMARY KEY,"
                + " Username VARCHAR(30)  NOT NULL,"
                + " Password VARCHAR(20))";
        db.execSQL(sql);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    //获取游标
    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    //插入一条记录
    public boolean insert(String username,String password ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Username", username);
        cv.put("Password", password);
        long row =db.insert(TABLE_NAME, null, cv);
        return true;
    }

    //根据条件查询
    public Cursor query(String[] args) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE Username LIKE ?", args);
        return cursor;
    }

    //删除记录
    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where ="_id = ?";
        String[] whereValue = { Integer.toString(id) };
        db.delete(TABLE_NAME, where, whereValue);
    }

    //更新记录
    public void update(int id,String username,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id = ?";
        String[] whereValue = { Integer.toString(id) };
        ContentValues cv = new ContentValues();
        cv.put("Username", username);
        cv.put("Password", password);
        db.update(TABLE_NAME, cv, where, whereValue);
    }
}