package com.example.relationbdd.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.relationbdd.R;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MetroListAdapter extends RecyclerView.Adapter<MetroListAdapter.ViewHolder> {

    private Activity context;
    private RoomDB database;
    private List<FullStation> dataList;

    public MetroListAdapter(Activity context,List<FullStation> dataList){
        this.context=context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MetroListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main_metro,parent,false);
        return new MetroListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MetroListAdapter.ViewHolder holder, int position) {
        FullStation data = dataList.get(position);
        database = RoomDB.getInstance(context);
        holder.textView.setText(data.getStationDB().getSname());
        holder.textView2.setText(data.getStationDB().getCname());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView2;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView= itemView.findViewById(R.id.station_name);
            textView2= itemView.findViewById(R.id.commun_name);
            img = itemView.findViewById(R.id.metro);
        }
    }
}
