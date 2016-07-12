package com.vratsasoftware.adroid.matcher;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.vratsasoftware.adroid.matcher.Database.DatabaseHelper;
import com.vratsasoftware.adroid.matcher.cmn.SaveScoreFragment;
import com.vratsasoftware.adroid.matcher.cmn.User;
import com.vratsasoftware.adroid.matcher.game_logic.Block;
import com.vratsasoftware.adroid.matcher.game_logic.GameHelper;

public class GameplayActivity extends AppCompatActivity {

    RelativeLayout relative;
    GameHelper gameHelper;
    TextView txtGameOver;
    Button btnTryAgain;
    DatabaseHelper dbHelper;
    EditText edtName;
    long startTime;
    long endTime;
    FrameLayout fragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        Firebase.setAndroidContext(this);

        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        setFragmentContainer();

        relative = (RelativeLayout) findViewById(R.id.relative);
        gameHelper = new GameHelper(relative);
        edtName = (EditText) findViewById(R.id.edt_name);
        txtGameOver = (TextView) findViewById(R.id.txt_game_over);
        dbHelper = new DatabaseHelper(this);
        btnTryAgain = (Button) findViewById(R.id.btn_try_again);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setVisibility(View.INVISIBLE);
                setButtonsVisibility(false);
                createNewGame();
            }
        });
        createNewGame();
    }

    private void setFragmentContainer(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SaveScoreFragment fragment = new SaveScoreFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public double getTime(){
        double time = endTime - startTime;
        return time / 1000;
    }

    public void createNewGame(){
        for(int i = 0; i < 36; i++){
            final Button btn = (Button) relative.getChildAt(i);
            btn.setBackgroundResource(Block.getRandomColor());
            btn.setVisibility(View.VISIBLE);
            final int finalI = i;
            final ColorDrawable cd = (ColorDrawable) btn.getBackground();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameHelper.removeConnectedBlocks(finalI, cd.getColor());
                    gameHelper.adjustBlocks();
                    if(!gameHelper.checkForPossibleMoves()){
                        endTime = System.currentTimeMillis();
                        if(relative.getChildAt(30).getVisibility() == View.INVISIBLE) {
                            txtGameOver.setText("CONGRATULATIONS!");
                        } else {
                            txtGameOver.setText("GAME OVER!");
                        }
                        setFragmentVisibility(true);
                        setRelativeVisibility(false);
                    }
                }
            });
        }
        startTime = System.currentTimeMillis();
    }

    public void setButtonsVisibility(boolean visibility){
        if(visibility){
            btnTryAgain.setVisibility(View.VISIBLE);
            txtGameOver.setVisibility(View.VISIBLE);
        } else {
            btnTryAgain.setVisibility(View.INVISIBLE);
            txtGameOver.setVisibility(View.INVISIBLE);
        }
        btnTryAgain.setClickable(visibility);
    }

    public RelativeLayout getRelative(){
        return this.relative;
    }

    public void setRelativeVisibility(boolean visible){
        if(visible){
            relative.setVisibility(View.VISIBLE);
        } else {
            relative.setVisibility(View.INVISIBLE);
        }
    }

    public Context getContext(){
        return this;
    }

    public void setFragmentVisibility(boolean visibility){
        if(visibility){
            fragmentContainer.bringToFront();
            fragmentContainer.setVisibility(View.VISIBLE);
        } else {
            fragmentContainer.setVisibility(View.INVISIBLE);
        }
    }
}
