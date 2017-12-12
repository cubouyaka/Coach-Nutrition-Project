package com.oc.rss.coach_nutrition_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b_go;
    Button b_settings ;
    private static int OK_CODE = 1;
    public int min_daily, max_daily;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_go = (Button)findViewById(R.id.b_add_food);
        b_settings = (Button)findViewById(R.id.b_settings);
        textView = (TextView)findViewById(R.id.t);
    }

    public void onClick(View v){
        Intent i = new Intent(this,add_food.class);
        startActivity(i);
    }

    public void buttonSettings(View v) {
        startActivityForResult(new Intent(this, settings.class), OK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == OK_CODE){
                min_daily = Integer.parseInt(data.getStringExtra("min"));
                max_daily = Integer.parseInt(data.getStringExtra("max"));
                textView.setText(min_daily+" "+ max_daily);
            }
        }
    }

}
