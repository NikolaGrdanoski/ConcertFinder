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

public class FavMetalEvents extends AppCompatActivity {
    SQLiteDatabase db;
    ConcertFinderDB concertFinderDB = new ConcertFinderDB(this);
    ArrayList<Concerts> concertsArrayList;
    String username;
    RecyclerView recyclerView;
    FavMetalEventsAdapter favMetalEventsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_metal_events);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        username = intent.getStringExtra("Username");

        concertsArrayList = new ArrayList<>();
        db = concertFinderDB.getReadableDatabase();

        recyclerView = (RecyclerView) findViewById(R.id.favorites);

        concertsArrayList = displayData();
        favMetalEventsAdapter = new FavMetalEventsAdapter(FavMetalEvents.this, concertsArrayList, username);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(favMetalEventsAdapter);
    }

    private ArrayList<Concerts> displayData() {
        db = concertFinderDB.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT UserFav.ufcName FROM UserFav WHERE UserFav.ufuName = '"+username+"'", null);

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
            Toast.makeText(this, "Already here", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id==R.id.action_added){
            Intent intent1 = new Intent(FavMetalEvents.this, AttendingMetalEvents.class);
            intent1.putExtra("Username", username);
            startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }
}