package com.example.lenovo.zoom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


public class zoomActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ZoomageView demoView;
    private View optionsView;
    private AlertDialog optionsDialog;
    int discharge,count = 0,arraylength, minImg,maxImg;
    Button maxButton,minButton;
    TextView viewText;
    int dischargeArray[] = {1788,2020,3040,3860,4630,5060,5370,5550,6080,6308,7220,7895,8286,8562};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);

        demoView = (ZoomageView)findViewById(R.id.demoView);
        maxButton = (Button)findViewById(R.id.maxButton);
        minButton = (Button)findViewById(R.id.minButton);
        viewText = (TextView)findViewById(R.id.viewText);

        Intent i = getIntent();
        discharge = i.getIntExtra("discharge",2000);
        arraylength = dischargeArray.length;

        while(count<arraylength && discharge>=dischargeArray[count]){
            count++;
        }
        maxImg = count;
        if(count==0) minImg = maxImg;
        else if(count==arraylength){
            maxImg = maxImg-1;
            minImg = maxImg;
        }else minImg = maxImg-1;

        demoView.setImageResource(spinnerActivity.imageMap.get((""+dischargeArray[maxImg])));
        viewText.setText("MAX");

        prepareOptions();

        maxButton.setOnClickListener(this);
        minButton.setOnClickListener(this);


    }

    private void prepareOptions() {
        optionsView = getLayoutInflater().inflate(R.layout.zoomage_options, null);
        setSwitch(R.id.zoomable, demoView.isZoomable());
        setSwitch(R.id.translatable, demoView.isTranslatable());
        setSwitch(R.id.animateOnReset, demoView.getAnimateOnReset());
        setSwitch(R.id.autoCenter, demoView.getAutoCenter());
        setSwitch(R.id.restrictBounds, demoView.getRestrictBounds());
        optionsView.findViewById(R.id.reset).setOnClickListener(this);
        optionsView.findViewById(R.id.autoReset).setOnClickListener(this);

        optionsDialog = new AlertDialog.Builder(this).setTitle("Zoomage Options")
                .setView(optionsView)
                .setPositiveButton("Close", null)
                .create();
    }

    private void setSwitch(int id, boolean state) {
        final SwitchCompat switchView = (SwitchCompat) optionsView.findViewById(id);
        switchView.setOnCheckedChangeListener(this);
        switchView.setChecked(state);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (!optionsDialog.isShowing()) {
            optionsDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.zoomable:
                demoView.setZoomable(isChecked);
                break;
            case R.id.translatable:
                demoView.setTranslatable(isChecked);
                break;
            case R.id.restrictBounds:
                demoView.setRestrictBounds(isChecked);
                break;
            case R.id.animateOnReset:
                demoView.setAnimateOnReset(isChecked);
                break;
            case R.id.autoCenter:
                demoView.setAutoCenter(isChecked);
                break;
        }
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.reset) {
            demoView.reset();
        }
        if(v==maxButton){
            demoView.setImageResource(spinnerActivity.imageMap.get((""+dischargeArray[maxImg])));
            viewText.setText("MAX");
        }
        if(v==minButton){
            demoView.setImageResource(spinnerActivity.imageMap.get((""+dischargeArray[minImg])));
            viewText.setText("MIN");
        }
    }

    private void showResetOptions() {
        CharSequence[] options = new CharSequence[]{"Under", "Over", "Always", "Never"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                demoView.setAutoResetMode(which);
            }
        });

        builder.create().show();
    }
}
