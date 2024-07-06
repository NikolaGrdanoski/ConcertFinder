package com.example.concertfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HeavyMetalEventAdd extends AppCompatActivity {

    String date;

    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heavy_metal_event_add);

        SQLiteDatabase db;
        ConcertFinderDB concertFinderDB = new ConcertFinderDB(this);

        EditText cSetName = findViewById(R.id.cSetName);
        EditText cSetArtistName = findViewById(R.id.cSetArtistName);
        EditText cSetGenre = findViewById(R.id.cSetGenre);
        Button cSetDateBtn = findViewById(R.id.cSetDate);
        Button cSetTimeBtn = findViewById(R.id.cSetTime);
        Button cSetPlaceBtn = findViewById(R.id.cSetPlace);
        Button submitBtn = findViewById(R.id.submit);

        db = concertFinderDB.getReadableDatabase();

        String cName = cSetName.getText().toString();
        String cArtistName = cSetArtistName.getText().toString();
        String cGenre = cSetGenre.getText().toString();

        cSetDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(cSetDateBtn);
            }
        });

        cSetTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(cSetTimeBtn);
            }
        });

        cSetPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(HeavyMetalEventAdd.this, MapAddPlace.class);
                startActivity(mapIntent);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cName = cSetName.getText().toString();
                String cArtistName = cSetArtistName.getText().toString();
                String cGenre = cSetGenre.getText().toString();
                db.execSQL("INSERT INTO Concert (cName, artistName, genre, cDate, cTime) VALUES ('"+cName+"', '"+cArtistName+"', '"+cGenre+"', '"+date+"', '"+time+"')");
                Toast.makeText(HeavyMetalEventAdd.this, "Succesfully added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setDate(Button cSetDateBtn) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                date = simpleDateFormat.format(calendar.getTime());
            }
        };

        new DatePickerDialog(HeavyMetalEventAdd.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void setTime(Button cSetTimeBtn) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

                time = simpleDateFormat.format(calendar.getTime());
            }
        };

        new TimePickerDialog(HeavyMetalEventAdd.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }
}