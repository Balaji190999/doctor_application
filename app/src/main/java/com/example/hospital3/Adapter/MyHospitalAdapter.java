package com.example.hospital3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospital3.Common.Common;
import com.example.hospital3.Interface.IRecyclerItemSelectedListener;
import com.example.hospital3.Model.Hospital;
import com.example.hospital3.R;

import java.util.ArrayList;
import java.util.List;

public class MyHospitalAdapter extends RecyclerView.Adapter<MyHospitalAdapter.MyViewHolder> {
    Context context;
    List<Hospital> hospitalList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public MyHospitalAdapter(Context context, List<Hospital> hospitalList) {
        this.context = context;
        this.hospitalList = hospitalList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup , int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_hospital, viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_hospital_name.setText(hospitalList.get(i).getName());
        myViewHolder.address_hospital.setText(hospitalList.get (i).getAddress());

        if(!cardViewList.contains(myViewHolder.card_hospital))
            cardViewList.add(myViewHolder.card_hospital);

        myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                //set white background for all card not be selected
                for(CardView cardView:cardViewList)
                    cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

                //Set selected BG for only selected item
                myViewHolder.card_hospital.setCardBackgroundColor(context.getResources()
                .getColor(android.R.color.holo_orange_dark));

                //send Broadcast to tell Booking Activity enable button next
                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_HOSPITAL_STORE,hospitalList.get(pos));
                intent.putExtra(Common.KEY_STEP, 1);
                localBroadcastManager.sendBroadcast(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_hospital_name,address_hospital;
        CardView card_hospital;

        public MyViewHolder setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
            return this;
        }

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_hospital = (CardView)itemView.findViewById(R.id.card_hospital);

            address_hospital = (TextView)itemView.findViewById(R.id.address_hospital);
            txt_hospital_name = (TextView)itemView.findViewById(R.id.txt_hospital_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectedListener.onItemSelectedListener(view,getAdapterPosition());
        }
    }
}
