package com.food2school.loginregister;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tianleisun on 4/27/17.
 */

public class Post implements Parcelable {
    private String resName;
    private int driverID;
    private String targetAddress;
    private String finishTime;
    private String acceptUntilTime;
    private int maxOrderNum;

    public Post(String R, int D, String T, String F, String A, int M){
        resName = R;
        driverID = D;
        targetAddress = T;
        finishTime = F;
        acceptUntilTime = A;
        maxOrderNum = M;
    }

    protected Post(Parcel in) {
        resName = in.readString();
        driverID = in.readInt();
        targetAddress = in.readString();
        finishTime = in.readString();
        acceptUntilTime = in.readString();
        maxOrderNum = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resName);
        dest.writeInt(driverID);
        dest.writeString(targetAddress);
        dest.writeString(finishTime);
        dest.writeString(acceptUntilTime);
        dest.writeInt(maxOrderNum);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getresName(){
        return this.resName;
    }

    public void setresName(String U){
        this.resName = U;
    }

    public int getdriverID(){
        return this.driverID;
    }

    public void setdriverID(int I){
        this.driverID = I;
    }

    public String gettargetAddress(){
        return this.targetAddress;
    }

    public void settargetAddress(String A){
        this.targetAddress = A;
    }

    public String getfinishTime(){
        return this.finishTime;
    }

    public void setfinishTime(String P){
        this.finishTime = P;
    }

    public String getacceptUntilTime(){
        return this.acceptUntilTime;
    }

    public void setacceptUntilTime(String E){
        this.acceptUntilTime = E;
    }

    public int getMaxOrderNum(){return this.maxOrderNum;}

    public void setMaxOrderNum(int G) {this.maxOrderNum = G;}
}
