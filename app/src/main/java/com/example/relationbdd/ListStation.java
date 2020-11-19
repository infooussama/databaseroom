package com.example.relationbdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.relationbdd.adapter.BusListAdapter;
import com.example.relationbdd.adapter.ListStationAdapter;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_station);
        searchView = findViewById(R.id.search_bar);
        recyclerView = findViewById(R.id.recycler_view);
        database = RoomDB.getInstance(this);
        dataList = database.fullStationDao().getAllStations();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        button = findViewById(R.id.fab_location_search);
        adapter = new ListStationAdapter(this,dataList,button);
        recyclerView.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Mylocation.class);
                startActivity(intent);
            }
        });
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