package com.oc.rss.coach_nutrition_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;
import java.util.TimeZone;

import static java.lang.Math.floor;

public class CalorieManager {

    static CalorieManager instance = null;
    static AppCompatActivity caller = null;

    public int calorie[] = new int[7];
    public long timestamp;
    public int current_date_index = 0;
    public int minCalorie = 0;
    public int maxCalorie = 0;

    // Get instance of the calorie manager
    static CalorieManager getInstance(AppCompatActivity app) {

        caller = app;

        if (null == instance)
            instance = new CalorieManager();

        return instance;
    }

    // Set actual index of date, this index will be used to add calories to a previous day
    void setDateIndex(int day, int month, int year) {

        Calendar calendarNow = Calendar.getInstance(TimeZone.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        // Days are different
        if (!isSameDay(calendarNow, calendar))
            current_date_index = getDistDate (calendarNow, calendar);
        else
            current_date_index = 0;
    }

    // Add calorie value in the history
    void addCalorieValue(int amount) {

        calorie[current_date_index] += amount;
        saveHistory ();
    }

    // Return calorie value of the last day
    int getTodayCalorieValue() {

        return calorie[0];
    }

    // Return calorie value of the actual indexed day
    int getCurrentCalorieValue() {

        return calorie[current_date_index];
    }

    // Check if two date are different
    boolean isSameDay(Calendar c1, Calendar c2) {

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    }

    // Get day difference between two date
    int getDistDate(Calendar c1, Calendar c2) {

         return (int) floor ((double) (c1.getTimeInMillis () - c2.getTimeInMillis ()) /
                (1000 * 60 * 60 * 24));
    }

    // Load history of calorie
    void loadHistory () {

        SharedPreferences pref = caller.getSharedPreferences("OBJECTIVE", Context.MODE_PRIVATE);

        minCalorie = pref.getInt ("min", caller.getResources ().getInteger (R.integer.minCalorie));
        maxCalorie = pref.getInt ("max", caller.getResources ().getInteger (R.integer.maxCalorie));

        // Load the calorie history
        for (int i = 0 ; i < 7 ; i++)
            calorie[i] = pref.getInt ("c" + i, 0);

        // Load the last day the app was used
        long last = pref.getLong ("last", 0);

        // Convert the last date into a human readable date
        Calendar calendarLast = Calendar.getInstance();
        calendarLast.setTimeInMillis (last);

        // Compare it with the actual day and store actual timestamp
        Calendar calendarNow  = Calendar.getInstance (TimeZone.getDefault ());

        if (!isSameDay (calendarNow, calendarLast)) {

            // We need to update the history

            // First we need to evaluate the gap between the last time the user used the application
            int gap = getDistDate (calendarNow, calendarLast);

            // Even if the gap is null, we need to shift at least one day
            if (gap == 0) gap = 1;

            // Shift the history 'gap' times
            for (int i = 0 ; i < 7 && i < gap ; i++)
                shiftHistory ();

            // Finally we save the history
            saveHistory ();
        }
    }

    // Shift one day of the history
    void shiftHistory () {

        int tmp[] = new int[7];

        // Set the last day to 0
        tmp[0] = 0;

        // Shift the old array
        for (int i = 1 ; i < 7 ; i++)
            tmp[i] = calorie[i - 1];

        // Replace the old values by the new ones (don't need to busy the GC)
        for (int i = 0 ; i < 0 ; i++)
            calorie[i] = tmp[i];
    }

    // Store history of calories in SharedPreferences
    void saveHistory () {

        SharedPreferences pref = caller.getSharedPreferences("OBJECTIVE", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit=pref.edit();

        prefEdit.putLong ("last", timestamp);

        for (int i = 0 ; i < 7 ; i++)
            prefEdit.putInt ("c" + i, calorie[i]);

        prefEdit.apply();
    }
}
