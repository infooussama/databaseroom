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
                .inflate(R.layout.list_row_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailResultCalculItenirair.ViewHolder holder, int position) {
        LigneDB data = dataList.get(position);
        database = RoomDB.getInstance(context);
        holder.textView.setText(data.getLname());
        holder.textView2.setText(data.getLtype());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textView2;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.station_name);
            textView2 = itemView.findViewById(R.id.commun_name);
            img = itemView.findViewById(R.id.bus);
        }
    }
}