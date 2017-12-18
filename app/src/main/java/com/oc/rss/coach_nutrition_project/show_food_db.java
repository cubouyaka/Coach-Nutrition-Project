package com.oc.rss.coach_nutrition_project;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class show_food_db extends AppCompatActivity {

    private String authority;

    private Spinner spinner;
    private SimpleCursorAdapter spinnerAdapter;
    private SimpleCursorAdapter listAdapter;
    private EditText editQuantity;
    private ListView listFood;
    private TextView textCalorie;
    // private long spinnerID;

    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_food_db);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        authority = getResources().getString(R.string.authority);

        listFood = (ListView) findViewById(R.id.list);
        textCalorie = (TextView) findViewById (R.id.calories);

        // Initialize current calories value
        textCalorie.setText ("" + CalorieManager.getInstance (this).getCurrentCalorieValue ());

        editQuantity = (EditText) findViewById (R.id.quantity);

        listFood.setChoiceMode (AbsListView.CHOICE_MODE_SINGLE);

        listAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_activated_2, null,
                new String[]{"Name", "Calorie"},
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
        listFood.setAdapter(listAdapter);

        displayFood ();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void addFoodToHistory (View v) {

        // Get position of the selected meal
        int position = listFood.getCheckedItemPosition ();

        // If a meal is selected
        if (position >= 0 && editQuantity.getText ().length () > 0) {

            Cursor cursor = (Cursor) listFood.getItemAtPosition (position);

            int calorie = Integer.parseInt (cursor.getString (2));
            float quantity = Float.parseFloat (editQuantity.getText ().toString ());

            // Compute the right amount of calorie
            int amount = (int) ((float) calorie * quantity);

            // Update calorie value
            CalorieManager.getInstance (this).addCalorieValue (amount);

            // Initialize current calories value
            textCalorie.setText ("" + CalorieManager.getInstance (this).getCurrentCalorieValue ());
        }
    }

    public void displayFood () {

        final Uri uri;
        Uri.Builder builder = new Uri.Builder();
        uri = builder.scheme("content")
                .authority(authority)
                .appendPath("food")
                .build();

        getLoaderManager().restartLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(getApplicationContext(),
                        uri, null, null, null, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                listAdapter.swapCursor(data);
                listAdapter.notifyDataSetChanged();
                Log.d("Display food","onLoadFinished");
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                listAdapter.swapCursor(null);
                listAdapter.notifyDataSetChanged();
                Log.d("Display food","onLoaderReset");
            }
        });
    }
}
