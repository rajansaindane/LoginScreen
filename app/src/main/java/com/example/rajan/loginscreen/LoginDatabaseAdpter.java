package com.example.rajan.loginscreen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rajan on 18/2/17.
 */

public class LoginDatabaseAdpter {

    static final String DATABASE_NAME="login.db";
    static  final int DATABASE_VERSION=1;
    public static final int NAME_COLUMN=1;

    static final String DATABASE_CREATE="create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text); ";

    public SQLiteDatabase db;

    private Context context;


    private DataBaseHelper dataBaseHelper;

    public LoginDatabaseAdpter(Context context){
        context = context;
        dataBaseHelper=new DataBaseHelper(context,DATABASE_NAME,null,DATABASE_VERSION);

            }
    public LoginDatabaseAdpter open() throws SQLException {
        db = dataBaseHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }
    public SQLiteDatabase getDatabaseInstance(){
        return db;
    }
    public void insertEntry(String username,String password){
        ContentValues newValues=new ContentValues();
        newValues.put("USERNAME",username);
        newValues.put("PASSWORD",password);
        db.insert("LOGIN",null,newValues);
    }
    public int deleteEntity(String UserName){
        String where="USERNAME=?";
        int numberOfEntriesDeleted=db.delete("LOGIN",where,new String[]{UserName});
        return numberOfEntriesDeleted;
    }
    public String getsinlgeEntry(String userName){
        Cursor cursor=db.query("LOGIN",null,"USERNAME=?",new String[]{userName},null,null,null);
        if (cursor.getCount()<1) {
        cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password=cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
        }
    public void  updateEntry(String userName,String password)
    {

        ContentValues updatedValues = new ContentValues();

        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD",password);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userName});
    }
}

