package com.example.concertfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HeavyMetalEvent extends AppCompatActivity {

    String username;
    public static final int N_ID = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heavy_metal_event);

        SQLiteDatabase db;
        ConcertFinderDB concertFinderDB = new ConcertFinderDB(this);

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.ahme);

        ImageView imageView = findViewById(R.id.bandImage);
        TextView cName = findViewById(R.id.cname);
        TextView cArtist = findViewById(R.id.cartist);
        TextView cGenre = findViewById(R.id.cgenre);
        TextView cDate = findViewById(R.id.cdate);
        TextView cTime = findViewById(R.id.ctime);
        //Button cPlace = findViewById(R.id.cplace);
        Button cAddBtn = findViewById(R.id.cAdd);
        Button cFavBtn = findViewById(R.id.cFav);
        String selectedCName;

        String ccName;
        String ccArtist;
        String ccGenre;
        String ccDate;
        String ccTime;
        //String ccPlace;

        db = concertFinderDB.getReadableDatabase();

        Intent intent = getIntent();
        selectedCName = intent.getStringExtra("cName");
        username = intent.getStringExtra("Username");

        Cursor cursor = db.rawQuery("SELECT Concert.cName FROM Concert WHERE Concert.cName = '"+selectedCName+"'", null);
        Cursor cursor1 = db.rawQuery("SELECT Concert.artistName FROM Concert WHERE Concert.cName = '"+selectedCName+"'", null);
        Cursor cursor2 = db.rawQuery("SELECT Concert.genre FROM Concert WHERE Concert.cName = '"+selectedCName+"'", null);
        Cursor cursor3 = db.rawQuery("SELECT Concert.cDate FROM Concert WHERE Concert.cName = '"+selectedCName+"'", null);
        Cursor cursor4 = db.rawQuery("SELECT Concert.cTime FROM Concert WHERE Concert.cName = '"+selectedCName+"'", null);
        //Cursor cursor6 = db.rawQuery("SELECT Concert.cPlace FROM Concert WHERE Concert.cName = '"+selectedCName+"'", null);

        cursor.moveToFirst();
        ccName = cursor.getString(0);
        cursor1.moveToFirst();
        ccArtist = cursor1.getString(0);
        cursor2.moveToFirst();
        ccGenre = cursor2.getString(0);
        cursor3.moveToFirst();
        ccDate = cursor3.getString(0);
        cursor4.moveToFirst();
        ccTime = cursor4.getString(0);
        //cursor6.moveToFirst();
        //ccPlace = cursor6.getString(0);

        if (ccArtist.equals("Metallica")) {
            imageView.setImageResource(R.drawable.metallica);
            constraintLayout.setBackgroundColor(Color.YELLOW);
        } else if (ccArtist.equals("Megadeth")) {
            imageView.setImageResource(R.drawable.megadeth);
            constraintLayout.setBackgroundColor(Color.DKGRAY);
        } else if (ccArtist.equals("Like Moths To Flames")) {
            imageView.setImageResource(R.drawable.lmtf);
            constraintLayout.setBackgroundColor(Color.parseColor("#cf8908"));
        } else if (ccArtist.equals("Avenged Sevenfold")) {
            imageView.setImageResource(R.drawable.a7x);
            constraintLayout.setBackgroundColor(Color.parseColor("#ffe18f"));
        } else {
            imageView.setImageResource(0);
        }

        String[] cData = {ccName, ccArtist, ccGenre, ccDate, ccTime};

        cName.setText(cData[0]);
        cArtist.setText(cData[1]);
        cDate.setText(cData[2]);
        cTime.setText(cData[3]);
        cGenre.setText(cData[4]);

        /*int c = 0;

        Cursor cursor7 = db.rawQuery("SELECT UserAttending.* FROM UserAttending", null);
        if (cursor7.moveToFirst()) {
            c = c + 1;
        } while (cursor7.moveToNext());

        if (c >= 300000) {
            cAddBtn.setEnabled(false);
        } else { cAddBtn.setEnabled(true); }

        Cursor c1 = db.rawQuery("SELECT UserAttending.uauName FROM UserAttending WHERE UserAttending.uauName = '"+username+"'", null);

        c1.moveToFirst();

        if (!c1.isNull(0)) {
            cAddBtn.setEnabled(false);
        } else { cAddBtn.setEnabled(true); }*/

        cAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor5 = db.rawQuery("SELECT User.UserID FROM User WHERE User.username = '"+username+"'", null);
                Cursor cursor6 = db.rawQuery("SELECT Concert.ConcertID FROM Concert WHERE Concert.cName = '"+ccName+"'", null);

                cursor5.moveToFirst();
                int uid = cursor5.getInt(0);
                cursor6.moveToFirst();
                int cid = cursor6.getInt(0);

                db.execSQL("INSERT INTO UserAttending (u1ID, c1ID, uaGenre, uauName, uacName) VALUES ('"+uid+"', '"+cid+"','"+ccGenre+"', '"+username+"', '"+ccName+"')");
                //Toast.makeText(HeavyMetalEvent.this, "Succesfully added", Toast.LENGTH_SHORT).show();

                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                String CHANNEL_ID = "My_Channel";
                CharSequence name = "MyChannel";
                String desc = "This is a channel";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                    channel.setDescription(desc);
                    manager.createNotificationChannel(channel);
                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(HeavyMetalEvent.this, CHANNEL_ID)
                        .setContentTitle("Concert attendance booked")
                        .setContentText("This concert will take place on " + ccDate)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.ic_launcher_foreground);
                Notification notification = builder.build();

                manager.notify(N_ID, notification);
            }
        });

        cFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor5 = db.rawQuery("SELECT User.UserID FROM User WHERE User.username = '"+username+"'", null);
                Cursor cursor6 = db.rawQuery("SELECT Concert.ConcertID FROM Concert WHERE Concert.cName = '"+ccName+"'", null);

                cursor5.moveToFirst();
                int uid = cursor5.getInt(0);
                cursor6.moveToFirst();
                int cid = cursor6.getInt(0);

                db.execSQL("INSERT INTO UserFav (uID, cID, ucGenre, ufuName, ufcName) VALUES ('"+uid+"', '"+cid+"', '"+ccGenre+"', '"+username+"', '"+ccName+"')");
                Toast.makeText(HeavyMetalEvent.this, "Succesfully added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}