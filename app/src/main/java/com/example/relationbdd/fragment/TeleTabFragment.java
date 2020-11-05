package com.example.relationbdd.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.relationbdd.R;
import com.example.relationbdd.adapter.TeleListAdapter;
import com.example.relationbdd.adapter.TramListAdapter;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeleTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeleTabFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;
    List<FullStation> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    TeleListAdapter adapter;
    EditText searchView;
    CharSequence search="";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeleTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeleTabFragment newInstance(String param1, String param2) {
        TeleTabFragment fragment = new TeleTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tele_tab, container, false);
        searchView = view.findViewById(R.id.search_bar);
        recyclerView = view.findViewById(R.id.recycler_view);
        database = RoomDB.getInstance(getActivity());
        dataList = database.fullStationDao().getFullStationsBus("Téléphérique");
        Log.e("nbr ligne", ""+dataList.size());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TeleListAdapter(getActivity(),dataList);
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
        return view;

    }
}