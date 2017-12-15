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
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class show_food_db extends AppCompatActivity {

    private String authority;

    private Spinner spinner;
    private SimpleCursorAdapter spinnerAdapter;
    private SimpleCursorAdapter listAdapter;
    private ListView titre;
    private long spinnerID;

    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_food_db);

        authority = getResources().getString(R.string.authority);
        spinner = (Spinner) findViewById(R.id.spinner);

        spinnerAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_item, null,
                new String[]{"Name"},
                new int[]{android.R.id.text1}, 0);
        spinner.setAdapter(spinnerAdapter);

        titre = (ListView) findViewById(R.id.list);
        listAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, null,
                new String[]{"Calorie"},
                new int[]{android.R.id.text1}, 0);
        titre.setAdapter(listAdapter);

        getLoaderManager().initLoader(0, null,
                new LoaderManager.LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                Uri uri;
                Uri.Builder builder = new Uri.Builder();
                uri = builder.scheme("content")
                        .authority(authority)
                        .appendPath("food")
                        .build();

                return new CursorLoader(getApplicationContext(), uri, new String[]{"_id", "Name", "Calorie"},
                        null, null, null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                spinnerAdapter.swapCursor(data);
                spinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                spinnerAdapter.swapCursor(null);
                spinnerAdapter.notifyDataSetChanged();
            }
        });
    }

    public void afficher(View view) {

        long id = spinner.getSelectedItemId();
        Log.d("Debug","afficher");
        Toast.makeText(getApplicationContext(), "id=" + id, Toast.LENGTH_SHORT).show();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content")
                .authority(authority)
                .appendPath("food");
        builder = ContentUris.appendId(builder, id);
        final Uri uri = builder.build();
        Log.d("uri=", uri.toString());

        // Pour alimenter la liste de titres il faut utiliser
        // restartLoader() et non pas initLoader().
        // Quand on change l'auteur alors initLoader ne  reinitialise pas
        // le chargement de donnees.
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
