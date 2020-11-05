package com.example.relationbdd.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.relationbdd.DetailTeleStation;
import com.example.relationbdd.DetailTramStation;
import com.example.relationbdd.R;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeleListAdapter extends RecyclerView.Adapter<TeleListAdapter.ViewHolder> {

    private Activity context;
    private RoomDB database;
    private List<FullStation> dataList;
    private List<FullStation> filtredataList;
    public TeleListAdapter(Activity context, List<FullStation> dataList){
        this.context=context;
        this.dataList = dataList;
        this.filtredataList = dataList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TeleListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main_tele,parent,false);
        return new TeleListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeleListAdapter.ViewHolder holder, int position) {
        FullStation data = filtredataList.get(position);
        database = RoomDB.getInstance(context);
        holder.textView.setText(data.getStationDB().getSname());
        holder.textView2.setText(data.getStationDB().getCname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"ItemClicked"+data.getStationDB().getSname(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, DetailTeleStation.class);
                intent.putExtra("station_code",filtredataList.get(position).getScode());
                intent.putExtra("station_name",filtredataList.get(position).getStationDB().getSname());
                intent.putExtra("station_lat",filtredataList.get(position).getStop_lat());
                intent.putExtra("station_lon",filtredataList.get(position).getStop_lon());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filtredataList.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String key = constraint.toString();
                if(key.isEmpty()){
                    filtredataList = dataList;
                }else {
                    List<FullStation> listfiltred = new ArrayList<>();
                    for(FullStation stationData: dataList){
                        if(stationData.getStationDB().getSname().toLowerCase().contains(key.toLowerCase())){
                            listfiltred.add(stationData);
                        }
                    }
                    filtredataList = listfiltred;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filtredataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filtredataList = (List<FullStation>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView2;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView= itemView.findViewById(R.id.station_name);
            textView2= itemView.findViewById(R.id.commun_name);
            img = itemView.findViewById(R.id.tram);
        }
    }
}
