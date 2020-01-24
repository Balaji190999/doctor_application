package com.example.hospital3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital3.Common.Common;
import com.example.hospital3.Interface.IRecyclerItemSelectedListener;
import com.example.hospital3.Model.Department;
import com.example.hospital3.R;

import java.util.ArrayList;
import java.util.List;



public class MyDepartmentAdaptor extends RecyclerView.Adapter<MyDepartmentAdaptor.MyViewHolder> {

    Context context;
    List<Department> departmentList;
    List<CardView> cardViewList;

    LocalBroadcastManager localBroadcastManager;

    public MyDepartmentAdaptor(Context context, List<Department> departmentList) {
        this.context = context;
        this.departmentList = departmentList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_department,viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_department_name.setText(departmentList.get(i).getName());

        if(!cardViewList.contains(myViewHolder.card_department))
            cardViewList.add(myViewHolder.card_department);

        myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                // Set white BG for all card not be selected
                for(CardView cardView:cardViewList)

                    cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

                // Set selected BG for only selected item
                myViewHolder.card_department.setCardBackgroundColor(context.getResources()
                .getColor(android.R.color.holo_orange_dark));

                //Send Broadcast to tell Booking Activity enable button
                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_DEPARTMENT_SELECTED,departmentList.get(pos));
                intent.putExtra(Common.KEY_STEP, 2);
                localBroadcastManager.sendBroadcast(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return departmentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_department_name;
        CardView card_department;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public MyViewHolder setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
            return this;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_department = (CardView)itemView.findViewById(R.id.card_department);

            txt_department_name = (TextView)itemView.findViewById(R.id.txt_department_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectedListener.onItemSelectedListener(view,getAdapterPosition());
        }
    }
}
