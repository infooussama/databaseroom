package com.example.relationbdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.relationbdd.acs.Acs;
import com.example.relationbdd.acs.Acs2;
import com.example.relationbdd.acs.Ant;
import com.example.relationbdd.acs.Ant2;
import com.example.relationbdd.adapter.DetailMetroListAdapter;
import com.example.relationbdd.adapter.DetailResultCalculItenirair;
import com.example.relationbdd.dao.FullStationDao;
import com.example.relationbdd.database.RoomDB;
import com.example.relationbdd.model.FullStation;
import com.example.relationbdd.model.LigneDB;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CalculeItineraire extends AppCompatActivity{
    RelativeLayout depart,destination;
    TextView departText, destionationText, prix , distance , prixText , distanceText;
    Button calcule,calcule2,show_route,report;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    DetailResultCalculItenirair adapter;
    Ant ant;
    Ant2 ant2;
    FullStation codea,coded;
    ImageButton swap;
    RoomDB roomDB;
    FullStationDao fullStationDao;
    Dialog dialog;
    List<FullStation> fullStationfinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcule_itineraire);
        roomDB = RoomDB.getInstance(this);
        fullStationDao = roomDB.fullStationDao();
        depart = findViewById(R.id.depart);
        destination = findViewById(R.id.destination);
        destionationText = findViewById(R.id.destinationtext);
        departText = findViewById(R.id.departtext);
        calcule = findViewById(R.id.search);
        calcule2 = findViewById(R.id.search2);
        show_route = findViewById(R.id.show_route);
        swap = findViewById(R.id.swap);
        prix = findViewById(R.id.prix);
        prixText = findViewById(R.id.prixText);
        distance = findViewById(R.id.distance);
        distanceText = findViewById(R.id.distanceText);
        report = findViewById(R.id.report);
        report.setVisibility(View.INVISIBLE);
        final LoadingDialog loadingDialog = new LoadingDialog(CalculeItineraire.this);
        show_route.setEnabled(false);
        show_route.setBackgroundColor(Color.parseColor("#EEEEEE"));
        calcule.setEnabled(false);
        calcule.setBackgroundColor(Color.parseColor("#656565"));
        calcule2.setEnabled(false);
        calcule2.setBackgroundColor(Color.parseColor("#656565"));
        dialog = new Dialog(this);
        Intent intent = getIntent();
        coded = (FullStation) intent.getSerializableExtra("station");
        destionationText.setText(coded.getStationDB().getSname());

        depart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculeItineraire.this, ListStation.class);
                intent.putExtra("code",1);
                CalculeItineraire.this.startActivityForResult(intent, 0);
                calcule.setBackgroundColor(Color.parseColor("#E75748"));
                calcule.setEnabled(true);
                calcule2.setBackgroundColor(Color.parseColor("#E75748"));
                calcule2.setEnabled(true);
            }
        });

        calcule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullStationfinal = new ArrayList<>();
                loadingDialog.startLoadingDialog();
                Acs acs = new Acs();
                ant = acs.calcule(codea.getScode(),coded.getScode());
                if(ant!=null){
                    loadingDialog.dismissDialog();
                    show_route.setBackgroundColor(Color.parseColor("#E75748"));
                    show_route.setEnabled(true);
                    report.setVisibility(View.VISIBLE);
                }
                prix.setVisibility(View.VISIBLE);
                distance.setVisibility(View.VISIBLE);
                prixText.setVisibility(View.VISIBLE);
                distanceText.setVisibility(View.VISIBLE);
                prix.setText(""+ant.prixTotal);
                distance.setText((new DecimalFormat("##.##")).format(ant.dis));
                List<LigneDB> ligneDBS = ant.getSolutionLigne();
                List<FullStation> fullStations = ant.getVisitedStation();
                for (int k = 0 ; k < fullStations.size(); k++){
                    fullStationfinal.add(fullStations.get(k));
                }
                fullStations.remove(0);
                recyclerView = findViewById(R.id.recycler_view);
                linearLayoutManager = new LinearLayoutManager(v.getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter = new DetailResultCalculItenirair((Activity) v.getContext(),ligneDBS,fullStations);
                recyclerView.setAdapter(adapter);
            }
        });

        calcule2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullStationfinal = new ArrayList<>();
                Acs2 acs = new Acs2();
                ant2 = acs.calcule(codea.getScode(),coded.getScode());
                if(ant2!=null){
                    show_route.setBackgroundColor(Color.parseColor("#E75748"));
                    show_route.setEnabled(true);
                    report.setVisibility(View.VISIBLE);
                }
                prix.setVisibility(View.VISIBLE);
                distance.setVisibility(View.VISIBLE);
                prixText.setVisibility(View.VISIBLE);
                distanceText.setVisibility(View.VISIBLE);
                prix.setText(""+ant2.prixTotal);
                distance.setText((new DecimalFormat("##.##")).format(ant2.dis));
                List<LigneDB> ligneDBS = ant2.getSolutionLigne();
                List<FullStation> fullStations = ant2.getVisitedStation();
                for (int k = 0 ; k < fullStations.size(); k++){
                    fullStationfinal.add(fullStations.get(k));
                }
                fullStations.remove(0);
                recyclerView = findViewById(R.id.recycler_view);
                linearLayoutManager = new LinearLayoutManager(v.getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter = new DetailResultCalculItenirair((Activity) v.getContext(),ligneDBS,fullStations);
                recyclerView.setAdapter(adapter);
            }
        });

        show_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(),ShowResultInmaps.class);
                intent1.putExtra("value", (Serializable) fullStationfinal);
                startActivity(intent1);
            }
        });

        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullStation tmp = coded;
                coded = codea;
                codea = tmp;
                destionationText.setText(coded.getStationDB().getSname());
                departText.setText(codea.getStationDB().getSname());
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText;
                Button button;
                dialog.setContentView(R.layout.costumpopup);
                editText = dialog.findViewById(R.id.commentaire);
                button = dialog.findViewById(R.id.send);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!editText.getText().toString().isEmpty()){
                            Intent intent1 = new Intent(Intent.ACTION_SENDTO);
                            intent1.putExtra(Intent.EXTRA_EMAIL,new String[]{"yabdelfatah7@gmail.com"});
                            intent1.putExtra(Intent.EXTRA_SUBJECT,"report");
                            intent1.putExtra(Intent.EXTRA_TEXT,editText.getText().toString());
                            intent1.setData(Uri.parse("mailto:"));
                            if(intent1.resolveActivity(getPackageManager()) != null){
                                startActivity(intent1);
                            } else {
                                Log.e("popup","erreur");
                            }
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String s="vide";
        if (requestCode == 0) {
            if(resultCode == Activity.RESULT_OK){
                codea = (FullStation) data.getSerializableExtra("station");
                departText.setText(codea.getStationDB().getSname());
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

}