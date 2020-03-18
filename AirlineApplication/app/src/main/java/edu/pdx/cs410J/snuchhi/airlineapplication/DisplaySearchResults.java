package edu.pdx.cs410J.snuchhi.airlineapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class DisplaySearchResults extends AppCompatActivity {
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search_results);
        ArrayList<String> finalresult=new ArrayList<>();
        Intent intent=getIntent();
        ArrayList<String> result=intent.getStringArrayListExtra("airline");
        String source=intent.getStringExtra("src");
        String destination=intent.getStringExtra("dest");

        ArrayList<Flight> result2=new ArrayList<>();
        Airline<edu.pdx.cs410J.AbstractFlight> a1 = null;
        ListView listView=(ListView)findViewById(R.id.ListView);
        assert result != null;
        for(String str:result){
            String[] temp=str.split(";");
            a1=new Airline<edu.pdx.cs410J.AbstractFlight>(temp[0]);
            Flight f1=new Flight(temp[1]);
            f1.setSource(temp[2]);
            String[] ddate=temp[3].split(" ");
            try {
                f1.setDepartDate(ddate[0],ddate[1]+" "+ddate[2]);
                f1.setDestination(temp[4]);
                if(source!=null && destination!=null && source.equals(f1.getSource()) && destination.equals(f1.getDestination())) {
                    String[] adate = temp[5].split(" ");
                    f1.setArrivalDate(adate[0], adate[1] + " " + adate[2]);
                    result2.add(f1);
                }else if(source==null && destination==null){
                    String[] adate = temp[5].split(" ");
                    f1.setArrivalDate(adate[0], adate[1] + " " + adate[2]);
                    result2.add(f1);
                }
            } catch (Exception e) {
                createAlert(e.getMessage());
            }

        }

        Collections.sort(result2);
        try {
            for (Flight f : result2) {
                finalresult.add(f.toString());
            }
        }catch (Exception e){
            createAlert(e.getMessage());
        }
        if(finalresult.size()==0 && source==null && destination==null){
            assert a1 != null;
            finalresult.add("No flights with Airline "+a1.getName());
        }else if(finalresult.size()==0 && source!=null && destination!=null){
            assert a1 != null;
            finalresult.add("No flights with Source "+source+" and destination "+destination+" from Airline "+a1.getName());
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,finalresult);
        listView.setAdapter(arrayAdapter);

    }

    public void createAlert(String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        AlertDialog alert11 = builder1.create();
        alert11.show();
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

}

