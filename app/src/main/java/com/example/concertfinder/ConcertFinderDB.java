package com.example.concertfinder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConcertFinderDB extends SQLiteOpenHelper {
    public ConcertFinderDB(Context context) { super(context, "ConcertFinderDB", null, 1);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE User " +
                "(UserID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "mail TEXT, " +
                "password TEXT)";

        String query1 = "CREATE TABLE Admin " +
                "(UserID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "mail TEXT, " +
                "password TEXT)";

        String query2 = "CREATE TABLE Concert " +
                "(ConcertID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cName TEXT, " +
                "artistName TEXT, " +
                "genre TEXT, " +
                "cDate DATE, " +
                "cTime TIME)";

        String query3 = "CREATE TABLE UserFav " +
                "(ufID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "uID INTEGER, " +
                "cID INTEGER, " +
                "ucGenre TEXT, " +
                "ufuName TEXT, " +
                "ufcName TEXT, " +
                "FOREIGN KEY (uID) REFERENCES User (UserID)," +
                "FOREIGN KEY (cID) REFERENCES Concert (ConcertID))";

        String query4 = "CREATE TABLE UserAttending " +
                "(uaID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "u1ID INTEGER, " +
                "c1ID INTEGER, " +
                "uaGenre TEXT, " +
                "uauName TEXT, " +
                "uacName TEXT, " +
                "FOREIGN KEY (u1ID) REFERENCES User (UserID)," +
                "FOREIGN KEY (c1ID) REFERENCES Concert (ConcertID))";

        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Admin");
        db.execSQL("DROP TABLE IF EXISTS Concert");
        db.execSQL("DROP TABLE IF EXISTS UserFav");
        db.execSQL("DROP TABLE IF EXISTS UserAttending");
        onCreate(db);
    }
}
