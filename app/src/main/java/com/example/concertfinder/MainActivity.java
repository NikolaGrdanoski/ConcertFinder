package com.example.concertfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button idLoginButtonBtn;
    TextView register;
    ConcertFinderDB concertFinderDB;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        username = findViewById(R.id.idUsername);
        password = findViewById(R.id.idPassword);
        idLoginButtonBtn = findViewById(R.id.idLoginButton);
        register = findViewById(R.id.register);

        concertFinderDB = new ConcertFinderDB(this);
        database = concertFinderDB.getWritableDatabase();

        //concertFinderDB.onUpgrade(database, 1, 2);
        //database.execSQL("INSERT INTO User (username, mail, password) VALUES ('yay123', 'yay@mail.com', '123')");

        idLoginButtonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String authenticateUsername = username.getText().toString();
                String authenticatePassword = password.getText().toString();

                if (authenticateUsername.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }

                if (authenticatePassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }

                Cursor cursor = database.rawQuery("SELECT User.* FROM User", null);
                Cursor cursor1 = database.rawQuery("SELECT Admin.* FROM Admin", null);

                String aaa = null;
                String aaa1 = null;

                char[] usernameCheck = authenticateUsername.toCharArray();

                if (usernameCheck[0] == 'a' && usernameCheck[1] == 'd' && usernameCheck[2] == 'm' && usernameCheck[3] == 'i' && usernameCheck[4] == 'n') {
                    //Admin intent

                    if (cursor1.moveToFirst()) {
                        do {
                            if (authenticateUsername.equals(cursor1.getString(1)) /*&& authenticatePassword.equals(cursor1.getString(3))*/) {
                                aaa = authenticateUsername;
                                Intent intent = new Intent(MainActivity.this, HeavyMetalAdmin.class);
                                startActivity(intent);
                                break;
                            }
                        } while (cursor1.moveToNext());
                        cursor1.moveToFirst();

                        if (aaa == null) {
                            Toast.makeText(MainActivity.this, "Not registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (cursor.moveToFirst()) {
                        do {
                            if (authenticateUsername.equals(cursor.getString(1)) /*&& authenticatePassword.equals(cursor.getString(3))*/) {
                                aaa1 = authenticateUsername;
                                Intent intent = new Intent(MainActivity.this, HeavyMetalUser.class);
                                intent.putExtra("Username", authenticateUsername);
                                startActivity(intent);
                                break;
                            }
                        } while (cursor.moveToNext());
                        cursor.moveToFirst();

                        if (aaa1 == null) {
                            Toast.makeText(MainActivity.this, "Not registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            Toast.makeText(this, "Item Two Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}