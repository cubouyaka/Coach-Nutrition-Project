package com.oc.rss.coach_nutrition_project;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AllSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_all_settings_land);
        else //if the screen is not in landscape orientation
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

        Intent intent = new Intent (this, SearchFile.class);
        startActivity (intent);
    }

    public void onClickUploadCSV (View v) {

        Intent intent = new Intent (this, AllSettings.class);
        startActivity (intent);
    }

    public void onClickDeleteHistory (View v) {

        CalorieManager.getInstance (this).resetHistory ();
        CalorieManager.getInstance (this).saveHistory ();
        Toast.makeText(this, "History deleted!", Toast.LENGTH_LONG).show();
    }

    public void onClickDeleteDatabase (View v) {

        MySQLiteHelper.getInstance(this).refreshDataBase();
        Toast.makeText(this, "Database deleted!", Toast.LENGTH_LONG).show();
    }
}
