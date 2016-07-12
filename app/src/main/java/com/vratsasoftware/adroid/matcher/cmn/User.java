package com.vratsasoftware.adroid.matcher.cmn;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable, Comparable<User>{

    private String name;
    private int score;
    private double time;

    public User(){
    }

    public User(String name, int score, double time){
        this.name = name;
        this.score = score;
        this.time = time;
    }

    public User(Parcel in){
        name = in.readString();
        score = in.readInt();
        time = in.readDouble();
    }

    public String getName(){
        return this.name;
    }

    public int getScore(){
        return this.score;
    }

    public double getTime(){
        return this.time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(name);
        dest.writeInt(score);
        dest.writeDouble(time);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        @Override
        public User createFromParcel(Parcel in){
            return new User(in);
        }

        @Override
        public User[] newArray(int size){
            return new User[size];
        }
    };

    @Override
    public int compareTo(User another) {
        if(this.score > another.score){
            return -1;
        }
        if(this.score == another.score){
            if(this.time == another.time){
                return 0;
            } else {
                return this.time < another.time ? -1 : 1;
            }
        }
        return 1;
    }
}
