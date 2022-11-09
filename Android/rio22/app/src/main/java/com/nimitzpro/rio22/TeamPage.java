package com.nimitzpro.rio22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TeamPage extends AppCompatActivity {

    private Button buttonNext;

    ImageView imageView2;
    TextView teampagename, created;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teampage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = getIntent().getExtras();
        Team t = (Team) b.getSerializable("team_name");

        buttonNext =findViewById(R.id.buttonInfo);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TeamPage.this, MoreDetails.class);
                intent.putExtra("team_name", t);
                startActivity(intent);
            }
        });

        imageView2 = findViewById(R.id.logo);
        teampagename = findViewById(R.id.teampagename);
        created = findViewById(R.id.created);

        teampagename.setText(t.getName());
        created.setText("Created: " + t.getCreated());

        Integer id = TeamPage.this.getResources().getIdentifier(t.getImage(), "drawable", TeamPage.this.getPackageName());
        imageView2.setImageResource(id);

    }
}