package com.example.concertfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText usernameR;
    EditText mailR;
    EditText passwordR;
    Button register;
    ConcertFinderDB concertFinderDB;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        usernameR = findViewById(R.id.idUsernameR);
        mailR = findViewById(R.id.idMailR);
        passwordR = findViewById(R.id.idPasswordR);
        register = findViewById(R.id.idRegisterButton);

        concertFinderDB = new ConcertFinderDB(this);
        database = concertFinderDB.getWritableDatabase();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameR.getText().toString();
                String mail = mailR.getText().toString();
                String password = passwordR.getText().toString();

                if (username.isEmpty() || mail.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Register.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }

                String aaa = null;
                String aaa1 = null;
                char[] usernameCheck = username.toCharArray();
                Cursor cursor = database.rawQuery("SELECT User.* FROM User", null);

                if (usernameCheck[0] == 'a' && usernameCheck[1] == 'd' && usernameCheck[2] == 'm' && usernameCheck[3] == 'i' && usernameCheck[4] == 'n') {
                    if (cursor.moveToFirst()) {
                        do {
                            if (username.equals(cursor.getString(1))) {
                                aaa = username;
                                Toast.makeText(Register.this, "Already registered", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        } while (cursor.moveToNext());
                        cursor.moveToFirst();
                    }

                    if (aaa == null) {
                        database.execSQL("INSERT INTO Admin (username, mail, password) VALUES ('"+username+"', '"+mail+"', '"+password+"')");
                        Toast.makeText(Register.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    if (cursor.moveToFirst()) {
                        do {
                            if (username.equals(cursor.getString(1))) {
                                aaa1 = username;
                                Toast.makeText(Register.this, "Already registered", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        } while (cursor.moveToNext());
                        cursor.moveToFirst();
                    }

                    if (aaa1 == null) {
                        database.execSQL("INSERT INTO User (username, mail, password) VALUES ('"+username+"', '"+mail+"', '"+password+"')");
                        Toast.makeText(Register.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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
            Toast.makeText(this, "Item Two Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}