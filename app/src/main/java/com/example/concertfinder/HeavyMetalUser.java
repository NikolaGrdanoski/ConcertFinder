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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class HeavyMetalUser extends AppCompatActivity {

    RecyclerView recyclerView;
    SQLiteDatabase db;
    ConcertFinderDB concertFinderDB;
    ArrayList<Concerts> concertsArrayList;
    HeavyMetalUserAdapter heavyMetalUserAdapter;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heavy_metal_user);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        username = intent.getStringExtra("Username");

        concertsArrayList = new ArrayList<>();
        concertFinderDB = new ConcertFinderDB(this);

        recyclerView = (RecyclerView) findViewById(R.id.heavymetalartists);

        concertsArrayList = displayData();
        heavyMetalUserAdapter = new HeavyMetalUserAdapter(HeavyMetalUser.this, concertsArrayList, username);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(heavyMetalUserAdapter);
    }

    private ArrayList<Concerts> displayData() {
        db = concertFinderDB.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Concert.cName FROM Concert", null);

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
            Intent intent1 = new Intent(HeavyMetalUser.this, FavMetalEvents.class);
            intent1.putExtra("Username", username);
            startActivity(intent1);
        }
        else if (id==R.id.action_added){
            Intent intent1 = new Intent(HeavyMetalUser.this, AttendingMetalEvents.class);
            intent1.putExtra("Username", username);
            startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }
}