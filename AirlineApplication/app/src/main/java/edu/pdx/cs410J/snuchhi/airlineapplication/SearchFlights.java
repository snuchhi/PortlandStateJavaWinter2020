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
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.pdx.cs410J.AirportNames;

public class SearchFlights extends AppCompatActivity {
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_flight);
        Button search=(Button)findViewById(R.id.SearchFlight);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText airlinename = (EditText) findViewById(R.id.AirlineNameSearch);
                String aname = airlinename.getText().toString();
                EditText src = (EditText) findViewById(R.id.SourceSearch);
                String source = src.getText().toString();
                EditText dest = (EditText) findViewById(R.id.DestinationSearch);
                String destination = dest.getText().toString();
                try {
                    if (source.isEmpty() && destination.isEmpty()) {
                        if (aname.isEmpty()) {
                            throw new Exception("Airline Name not provided");
                        } else {
                            readFile(aname, null, null, DisplaySearchResults.class);
                        }
                    } else if (!source.isEmpty() && !destination.isEmpty()) {
                        if(AirportNames.getName(source)==null){
                            throw new Exception("Source Code not valid!");
                        }
                        if(AirportNames.getName(destination)==null){
                            throw new Exception("Destination Code not valid!");
                        }
                        readFile(aname, source, destination, DisplaySearchResults.class);
                    }else if(!source.isEmpty()) {
                        if(AirportNames.getName(source)==null){
                            throw new Exception("Source Code not valid!");
                        }
                        throw new Exception("Source Code is given but destination code is not given.");
                    }
                    else {
                        if(AirportNames.getName(destination)==null){
                            throw new Exception("Destination Code not valid!");
                        }
                        throw new Exception("Source Code is not given but destination code is  given.");
                    }
                }catch (Exception e){
                    createAlert(e.getMessage());
                }
            }
        });

        Button prettyPrint =  (Button) findViewById(R.id.prettyprint);
        prettyPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText airlinename=(EditText)findViewById(R.id.AirlineNameSearch);
                String aname=airlinename.getText().toString();
                EditText src=(EditText)findViewById(R.id.SourceSearch);
                String source=src.getText().toString();
                EditText dest=(EditText)findViewById(R.id.DestinationSearch);
                String destination=dest.getText().toString();
                try {
                    if (source.isEmpty() && destination.isEmpty()) {
                        if (aname.isEmpty()) {
                            throw new Exception("Airline Name not provided");
                        } else {
                            readFile(aname, null, null, PrettyPrintSearchResults.class);
                        }
                    } else if (!source.isEmpty() && !destination.isEmpty()) {
                        readFile(aname, source, destination, PrettyPrintSearchResults.class);
                    }else if(!source.isEmpty()) {
                        throw new Exception("Source Code is given but destination code is not given.");
                    }
                    else {
                        throw new Exception("Source Code is not given but destination code is given.");
                    }
                }catch (Exception e){
                    createAlert(e.getMessage());
                }
            }
            });
    }
public void createAlert(String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        AlertDialog alert11 = builder1.create();
        alert11.show();
        }

public void readFile(String aname,String src,String dest,Class classname) throws Exception {
        File file = new File(context.getFilesDir(), aname+".txt");
        if(file.exists()) {
        FileInputStream fis = context.openFileInput(aname + ".txt");
        InputStreamReader inputStreamReader = new InputStreamReader(fis);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        ArrayList<String> result=new ArrayList<>();
        while ((line = reader.readLine()) != null) {
        result.add(line);
        }
        Intent searchInfo =new Intent(getApplicationContext(),classname);
        searchInfo.putExtra("airline",result);
        searchInfo.putExtra("src",src);
        searchInfo.putExtra("dest",dest);
        startActivity(searchInfo);

        }else{
        createAlert("Airline not found. Please enter correct Airline Name(Airline Name is case sensitive");

        }

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

