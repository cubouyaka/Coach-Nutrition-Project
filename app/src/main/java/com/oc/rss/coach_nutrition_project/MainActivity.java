package com.oc.rss.coach_nutrition_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        go = (Button)findViewById(R.id.b_add_food);
    }

    public void onClickAddFood (View view){
        Intent intent = new Intent (this, add_food.class);
        startActivity (intent);
    }

    public void onClickShowFood (View view){
        Intent intent = new Intent (this, show_food_db.class);
        startActivity (intent);
    }
}
