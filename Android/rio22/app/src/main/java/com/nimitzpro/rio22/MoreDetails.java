package com.nimitzpro.rio22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MoreDetails extends AppCompatActivity {

    private Button buttonNext;

    TextView desc, winnings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moredetails);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buttonNext = findViewById(R.id.buttonLink);

        Bundle b = getIntent().getExtras();
        Team t = (Team) b.getSerializable("team_name");

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoreDetails.this, WebView.class);
                intent.putExtra("team_name", t);
                startActivity(intent);
            }
        });

        desc = findViewById(R.id.desc);
        desc.setText(t.getDesc());

        winnings = findViewById(R.id.winnings);
        winnings.setText("Approx. Total Winnings: " + t.getWinnings());

    }
}