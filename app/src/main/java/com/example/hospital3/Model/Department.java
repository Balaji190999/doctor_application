package com.example.hospital3.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Department implements Parcelable {
    private String name,departmentId;

    public Department() {
    }

    protected Department(Parcel in) {
        name = in.readString();
        departmentId = in.readString();
    }

    public static final Creator<Department> CREATOR = new Creator<Department>() {
        @Override
        public Department createFromParcel(Parcel in) {
            return new Department(in);
        }

        @Override
        public Department[] newArray(int size) {
            return new Department[size];
        }
    };

    public String getName() {
        return name;
    }

    public Department setName(String name) {
        this.name = name;
        return this;
    }

    public String getDepartmentId () {

        return departmentId;
    }

    public Department setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(departmentId);
    }
}
