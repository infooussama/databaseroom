package com.example.relationbdd.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.relationbdd.DetailBusStation;
import com.example.relationbdd.DetailLigne;
import com.example.relationbdd.R;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneDB;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LigneListAdapter extends RecyclerView.Adapter<LigneListAdapter.ViewHolder> {
    private Activity context;
    private RoomDB database;
    private List<LigneDB> dataList;
    private List<LigneDB> filtredataList;


    public LigneListAdapter(Activity context, List<LigneDB> dataList){
        this.context=context;
        this.dataList = dataList;
        this.filtredataList = dataList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public LigneListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main_ligne,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LigneListAdapter.ViewHolder holder, int position) {
        LigneDB data = filtredataList.get(position);
        database = RoomDB.getInstance(context);
        holder.textView.setText(data.getLname());
        holder.textView2.setText(data.getOp_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"ItemClicked"+data.getStationDB().getSname(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, DetailLigne.class);
                intent.putExtra("lid",filtredataList.get(position).getLid());
                intent.putExtra("station_depart",filtredataList.get(position).getId_depart());
                intent.putExtra("station_arrive",filtredataList.get(position).getId_arrive());
                intent.putExtra("ligne_name",filtredataList.get(position).getLname());
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
                    List<LigneDB> listfiltred = new ArrayList<>();
                    for(LigneDB stationData: dataList){
                        if(stationData.getLname().toLowerCase().contains(key.toLowerCase())){
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
                filtredataList = (List<LigneDB>) results.values;
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
            img = itemView.findViewById(R.id.bus);
        }
    }
}
