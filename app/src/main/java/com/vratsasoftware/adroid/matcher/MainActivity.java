package com.vratsasoftware.adroid.matcher;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vratsasoftware.adroid.matcher.Database.DatabaseHelper;
import com.vratsasoftware.adroid.matcher.Database.DatabaseUtils;
import com.vratsasoftware.adroid.matcher.Database.SQLHelper;
import com.vratsasoftware.adroid.matcher.cmn.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnPlay;
    Button btnLeaderboard;
    Button btnLeaderboardSQL;
    DatabaseHelper dbHelper;
    ArrayList<User> users;
    SQLHelper sqlHelper;
    DatabaseUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        sqlHelper = new SQLHelper(this);
        dbUtils = DatabaseUtils.getInstance(this);

        users = dbHelper.readUsersFromDatabase();

        btnPlay = (Button) findViewById(R.id.btn_play);
        btnLeaderboard = (Button) findViewById(R.id.btn_leaderboard);
        btnLeaderboardSQL = (Button) findViewById(R.id.btn_leaderboard_sql);

        setButtonClickListeners();
    }

    private void setButtonClickListeners(){
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GameplayActivity.class);
                startActivity(i);
            }
        });

        btnLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LeaderboardActivity.class);
                Bundle b = new Bundle();
                b.putParcelableArrayList("users", users);
                i.putExtras(b);
                startActivity(i);
            }
        });

        btnLeaderboardSQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LeaderboardSQLActivity.class);
                startActivity(i);
            }
        });
    }
}
