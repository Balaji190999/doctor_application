package com.example.hospital3.Common;

import com.example.hospital3.Model.Department;
import com.example.hospital3.Model.Doctor;
import com.example.hospital3.Model.Hospital;

public class Common {
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT" ;
    public static final String KEY_HOSPITAL_STORE = "HOSPITAL_SAVE";
    public static final String KEY_DEPARTMENT_LOAD_DONE = "DEPARTMENT_LOAD_DONE";
    public static final String KEY_DEPARTMENT_STORE = "DEPARTMENT_SAVE";
    public static final String KEY_STEP = "STEP";
    public static final String KEY_DOCTOR_STORE = "DOCTOR_SAVE";
    public static final String KEY_DEPARTMENT_SELECTED = "DEPARTMENT_SELECTED";
    public static final String KEY_DOCTOR_LOAD_DONE = "DOCTOR_LOAD_DONE";
    public static final String KEY_DISPLAY_TIME_SLOT = "DISPLAY_TIME_SLOT" ;
    public static final String KEY_DOCTOR_SELECTED = "DOCTOR_SELECTED";
    public static Hospital currentHospital;
    public static int step = 0 ; //Ini first step is  0
    public static String city="";
    public static Department currentDepartment;
    public static String hosp="";
    public static Doctor currentDoctor;
}
