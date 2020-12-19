package com.stubborn.insertingmultipledatatosqlite;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    String[] PERMISSIONS = {Manifest.permission.READ_SMS};

    long rowId;

    //FirebaseFirestore fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lViewSMS = (ListView) findViewById(R.id.listViewSMS);
        if (fetchInbox() != null) {
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fetchInbox());
            lViewSMS.setAdapter(adapter);
        }
    }


    public ArrayList fetchInbox() {
        ArrayList sms = new ArrayList();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            requestContactsPermissions();

            return requestContactsPermissions();
        }

        Uri uriSms = Uri.parse("content://sms/inbox");
//        Cursor cursor = getContentResolver().query(uriSms,
//                new String[]{"_id", "address = '+918484934914'", "date", "body"}, null, null, null);

        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};

        //for fetching particular contact sms sending value to querry
        Cursor cursor = getContentResolver().query(uriSms, projection, "address='+918484934914'", null, "date desc");

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String address = cursor.getString(1);
            String body = cursor.getString(3);
            long longdate = cursor.getLong(4);
            // String deafult_add = "+918484934914";

            String finalDate;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(longdate);
            Date date = calendar.getTime();
            finalDate = date.toString();

            Log.e("adress****************", finalDate + "");
            Log.e("adress****************", address + "");
            Log.e("body*****************", body + "");


            sms.add("Date = " + finalDate + "\n Address = " + address + "\n" + "n SMS =  " + body);
            sendingSmsToFirestore(finalDate, body);
        }
        return sms;

    }

    private void sendingSmsToFirestore(String finalDate, String body) {

        String uuid = UUID.randomUUID().toString();
        FieldValue.serverTimestamp();
        Modal modal = new Modal(finalDate, uuid, body, FieldValue.serverTimestamp());


        //before sending sms to firestore we should first check that data is already exist or not for that reating sqlite database and checking with that
        //for that first add data to local databse and then fetch the data and compaire

        //adding DATA to databse
        //creating helper object
        DbHelper dbHelper = new DbHelper (this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (int i = 0; i <= modal.getId().length(); i++) {
            values.put(modal.getId(), "sms_uuid");
            values.put(modal.getDatabase_date(), "sms_time");
            values.put(modal.getSms_body(), "sms_body");
            rowId= db.insert("SMS", null, values);

        }
        //passing data thrugh querry
        if (rowId == -1) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "data added to the database successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList requestContactsPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }
        return null;
    }

}


