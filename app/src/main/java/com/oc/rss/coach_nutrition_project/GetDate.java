package com.oc.rss.coach_nutrition_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.TimeZone;

public class GetDate extends AppCompatActivity {

    private DatePicker datePicker;
    int SHOW_FOOD_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_date);

        datePicker = (DatePicker) findViewById (R.id.datePicker);

        // Set date to current day
        long time = System.currentTimeMillis ();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        datePicker.updateDate (
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        // Set a range of 7 days
        datePicker.setMinDate (calendar.getTimeInMillis () - (1000 * 60 * 60 * 24 * 6));
        datePicker.setMaxDate (calendar.getTimeInMillis () + (1000 * 60 * 60 * 24));
    }

    public void onClickOk (View view){

        // Store time selected by the user
        int day   = datePicker.getDayOfMonth ();
        int month = datePicker.getMonth ();
        int year  =  datePicker.getYear ();

        CalorieManager.getInstance (this).setDateIndex (day, month, year);

        Intent intent = new Intent (this, show_food_db.class);
        startActivityForResult (intent,SHOW_FOOD_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == SHOW_FOOD_REQUEST)
            if (resultCode == RESULT_OK)
                finish();
    }
}
