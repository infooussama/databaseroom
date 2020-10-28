package com.example.relationbdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.example.relationbdd.adapter.BusListAdapter;
import com.example.relationbdd.adapter.ListStationAdapter;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;

import java.util.ArrayList;
import java.util.List;

public class ListStation extends AppCompatActivity {

    RecyclerView recyclerView;
    List<FullStation> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    ListStationAdapter adapter;
    EditText searchView;
    CharSequence search="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_station);
        searchView = findViewById(R.id.search_bar);
        recyclerView = findViewById(R.id.recycler_view);
        database = RoomDB.getInstance(this);
        dataList = database.fullStationDao().getAllStations();
        //Log.e("nbr ligne", ""+dataList.size());
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ListStationAdapter(this,dataList);
        recyclerView.setAdapter(adapter);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
                search = s;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}