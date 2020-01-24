package com.example.hospital3.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Doctor implements Parcelable {
    private String name;
    private String username,doctorId;

    protected Doctor(Parcel in) {
        name = in.readString();
        username = in.readString();
        doctorId = in.readString();
        password = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readLong();
        }
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

    public String getPassword() {
        return password;
    }

    public Doctor setPassword(String password) {
        this.password = password;
        return this;
    }

    private String password;
    private Long rating;

    public Doctor() {
    }

    public String getName() {
        return name;
    }

    public Doctor setName(String name) {
        this.name = name;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Doctor setUsername(String username) {
        this.username = username;
        return this;
    }

    public Long getRating() {
        return rating;
    }

    public Doctor setRating(Long rating) {
        this.rating = rating;
        return this;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public Doctor setDoctorId(String doctorId) {
        this.doctorId = doctorId;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(doctorId);
        dest.writeString(password);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(rating);
        }
    }
}
