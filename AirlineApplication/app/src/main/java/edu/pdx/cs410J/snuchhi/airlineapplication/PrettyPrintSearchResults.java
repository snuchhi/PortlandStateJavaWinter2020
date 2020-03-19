package edu.pdx.cs410J.snuchhi.airlineapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class PrettyPrintSearchResults extends AppCompatActivity {
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pretty_print_search_results);
        Button search=(Button)findViewById(R.id.HomePage);
        search.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent homePage=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(homePage);
            }

        });
        Intent intent=getIntent();
        ArrayList<String> finalOutput =new ArrayList<>();
        ArrayList<String> result=intent.getStringArrayListExtra("airline");
        String source=intent.getStringExtra("src");
        String destination=intent.getStringExtra("dest");
        ArrayList<Flight> resultnew=new ArrayList<>();
        Airline a1 = null;
        ListView listView=(ListView) findViewById(R.id.ListViewID);
        assert result != null;
        for(String str:result){
            String[] flightDetails=str.split(";");
            a1=new Airline(flightDetails[0]);
            Flight f =new Flight(flightDetails[1]);
            f.setSource(flightDetails[2]);
            String[] ddate=flightDetails[3].split(" ");
            try {
                f.setDepartDate(ddate[0],ddate[1]+" "+ddate[2]);
                f.setDestination(flightDetails[4]);
                if(source!=null && destination!=null && source.equals(f.getSource()) && destination.equals(f.getDestination())) {
                    String[] adate = flightDetails[5].split(" ");
                    f.setArrivalDate(adate[0], adate[1] + " " + adate[2]);
                    resultnew.add(f);
                }else if(source==null && destination==null){
                    String[] adate = flightDetails[5].split(" ");
                    f.setArrivalDate(adate[0], adate[1] + " " + adate[2]);
                    resultnew.add(f);
                }
            } catch (Exception e) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage(e.getMessage());
                builder1.setCancelable(true);
                AlertDialog alert1 = builder1.create();
                alert1.show();
            }
        }

        Collections.sort(resultnew);
        try {
            for (Flight f : resultnew) {
                assert a1 != null;
                finalOutput.add("Airline : "+a1.getName()+"\n"+"Flight Number: "+f.getNumber()+" \n"+
                        "Departs "+f.getSource()+" at "+f.getDepartureString()+"\n"+"Arrives "+f.getDestination()+" at "+
                        f.getArrivalString()+" \n"+"Travel Time is "+f.timesDifferenceInMinutes(f.getDepartureString(),f.getArrivalString())+" minutes");
            }
        }catch (Exception e){

            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setMessage(e.getMessage());
            builder1.setCancelable(true);
            AlertDialog alert1 = builder1.create();
            alert1.show();
        }
        if(finalOutput.size()==0 && source==null && destination==null){
            assert a1 != null;
            finalOutput.add("No flights found for "+a1.getName());
        }else if(finalOutput.size()==0 && source!=null && destination!=null){
            assert a1 != null;
            finalOutput.add("No flights with Source "+source+" and destination "+destination+" from Airline "+a1.getName());
        }


        ArrayAdapter arrayAdapter;
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,finalOutput){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                    view.setBackgroundColor(Color.parseColor("#BBFFFF"));
                    view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);



                return view;
            }
        };
        listView.setAdapter(arrayAdapter);

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

