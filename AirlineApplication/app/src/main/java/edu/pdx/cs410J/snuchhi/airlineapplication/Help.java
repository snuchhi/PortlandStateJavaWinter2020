package edu.pdx.cs410J.snuchhi.airlineapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readme);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
