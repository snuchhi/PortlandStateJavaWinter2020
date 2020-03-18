package edu.pdx.cs410J.snuchhi.airlineapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class AddFlightDetails extends AppCompatActivity {
    Context context = this;
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_flight);
        Button add_flights=(Button)findViewById(R.id.add_flight_details);
        add_flights.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText airlineName=(EditText)findViewById(R.id.airline_name);
                String aname=airlineName.getText().toString();
                EditText flightnum=(EditText)findViewById(R.id.flight_number);
                String fnum=flightnum.getText().toString();
                EditText source=(EditText)findViewById(R.id.source);
                String src=source.getText().toString();
                EditText departdate=(EditText)findViewById(R.id.depart_date);
                String ddate=departdate.getText().toString();
                EditText departtime=(EditText)findViewById(R.id.depart_time);
                String dtime=departtime.getText().toString();
                EditText destination=(EditText)findViewById(R.id.destination);
                String dest=destination.getText().toString();
                EditText arrivaldate=(EditText)findViewById(R.id.arrival_date);
                String adate=arrivaldate.getText().toString();
                EditText arrivaltime=(EditText)findViewById(R.id.arrival_time);
                String atime=arrivaltime.getText().toString();
                String depart = ddate + " " + dtime;
                String arrive = adate + " " + atime;
                try {
                    Airline a1 = new Airline(aname);
                    Flight f1 = new Flight(fnum);
                    f1.setSource(src);
                    f1.setDepartDate(ddate, dtime);
                    f1.setDestination(dest);
                    f1.setArrivalDate(adate, atime);
                    if(!Flight.checkarrivalanddepartdate(f1.getDepartureString(),f1.getArrivalString())){
                        throw new Exception("Arrival date or time must be before departure date or time");
                    }
                    writeFile(aname,f1);

                }catch (Exception e){
                    System.out.println(e.getMessage());
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setMessage(e.getMessage());
                    builder1.setCancelable(true);
                    AlertDialog alert1 = builder1.create();
                    alert1.show();
                }
            }
        });
    }
    public void writeFile(String aname,Flight f1) throws IOException {
        File file = new File(context.getFilesDir(), aname+".txt");
        if(file.exists()){
            System.out.println("File exists "+context.getFilesDir());
            FileWriter writer = new FileWriter(file,true);
            writer.write(aname+";"+f1.getNumber()+";"+f1.getSource()+";"+f1.getDepartureString()+";"+f1.getDestination()+";"+f1.getArrivalString()+System.getProperty("line.separator"));
            writer.close();
            Toast.makeText(this,"Flight details added",Toast.LENGTH_LONG).show();
        }else{
            file.createNewFile();
            FileWriter writer = new FileWriter(file,true);
            writer.write(aname+";"+f1.getNumber()+";"+f1.getSource()+";"+f1.getDepartureString()+";"+f1.getDestination()+";"+f1.getArrivalString()+System.getProperty("line.separator"));
            writer.close();
            Toast.makeText(this,"Flight details added",Toast.LENGTH_LONG).show();
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

