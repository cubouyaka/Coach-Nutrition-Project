package com.oc.rss.coach_nutrition_project;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    private TextView calorie;
    int OK_CODE = 1;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_main_land);
        else //if the screen is not in landscape orientation
            setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById (R.id.toolBar);
        calorie = (TextView) findViewById (R.id.calories);

        CalorieManager.getInstance (this).loadHistory ();

        // Initialize today's calories value
        calorie.setText ("" + CalorieManager.getInstance (this).getTodayCalorieValue ());

        setSupportActionBar(toolbar);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
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
    }

}
