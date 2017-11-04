package com.example.lenovo.zoom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class startPage extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    static ArrayList<String> dates;
    ListView list;
    int count = 0;
    static HashMap<String,String> valueMap;
    DataSnapshot data;
    boolean isfirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://flood-plane-map.firebaseio.com/");
        dates = new ArrayList<>();
        valueMap = new HashMap<>();

        LoadList();
    }
    void LoadList(){
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dates.add(dataSnapshot.getKey());
                valueMap.put(dataSnapshot.getKey(),dataSnapshot.getValue().toString());
                count++;

                if(count>=dataSnapshot.getChildrenCount()){
                    Collections.sort(dates, new Comparator<String>() {
                        DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
                        @Override
                        public int compare(String t, String t1) {
                            try {
                                return f.parse(t).compareTo(f.parse(t1));
                            } catch (ParseException ex) {
                                throw new IllegalArgumentException(ex);
                            }
                        }
                    });
                    if(isfirst){
                        isfirst=false;
                        startActivity(new Intent(getApplicationContext(),spinnerActivity.class));
                    }
                    else
                        finish();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
