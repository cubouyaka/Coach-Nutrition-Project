package com.oc.rss.coach_nutrition_project;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class show_food_db extends BaseActivity {

    private String authority;

    private SimpleCursorAdapter listAdapter;
    private EditText editQuantity;
    private ListView listFood;
    private TextView textCalorie;

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

        toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

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
    public int getLayoutResource() {
        return R.layout.activity_show_food_db;
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
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            Toast.makeText(this, "Meal added !", Toast.LENGTH_LONG).show();
            finish();
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
