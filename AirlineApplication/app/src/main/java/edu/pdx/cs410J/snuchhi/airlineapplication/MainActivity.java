package edu.pdx.cs410J.snuchhi.airlineapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button onAddFlightClick = (Button) findViewById(R.id.add_flights) ;
        Button onSearchClick = (Button) findViewById(R.id.search_flights);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.help_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, Help.class);
        this.startActivity(intent);
        return true;
    }

    public void onAddFlightClick(View view) {
        Intent addFlightIntent = new Intent(this , AddFlightDetails.class);
        final int result = 1;
       startActivityForResult(addFlightIntent, result);
    }

    public void onSearchFlightClick(View view) {
        Intent searchFlightIntent = new Intent(this, SearchFlights.class);
        final int result = 1;
        startActivityForResult(searchFlightIntent, result);
    }

}
