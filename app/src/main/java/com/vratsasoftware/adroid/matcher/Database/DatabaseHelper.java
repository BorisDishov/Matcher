package com.vratsasoftware.adroid.matcher.Database;

import android.content.Context;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.vratsasoftware.adroid.matcher.cmn.User;

import java.util.ArrayList;

public class DatabaseHelper {

    private Firebase database;
    private final String LEADERBOARD = "Leaderboard";
    private final String DATABASE_URL = "https://matcher-c12d9.firebaseio.com/";
    private boolean flag;

    public DatabaseHelper(Context context) {
        Firebase.setAndroidContext(context);
        database = new Firebase(DATABASE_URL);
        flag = true;
    }

    public void writeToDatabase(User user) {
        database.child(LEADERBOARD).push().setValue(user);
    }

    public ArrayList<User> readUsersFromDatabase() {
        final ArrayList<User> users = new ArrayList<User>();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot lastSnapshot = null;
                for (DataSnapshot userSnapshot : dataSnapshot.child(LEADERBOARD).getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    lastSnapshot = userSnapshot;
                    if (flag) {
                        users.add(user);
                        Log.e("read", "read a new user");
                    }
                }
                if(!flag){
                    User user = lastSnapshot.getValue(User.class);
                    users.add(user);
                }
                flag = false;

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        return users;
    }

}
