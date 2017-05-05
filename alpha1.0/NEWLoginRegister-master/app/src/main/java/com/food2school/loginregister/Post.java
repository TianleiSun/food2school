package com.food2school.loginregister;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tianleisun on 4/27/17.
 */

public class Post implements Parcelable {
    private String resName;
    private int driverID;
    private int restID;
    private String targetAddress;
    private String deliveryTime;
    private int maxOrderNum;



    protected Post(Parcel in) {
        resName = in.readString();
        driverID = in.readInt();
        restID = in.readInt();
        targetAddress = in.readString();
        deliveryTime = in.readString();
        maxOrderNum = in.readInt();
    }

    public Post(String R, int D, int rID, String T, String F, int M){
        resName = R;
        driverID = D;
        restID = rID;
        targetAddress = T;
        deliveryTime = F;
        maxOrderNum = M;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resName);
        dest.writeInt(driverID);
        dest.writeInt(restID);
        dest.writeString(targetAddress);
        dest.writeString(deliveryTime);
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

    public int getrestID(){
        return this.restID;
    }

    public void setrestID(int I){
        this.restID = I;
    }

    public String gettargetAddress(){
        return this.targetAddress;
    }

    public void settargetAddress(String A){
        this.targetAddress = A;
    }

    public String getDeliveryTime(){
        return this.deliveryTime;
    }

    public void setDeliveryTime(String P){
        this.deliveryTime = P;
    }

    public int getMaxOrderNum(){return this.maxOrderNum;}

    public void setMaxOrderNum(int G) {this.maxOrderNum = G;}
}
