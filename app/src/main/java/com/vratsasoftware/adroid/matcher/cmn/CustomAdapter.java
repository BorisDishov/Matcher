package com.vratsasoftware.adroid.matcher.cmn;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vratsasoftware.adroid.matcher.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{

    ArrayList<User> users;

    public CustomAdapter(ArrayList<User> users){
        this.users = users;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_single_user, parent, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        User user = users.get(position);
        holder.txtNumber.setText(String.valueOf(position + 1) + ".");
        holder.txtName.setText(user.getName() + ":");
        holder.txtUserInformation.setText(getUserString(user));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    private String getUserString(User user){
        return user.getScore() + "p,   " + user.getTime() + "s;";
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView txtNumber;
        TextView txtName;
        TextView txtUserInformation;
        View separator;

        public CustomViewHolder(View itemView) {
            super(itemView);
            txtNumber = (TextView) itemView.findViewById(R.id.txt_number);
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            txtUserInformation = (TextView) itemView.findViewById(R.id.txt_user_information);
            separator = (View) itemView.findViewById(R.id.separator);
        }
    }
}
