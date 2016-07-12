package com.vratsasoftware.adroid.matcher.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vratsasoftware.adroid.matcher.cmn.User;

/**
 * Created by VS-PC on 7/11/2016.
 */
public class SQLHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "db";
    public static final int DB_VERSION = 1;
    public static final String DB_TABLE_USER = "DB_TABLE_USER";
    public static final String KEY_NAME = "NAME";
    public static final String KEY_SCORE = "SCORE";
    public static final String KEY_TIME = "TIME";
    protected SQLiteDatabase database;

    public SQLHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        open();
    }

    private void open(){
        database = this.getWritableDatabase();
    }

    public void insertUser(User user){
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, user.getName());
        cv.put(KEY_SCORE, user.getScore());
        cv.put(KEY_TIME, user.getTime());
        database.insert(DB_TABLE_USER, null, cv);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUserTable());
    }

    public String createUserTable(){
        return "CREATE TABLE IF NOT EXISTS " + DB_TABLE_USER + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_SCORE + " INTEGER, "
                + KEY_TIME + " REAL);";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_USER);
        onCreate(db);
    }

    public void close(){
        database.close();
    }

    public Cursor getUserValues(){
        return this.database.query(DB_TABLE_USER, new String[]{KEY_NAME, KEY_SCORE, KEY_TIME},
                null, null, null, null, null);
    }
}
