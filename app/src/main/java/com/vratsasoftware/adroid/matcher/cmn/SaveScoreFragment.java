package com.vratsasoftware.adroid.matcher.cmn;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vratsasoftware.adroid.matcher.Database.DatabaseHelper;
import com.vratsasoftware.adroid.matcher.Database.DatabaseUtils;
import com.vratsasoftware.adroid.matcher.GameplayActivity;
import com.vratsasoftware.adroid.matcher.R;
import com.vratsasoftware.adroid.matcher.game_logic.GameHelper;

public class SaveScoreFragment extends Fragment{

    Button btnOk;
    Button btnCancel;
    EditText edtName;
    GameplayActivity activity;
    GameHelper gameHelper;
    DatabaseHelper dbHelper;
    DatabaseUtils dbUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.save_result_fragment, container, false);
        activity = (GameplayActivity) getActivity();
        gameHelper = new GameHelper(activity.getRelative());
        dbHelper = new DatabaseHelper(activity.getContext());
        dbUtils = DatabaseUtils.getInstance(activity.getContext());

        btnOk = (Button) view.findViewById(R.id.btn_ok);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        edtName = (EditText) view.findViewById(R.id.edt_name1);

        setButtonsOnClickListeners();

        return view;
    }

    private void setButtonsOnClickListeners(){
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeRecord(edtName.getText().toString());
                activity.setFragmentVisibility(false);
                activity.setButtonsVisibility(true);
                activity.setRelativeVisibility(true);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setFragmentVisibility(false);
                activity.setButtonsVisibility(true);
                activity.setRelativeVisibility(true);
            }
        });
    }

    private void writeRecord(final String name){
        AsyncTask <Void, Void, Void> as = new AsyncTask() {
            @Override
            protected Void doInBackground(Object[] params) {
                User user = new User(name, gameHelper.getPoints(), activity.getTime());
                dbHelper.writeToDatabase(user);
                dbUtils.writeUserRecord(user);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
            }
        }.execute();
    }
}
