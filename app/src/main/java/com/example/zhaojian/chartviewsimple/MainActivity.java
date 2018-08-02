package com.example.zhaojian.chartviewsimple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{


    //test3
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((HeartRateView)findViewById(R.id.heart_rate_view)).setHeartRate(110);
    }
}
