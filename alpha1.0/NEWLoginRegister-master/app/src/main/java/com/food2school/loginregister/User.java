package com.food2school.loginregister;

/**
 * Created by Evelyn on 4/23/2017.
 */

/**
 * Created by tianleisun on 4/23/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tianleisun on 4/23/17.
 */

public class User implements Parcelable{
    private String username;
    private int userID;
    private String address;
    private String phone;
    private String email;

    public User(String usern, int userI, String A, String P, String E){
        username = usern;
        userID = userI;
        address = A;
        phone = P;
        email = E;
    }

    protected User(Parcel in) {
        username = in.readString();
        userID = in.readInt();
        address = in.readString();
        phone = in.readString();
        email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeInt(userID);
        dest.writeString(address);
        dest.writeString(phone);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String U){
        this.username = U;
    }

    public int getUserID(){
        return this.userID;
    }

    public void setUserID(int I){
        this.userID = I;
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String A){
        this.address = A;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String P){
        this.phone = P;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String E){
        this.email = E;
    }
}