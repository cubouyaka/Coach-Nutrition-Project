package com.oc.rss.coach_nutrition_project;

import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class show_food_db extends AppCompatActivity {

    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_food_db);

        /*MySQLiteHelper helper = new MySQLiteHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        arrayList = new ArrayList<>();
        ListView listView = (ListView)findViewById(R.id.listView);

        ListAdapter adapter = new SimpleCursorAdapter(this,R.layout.activity_show_food_db)*/

    }
}
