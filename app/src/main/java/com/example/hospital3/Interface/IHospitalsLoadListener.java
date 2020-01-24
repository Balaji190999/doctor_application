package com.example.hospital3.Interface;

import com.example.hospital3.Model.Hospital;

import java.util.List;

public interface IHospitalsLoadListener {
    void onHospitalsLoadSuccess(List<Hospital> hospitalList);

    void onHospitalsLoadFailed(String message);
}
