package com.example.hospital3.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital3.Adapter.MyHospitalAdapter;
import com.example.hospital3.Common.Common;
import com.example.hospital3.Common.SpacesItemDocoration;
import com.example.hospital3.Interface.IAllHospitalLoadListener;
import com.example.hospital3.Interface.IHospitalsLoadListener;
import com.example.hospital3.Model.Hospital;
import com.example.hospital3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class Bookingstep1Fragment extends Fragment implements IAllHospitalLoadListener, IHospitalsLoadListener {

    //Variable
    CollectionReference allHospitalRef;
    CollectionReference hospitalsRef;

    IAllHospitalLoadListener iAllHospitalLoadListener;
    IHospitalsLoadListener iHospitalsLoadListener;

    @BindView(R.id.spinner)
    MaterialSpinner spinner;
    @BindView(R.id.recycler_hospital)
    RecyclerView recycler_hospital;

    Unbinder unbinder;

    AlertDialog dialog;


    static Bookingstep1Fragment instance;

    public Bookingstep1Fragment() {

    }

    public static Bookingstep1Fragment getInstance()
    {
        if(instance == null )
            instance = new Bookingstep1Fragment();
        return instance;
    }


    public Bookingstep1Fragment setAllHospitalRef(CollectionReference allHospitalRef) {
        this.allHospitalRef = allHospitalRef;
        return this;
    }

    public Bookingstep1Fragment(int contentLayoutId) {
        super(contentLayoutId);
    }
    ///AllHospital/Madurai/Hospitals/j0AMvCA7JrB7y4QkVKCx

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allHospitalRef = FirebaseFirestore.getInstance().collection("AllHospital");
        iAllHospitalLoadListener = this;
        iHospitalsLoadListener = this;

        dialog = new SpotsDialog.Builder().setContext(getActivity()).setCancelable(false).build();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

         View itemView = inflater.inflate(R.layout.fragment_booking_step_one,container,false);
         unbinder = ButterKnife.bind(this,itemView);

         initView();
         loadAllHospital();

         return itemView;
    }

    private void initView() {
        recycler_hospital.setHasFixedSize(true);
        recycler_hospital.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycler_hospital.addItemDecoration((new SpacesItemDocoration(4)));
    }

    private void loadAllHospital() {
        allHospitalRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            List<String> list = new ArrayList<>();
                            list.add("Please choose city");
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                                list.add(documentSnapshot.getId());
                            iAllHospitalLoadListener.onAllHospitalLoadSuccess(list);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iAllHospitalLoadListener.onAllHospitalLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onAllHospitalLoadSuccess(List<String> areaNameList) {
        spinner.setItems(areaNameList);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position > 0)
                {
                    loadHospitalsOfCity(item.toString());
                }
                else
                {
                    recycler_hospital.setVisibility(View.GONE);
                }

            }
            });
    }

    private void loadHospitalsOfCity(String cityName) {
        dialog.show();
        Common.city=cityName;


        hospitalsRef = FirebaseFirestore.getInstance()
                .collection("AllHospital")
                .document(cityName)
                .collection("Hospitals");
        hospitalsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Hospital> list = new ArrayList<>();
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot documentSnapshot:task.getResult())
                    {
                        Hospital hospital = documentSnapshot.toObject(Hospital.class);
                        hospital.setHospitalsId(documentSnapshot.getId());
                        list.add(hospital);

                    }
                    iHospitalsLoadListener.onHospitalsLoadSuccess(list);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iHospitalsLoadListener.onHospitalsLoadFailed(e.getMessage());

            }
        });

    }

    @Override
    public void onAllHospitalLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onHospitalsLoadSuccess(List<Hospital> hospitalList) {
        MyHospitalAdapter adapter = new MyHospitalAdapter(getActivity(),hospitalList);
        recycler_hospital.setAdapter(adapter);
        recycler_hospital.setVisibility(View.VISIBLE);
        dialog.dismiss();

    }

    @Override
    public void onHospitalsLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        dialog.dismiss();

    }
}

