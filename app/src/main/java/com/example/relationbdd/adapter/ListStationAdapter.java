package com.example.relationbdd.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.relationbdd.CalculeItineraire;
import com.example.relationbdd.DetailBusStation;
import com.example.relationbdd.MainActivity;
import com.example.relationbdd.R;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.fragment.ItineraireFragment;
import com.example.relationbdd.model.FullStation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ListStationAdapter extends RecyclerView.Adapter<ListStationAdapter.ViewHolder> {

    private Activity context;
    private RoomDB database;
    private List<FullStation> dataList;
    private List<FullStation> filtredataList;
    int c=2;
    private int intentCode;
    private FloatingActionButton button;
    TextView textView;
    public ListStationAdapter(Activity context, List<FullStation> dataList, FloatingActionButton button, TextView textView){
        this.context = context;
        this.dataList = dataList;
        this.filtredataList = dataList;
        this.intentCode = this.context.getIntent().getIntExtra("code", -1);
        this.button = button;
        this.textView = textView;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ListStationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListStationAdapter.ViewHolder holder, int position) {
        FullStation data = filtredataList.get(position);
        database = RoomDB.getInstance(context);
        holder.textView.setText(data.getStationDB().getSname());
        holder.textView2.setText(data.getStationDB().getCname());
        if(intentCode == 1){
            textView.setText("Depart");
            button.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"ItemClicked"+data.getStationDB().getSname(),Toast.LENGTH_LONG).show();
                if(intentCode == 0){
                    Intent intent = new Intent(context, CalculeItineraire.class);
                    intent.putExtra("station",filtredataList.get(position));
                    context.startActivity(intent);
                }
                if(intentCode == 1){
                    button.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent();
                    intent.putExtra("station",filtredataList.get(position));
                    context.setResult(Activity.RESULT_OK, intent);
                    context.finish();
                }
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
            img = itemView.findViewById(R.id.bus);
        }
    }
}
