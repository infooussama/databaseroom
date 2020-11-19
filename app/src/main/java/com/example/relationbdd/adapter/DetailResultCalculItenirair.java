package com.example.relationbdd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.relationbdd.R;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.LigneAndFullStationArriver;
import com.example.relationbdd.model.LigneDB;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailResultCalculItenirair extends RecyclerView.Adapter<DetailResultCalculItenirair.ViewHolder> {
    private Activity context;
    private RoomDB database;
    private List<LigneDB> dataList;

    public DetailResultCalculItenirair(Activity context, List<LigneDB> dataList) {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DetailResultCalculItenirair.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main_calclue, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailResultCalculItenirair.ViewHolder holder, int position) {
        LigneDB data = dataList.get(position);
        if(data.getLtype().equals("B")){
            database = RoomDB.getInstance(context);
            holder.textView.setText(data.getLname());
            holder.textView2.setText(data.getOp_color());
            holder.op_name.setText(data.getOp_name());
            holder.lnum.setText(data.getLnum());
            holder.img.setImageResource(R.drawable.ic_bus);
        }
        if(data.getLtype().equals("M")){
            database = RoomDB.getInstance(context);
            holder.textView.setText(data.getLname());
            holder.textView2.setText(data.getOp_color());
            holder.op_name.setText(data.getOp_name());
            holder.lnum.setText(data.getLnum());
            holder.img.setImageResource(R.drawable.ic_metro);
        }
        if(data.getLtype().equals("T")){
            database = RoomDB.getInstance(context);
            holder.textView.setText(data.getLname());
            holder.textView2.setText(data.getOp_color());
            holder.op_name.setText(data.getOp_name());
            holder.lnum.setText(data.getLnum());
            holder.img.setImageResource(R.drawable.ic_tram);
        }
        if(data.getLtype().equals("L")){
            database = RoomDB.getInstance(context);
            holder.textView.setText(data.getLname());
            holder.textView2.setText(data.getOp_color());
            holder.op_name.setText(data.getOp_name());
            holder.lnum.setText(data.getLnum());
            holder.img.setImageResource(R.drawable.ic_railway);
        }
        if(data.getLtype().equals("P")){
            database = RoomDB.getInstance(context);
            holder.textView.setText(data.getLname());
            holder.textView2.setText(data.getOp_color());
            holder.op_name.setText(data.getOp_name());
            holder.lnum.setText(data.getLnum());
            holder.img.setImageResource(R.drawable.ic_walk);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textView2,lnum,op_name;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.station_name);
            textView2 = itemView.findViewById(R.id.commun_name);
            lnum = itemView.findViewById(R.id.lnum);
            op_name = itemView.findViewById(R.id.op_name);
            img = itemView.findViewById(R.id.bus);
        }
    }
}