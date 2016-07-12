package com.vratsasoftware.adroid.matcher;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vratsasoftware.adroid.matcher.Database.DatabaseHelper;
import com.vratsasoftware.adroid.matcher.Database.DatabaseUtils;
import com.vratsasoftware.adroid.matcher.Database.SQLHelper;
import com.vratsasoftware.adroid.matcher.cmn.CustomAdapter;
import com.vratsasoftware.adroid.matcher.cmn.User;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardSQLActivity extends AppCompatActivity {

    RecyclerView recView;
    ArrayList<User> users;
    DatabaseUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_sql);

        dbUtils = DatabaseUtils.getInstance(this);
        getUsers();

//        recView = (RecyclerView) findViewById(R.id.rec_view_sql);
//        final Bundle b = getIntent().getExtras();
//        users = b.getParcelableArrayList("SQLUsers");
//        Collections.sort(users);
//        recView.setLayoutManager(new LinearLayoutManager(this));
//        recView.setAdapter(new CustomAdapter(users));
    }

    private void getUsers(){
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                users = getUsersFromSQL();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                recView = (RecyclerView) findViewById(R.id.rec_view_sql);
//                final Bundle b = getIntent().getExtras();
//                users = b.getParcelableArrayList("SQLUsers");
                Collections.sort(users);
                recView.setLayoutManager(new LinearLayoutManager(LeaderboardSQLActivity.this));
                recView.setAdapter(new CustomAdapter(users));
            }
        }.execute();
    }

    private ArrayList<User> getUsersFromSQL(){
        ArrayList<User> users = new ArrayList<>();
        Cursor cursor = dbUtils.readUserRecord();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(SQLHelper.KEY_NAME));
                int score = cursor.getInt(cursor.getColumnIndex(SQLHelper.KEY_SCORE));
                double time = cursor.getDouble(cursor.getColumnIndex(SQLHelper.KEY_TIME));
                users.add(new User(name, score, time));
            } while (cursor.moveToNext());
        }
        return users;
    }
}
