package com.oc.rss.coach_nutrition_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class History extends AppCompatActivity {

    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        drawView = new DrawView(this);
        drawView.setBackgroundColor (getResources ().getColor (R.color.colorBackground));
        setContentView(drawView);
    }
}
