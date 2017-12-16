package com.oc.rss.coach_nutrition_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AllSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_settings);
    }

    public void onClickDailyGoal (View v) {

        Intent intent = new Intent (this, settings.class);
        startActivity (intent);
    }

    public void onClickAddFood (View v) {

        Intent intent = new Intent (this, add_food.class);
        startActivity (intent);
    }

    public void onClickImportCSV (View v) {

        Intent intent = new Intent (this, AllSettings.class);
        startActivity (intent);
    }

    public void onClickUploadCSV (View v) {

        Intent intent = new Intent (this, AllSettings.class);
        startActivity (intent);
    }
}
