package com.example.hospital3.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital3.Adapter.MyDepartmentAdaptor;
import com.example.hospital3.Common.Common;
import com.example.hospital3.Common.SpacesItemDocoration;
import com.example.hospital3.Model.Department;
import com.example.hospital3.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Bookingstep2Fragment extends Fragment {

    Unbinder unbinder;
    LocalBroadcastManager localBroadcastManager;

    @BindView(R.id.recycler_department)
    RecyclerView recycler_department;

    private BroadcastReceiver departmentDoneReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Department> departmentArrayList = intent.getParcelableArrayListExtra(Common.KEY_DEPARTMENT_LOAD_DONE);
            //create adopter late
            MyDepartmentAdaptor adaptor = new MyDepartmentAdaptor(getContext(),departmentArrayList);
            recycler_department.setAdapter(adaptor);
        }
    };







    static Bookingstep2Fragment instance;

    public static Bookingstep2Fragment getInstance(){
        if(instance == null)
            instance = new Bookingstep2Fragment();
        return instance;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(departmentDoneReceiver,new IntentFilter(Common.KEY_DEPARTMENT_LOAD_DONE));
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(departmentDoneReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_booking_step_two,container, false);


        unbinder = ButterKnife.bind( this,itemView);

        initView();






        return itemView;
    }

    private void initView() {
        recycler_department.setHasFixedSize(true);
        recycler_department.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycler_department.addItemDecoration((new SpacesItemDocoration(4)));
    }


}
