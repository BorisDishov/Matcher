package com.vratsasoftware.adroid.matcher.Database;

import android.content.Context;
import android.database.Cursor;

import com.google.firebase.database.FirebaseDatabase;
import com.vratsasoftware.adroid.matcher.cmn.User;

public class DatabaseUtils {

    private static DatabaseUtils instance;
    private SQLHelper db;

    private DatabaseUtils(Context context){
        initDB(context);
    }

    public static DatabaseUtils getInstance(Context context){
        if(instance == null){
            instance = new DatabaseUtils(context);
        }
        return instance;
    }

    public void writeUserRecord(User user){
        db.insertUser(user);
    }

    public Cursor readUserRecord(){
        return db.getUserValues();
    }

    private SQLHelper initDB(Context context) {
        if (db == null){
            db = new SQLHelper(context);

        }
        return db;
    }

}
