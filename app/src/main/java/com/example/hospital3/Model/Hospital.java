package com.example.hospital3.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Hospital implements Parcelable {
    private String name,address,hospitalsId;

    public Hospital() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;

    }

    public String getHospitalsId() {
        return hospitalsId;
    }

    public void setHospitalsId(String hospitalsId) {
        this.hospitalsId = hospitalsId;

    }



    protected Hospital(Parcel in) {
        name = in.readString();
        address = in.readString();
        hospitalsId = in.readString();
    }

    public static final Creator<Hospital> CREATOR = new Creator<Hospital>() {
        @Override
        public Hospital createFromParcel(Parcel in) {
            return new Hospital(in);
        }

        @Override
        public Hospital[] newArray(int size) {
            return new Hospital[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(hospitalsId);
    }



}
