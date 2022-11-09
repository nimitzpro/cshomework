package com.nimitzpro.rio22;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        CountDownTimer waitTimer = new CountDownTimer(2500, 300) {

            public void onTick(long millisUntilFinished) {
                setContentView(R.layout.welcome);
            }

            public void onFinish() {
                setContentView(R.layout.activity_main);
            }
        }.start();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(Welcome_Screen.this, MainActivity.class));
//                finish();
//            }
//        }, 250);

        super.onCreate(savedInstanceState);
    }
}
