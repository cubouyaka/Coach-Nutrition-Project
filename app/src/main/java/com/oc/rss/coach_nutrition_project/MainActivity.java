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

    private Button b_go;
    private Button b_settings ;
    private static int OK_CODE = 1;
    public int min_daily, max_daily;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClickMeal (View view){
        Intent intent = new Intent (this, add_food.class);
        startActivity (intent);
    }

    public void onClickHistory (View view){
        Intent intent = new Intent (this, show_food_db.class);
        startActivity (intent);
    }

    public void onClickSettings (View v) {

        Intent intent = new Intent (this, AllSettings.class);
        startActivity (intent);
    }

    //startActivityForResult(new Intent(this, settings.class), OK_CODE);

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == OK_CODE){
                min_daily = Integer.parseInt(data.getStringExtra("min"));
                max_daily = Integer.parseInt(data.getStringExtra("max"));
            }
        }
    }
    */
}
