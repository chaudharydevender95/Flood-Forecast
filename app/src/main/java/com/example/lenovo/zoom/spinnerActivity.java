package com.example.lenovo.zoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class spinnerActivity extends Activity {

    private Spinner spinner1, spinner2, spinner3;
    private Button btnSubmit;

    static HashMap<String,Integer> imageMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);


        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner)findViewById(R.id.spinner3);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);

        imageMap = new HashMap<>();

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,startPage.dates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);

        imageMap.put("1788",R.drawable.ia2);
        imageMap.put("2020",R.drawable.ia2_33);
        imageMap.put("3040",R.drawable.ia5);
        imageMap.put("3860",R.drawable.ia10);
        imageMap.put("4630",R.drawable.ia20);
        imageMap.put("5060",R.drawable.ia30);
        imageMap.put("5370",R.drawable.ia40);
        imageMap.put("5550",R.drawable.ia50);
        imageMap.put("6080",R.drawable.ia80);
        imageMap.put("6308",R.drawable.ia100);
        imageMap.put("7220",R.drawable.ia250);
        imageMap.put("7895",R.drawable.ia500);
        imageMap.put("8286",R.drawable.ia750);
        imageMap.put("8562",R.drawable.ia1000);

        btnSubmit = (Button)findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent  = new Intent(spinnerActivity.this,zoomActivity.class);
                int discharge = Integer.parseInt(startPage.valueMap.get(spinner3.getSelectedItem()));
                intent.putExtra("discharge",discharge);
                startActivity(intent);
            }

        });


    }



    // add items into spinner dynamically
    public void addItemsOnSpinner2() {


        List<String> list = new ArrayList<String>();
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {

        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(spinnerActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
                                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        spinnerActivity.super.finish();
                    }
                }).setNegativeButton("No", null).show();
    }
}
