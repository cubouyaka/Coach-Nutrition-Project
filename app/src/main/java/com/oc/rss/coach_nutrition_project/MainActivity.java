package com.oc.rss.coach_nutrition_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView calorie;
    int OK_CODE = 1;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        toolbar = (Toolbar) findViewById (R.id.toolBar);
        calorie = (TextView) findViewById (R.id.calories);

        CalorieManager.getInstance (this).loadHistory ();

        // Initialize today's calories value
        calorie.setText ("" + CalorieManager.getInstance (this).getTodayCalorieValue ());

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClickMeal (View view){

        startActivityForResult(new Intent(this, GetDate.class), OK_CODE);
    }

    public void onClickHistory (View view){
        Intent intent = new Intent (this, History.class);
        startActivity (intent);
    }

    public void onClickSettings (View v) {

        Intent intent = new Intent (this, AllSettings.class);
        startActivity (intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Initialize today's calories value
        calorie.setText ("" + CalorieManager.getInstance (this).getTodayCalorieValue ());

        /*
        if (requestCode == 1) {
            if(resultCode == OK_CODE){
                min_daily = Integer.parseInt(data.getStringExtra("min"));
                max_daily = Integer.parseInt(data.getStringExtra("max"));
            }
        }
        */
    }
}
