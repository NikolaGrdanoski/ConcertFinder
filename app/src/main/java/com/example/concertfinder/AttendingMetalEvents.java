package com.example.concertfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class AttendingMetalEvents extends AppCompatActivity {
    SQLiteDatabase db;
    ConcertFinderDB concertFinderDB = new ConcertFinderDB(this);
    ArrayList<Concerts> concertsArrayList;
    String username;
    RecyclerView recyclerView;
    AttendingMetalEventsAdapter attendingMetalEventsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attending_metal_events);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        username = intent.getStringExtra("Username");

        concertsArrayList = new ArrayList<>();
        db = concertFinderDB.getReadableDatabase();

        recyclerView = (RecyclerView) findViewById(R.id.attending);

        concertsArrayList = displayData();
        attendingMetalEventsAdapter = new AttendingMetalEventsAdapter(AttendingMetalEvents.this, concertsArrayList, username);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(attendingMetalEventsAdapter);
    }

    private ArrayList<Concerts> displayData() {
        db = concertFinderDB.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT UserAttending.uacName FROM UserAttending WHERE UserAttending.uauName = '"+username+"'", null);

        if (cursor.moveToFirst()) {
            do {
                concertsArrayList.add(new Concerts(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        return concertsArrayList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id==R.id.action_favorite){
            Toast.makeText(this, "Item One Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id==R.id.action_added){
            Toast.makeText(this, "Already here", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}