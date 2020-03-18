package edu.pdx.cs410J.snuchhi.airlineapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class PrettyPrintSearchResults extends AppCompatActivity {
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pretty_print_search_results);
        Intent intent=getIntent();
        ArrayList<String> finalresult=new ArrayList<>();
        ArrayList<String> result=intent.getStringArrayListExtra("airline");
        String source=intent.getStringExtra("src");
        String destination=intent.getStringExtra("dest");
        ArrayList<Flight> result2=new ArrayList<>();
        Airline a1 = null;
        ListView listView=(ListView)findViewById(R.id.ListViewID);
        for(String str:result){
            String[] temp=str.split(";");
            a1=new Airline(temp[0]);
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
                assert a1 != null;
                finalresult.add("Airline Name: "+a1.getName()+" \n"+"Flight Number: "+f.getNumber()+" \n"+
                        "Departs "+f.getSource()+" at "+f.getDepartureString()+"\n"+"Arrives "+f.getDestination()+" at "+
                        f.getArrivalString()+" \n"+"Travel Time is "+f.timesDifferenceInMinutes(f.getDepartureString(),f.getArrivalString())+" minutes");
            }
        }catch (Exception e){
            createAlert(e.getMessage());
        }
        if(finalresult.size()==0 && source==null && destination==null){
            assert a1 != null;
            finalresult.add("No flights found for "+a1.getName());
        }else if(finalresult.size()==0 && source!=null && destination!=null){
            assert a1 != null;
            finalresult.add("No flights with Source "+source+" and destination "+destination+" from Airline "+a1.getName());
        }


        ArrayAdapter arrayAdapter;
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,finalresult){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                if(position %2 == 0)
                {
                    view.setBackgroundColor(Color.parseColor("#40E0D0"));
                }
                else
                {
                    view.setBackgroundColor(Color.parseColor("White"));
                }
                return view;
            }
        };
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

