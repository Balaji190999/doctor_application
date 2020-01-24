package com.example.hospital3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.hospital3.Adapter.MyViewPagerAdapter;
import com.example.hospital3.Common.Common;
import com.example.hospital3.Common.NonSwipeViewPager;
import com.example.hospital3.Model.Department;
import com.example.hospital3.Model.Doctor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class Main2Activity extends AppCompatActivity {

    LocalBroadcastManager localBroadcastManager;
    AlertDialog dialog;
    CollectionReference departmentRef;
    CollectionReference doctorRef;


    @BindView(R.id.step_view)
    StepView stepView;
    @BindView(R.id.view_pager)
    NonSwipeViewPager viewPager;
    @BindView(R.id.btn_previous_step)
    Button btn_previous_step;
    @BindView(R.id.btn_next_step)
    Button btn_next_step;

    //Event

    @OnClick(R.id.btn_previous_step)
    void previousStep(){
        if(Common.step == 5 || Common.step >  0)
        {
            Common.step--;
            viewPager.setCurrentItem(Common.step);
        }
    }
    @OnClick(R.id.btn_next_step)
    void nextClick(){
        if(Common.step < 5 || Common.step == 0 )
        {
            Common.step ++; //increase
            if(Common.step == 1) //After choose hospital
            {
                if (Common.currentHospital != null)
                {
                    loadDeptpartmentsByHospital(Common.currentHospital.getHospitalsId());
                }
            }
            else if (Common.step == 2) // Doctor Select
            {
                if (Common.currentDepartment != null)
                {
                    loadDoctorByDepartment(Common.currentDepartment.getDepartmentId());
                }
            }
            else if (Common.step == 3) // Pick the time Slot
            {
                if(Common.currentDoctor != null)
                {
                    loadTimeSlotOfDoctor(Common.currentDoctor.getDoctorId());
                }

            }

            viewPager.setCurrentItem(Common.step);

        }
    }

    private void loadTimeSlotOfDoctor(String doctorId) {
        //Send Local Broadcast to Fragment step 4
        Intent intent = new Intent(Common.KEY_DISPLAY_TIME_SLOT);
        localBroadcastManager.sendBroadcast(intent);
    }


    private void loadDeptpartmentsByHospital(String hospitalsId) {
        dialog.show();
        Common.hosp=hospitalsId ;
        //now set all department of hospital
        ///AllHospital/Trichy/Hospitals/feAHzmQanXnV4Ll8tVhJ/Department/aFD8Jh9OD5bTW3W4Vcmn
        if (!TextUtils.isEmpty(Common.city))
        {
            departmentRef = FirebaseFirestore.getInstance()
                    .collection("AllHospital")
                    .document(Common.city)
                    .collection("Hospitals")
                    .document(Common.hosp)
                    .collection("Department");
            departmentRef.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            ArrayList<Department>departments = new ArrayList<>();
                            for (QueryDocumentSnapshot departmentSnapShot:task.getResult())
                            {
                                Department department = departmentSnapShot.toObject(Department.class);
                                department.setDepartmentId(departmentSnapShot.getId());//get id of department


                                departments.add(department);
                            }
                            List<Department> list =new ArrayList<>();
                            if(task.isSuccessful())
                            {
                                for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                                {
                                    Department department = documentSnapshot.toObject(Department.class);
                                    department.setDepartmentId(documentSnapshot.getId());//get id of department


                                    list.add(department);
                                }


                            }

                            //sending brodcast to bookingstep2 to load recycler
                            Intent intent =new Intent(Common.KEY_DEPARTMENT_LOAD_DONE);
                            intent.putParcelableArrayListExtra(Common.KEY_DEPARTMENT_LOAD_DONE,departments);
                            localBroadcastManager.sendBroadcast(intent);
                            dialog.dismiss();


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                        }
                    });
        }

    }

    private void loadDoctorByDepartment(String departmentId)

    {
        dialog.show();
        //Noe select all Doctor of Hospital
        ///AllHospital/Trichy/Hospitals/feAHzmQanXnV4Ll8tVhJ/Department/Tbv2KI6LAqlEsW6fUmZa/Doctor/GwLdyjlBhcWSskdwyDyg
        if(!TextUtils.isEmpty(Common.city))
        {
            doctorRef = FirebaseFirestore.getInstance().collection("AllHospital")
                    .document(Common.city).collection("Hospitals")
                    .document(Common.hosp).collection("Department")
                    .document(departmentId).collection("Doctor");

            doctorRef.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            ArrayList<Doctor> doctors = new ArrayList<>();
                            for (QueryDocumentSnapshot doctorSnapShot:task.getResult())
                            {
                                Doctor doctor = doctorSnapShot.toObject(Doctor.class);
                                doctor.setPassword(""); //Remove password because in client app
                                doctor.setDoctorId(doctorSnapShot.getId()); //Get Id of doctor

                                doctors.add(doctor);

                            }
                            //Send BroadCast to BookingStep 3 fragment to load Reclycler
                            Intent intent = new Intent(Common.KEY_DOCTOR_LOAD_DONE);
                            intent.putParcelableArrayListExtra(Common.KEY_DOCTOR_LOAD_DONE,doctors);
                            localBroadcastManager.sendBroadcast(intent);

                            dialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();

                        }
                    });
        }

    }




    //Broadcast Receiver
    private BroadcastReceiver buttonNextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            int step = intent.getIntExtra(Common.KEY_STEP, 0);
            if(step == 1)
                Common.currentHospital = intent.getParcelableExtra(Common.KEY_HOSPITAL_STORE);
            else if(step == 2)
                Common.currentDepartment = intent.getParcelableExtra(Common.KEY_DEPARTMENT_SELECTED);
            else if(step == 3)
                Common.currentDoctor = intent.getParcelableExtra(Common.KEY_DOCTOR_SELECTED);


            btn_next_step.setEnabled(true);
            setColorButton();
        }
    };

    @OnClick
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(buttonNextReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(Main2Activity.this);
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();


        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(buttonNextReceiver,new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));

        setupStepView();

        setColorButton();

        //View
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(5); //wehave 6 fragment so we need keep stateof this 6 screen page

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {


            }

            @Override
            public void onPageSelected(int i) {
                //Show step
                stepView.go(i, true);
                if(i == 0)
                    btn_previous_step.setEnabled(false);
                else
                    btn_previous_step.setEnabled(true);

                //Set disable button next here
                btn_next_step.setEnabled(false);

                setColorButton();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




    }
    private void setColorButton() {
        if(btn_next_step.isEnabled())
        {
            btn_next_step.setBackgroundResource(R.color.colorButton);
        }
        else
        {
            btn_next_step.setBackgroundResource(android.R.color.darker_gray);
        }

        if(btn_previous_step.isEnabled())
        {
            btn_previous_step.setBackgroundResource(R.color.colorButton);
        }
        else
        {
            btn_previous_step.setBackgroundResource(android.R.color.darker_gray);
        }
    }

    private void setupStepView() {
        List<String> stepList = new ArrayList<>();
        stepList.add("Hospitals");
        stepList.add("Departments");
        stepList.add("Doctors");
        stepList.add("Time");
        stepList.add("Confirm");
        stepView.setSteps(stepList);
    }




}
