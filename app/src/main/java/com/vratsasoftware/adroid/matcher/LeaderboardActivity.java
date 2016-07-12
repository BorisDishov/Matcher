package com.vratsasoftware.adroid.matcher;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vratsasoftware.adroid.matcher.Database.DatabaseHelper;
import com.vratsasoftware.adroid.matcher.cmn.CustomAdapter;
import com.vratsasoftware.adroid.matcher.cmn.User;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardActivity extends AppCompatActivity {

    RecyclerView recView;
    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        recView = (RecyclerView) findViewById(R.id.rec_view);
        final Bundle b = getIntent().getExtras();
        users = b.getParcelableArrayList("users");
        Collections.sort(users);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(new CustomAdapter(users));
    }
}
