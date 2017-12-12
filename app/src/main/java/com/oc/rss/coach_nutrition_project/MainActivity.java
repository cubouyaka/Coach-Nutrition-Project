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

    public void onClick(View v){
        Intent i = new Intent(this,add_food.class);
        startActivity(i);
    }
}
