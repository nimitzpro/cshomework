


package com.nimitzpro.rio22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView searchView;

    private RecyclerView recyclerView;
    private DataAdapter adapter;
    private getTeamsXML teamsXML;
    private ArrayList<Team> teams;

    private ImageView iem;
    private Button wiki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        teamsXML = new getTeamsXML(this);
        teams = teamsXML.getTeams();

        adapter = new DataAdapter(this, R.layout.row, teams);
        recyclerView.setAdapter(adapter);


        iem = findViewById(R.id.iem);
        iem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, WebViewGeneral.class);
                intent.putExtra("url", "https://pro.eslgaming.com/tour/csgo");
                startActivity(intent);
            }
        });
        wiki = findViewById(R.id.wiki);
        wiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, WebViewGeneral.class);
                intent.putExtra("url", "https://liquipedia.net/counterstrike/Main_Page");
                startActivity(intent);
            }
        });

    }

    private void filter_data(String newText) {
        ArrayList<Team> filtered_list = new ArrayList<>();
        for (Team item: teams){
            if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                filtered_list.add(item);
            }
        }
        adapter = new DataAdapter(this, R.layout.row, filtered_list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
//        Log.d("nimitztest", "onTextChange");
        filter_data(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        filter_data(query);
//        Log.d("nimitztest", "onTextChange");
        return false;
    }

}