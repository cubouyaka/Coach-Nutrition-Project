package com.oc.rss.coach_nutrition_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.TimeZone;

public class GetDate extends AppCompatActivity {

    private DatePicker datePicker;

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

        // Get time from datePicker in ms
        int day = datePicker.getDayOfMonth ();
        int month = datePicker.getMonth ();
        int year =  datePicker.getYear ();

        Calendar calendar = Calendar.getInstance ();
        calendar.set(year, month, day);

        calendar.getTime();

        Intent intent = new Intent (this, show_food_db.class);
        startActivity (intent);
    }
}
