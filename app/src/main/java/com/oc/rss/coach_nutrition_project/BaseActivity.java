package com.oc.rss.coach_nutrition_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        setToolBar();
    }

    protected abstract int getLayoutResource();

    private void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onClickItemMeal(MenuItem item){
        startActivityForResult(new Intent(this, GetDate.class), RESULT_OK);
    }

    public void onClickItemHistory (MenuItem item){
        Intent intent = new Intent (this, History.class);
        startActivity (intent);
    }
    public void onClickItemSettings (MenuItem item) {
        Intent intent = new Intent (this, AllSettings.class);
        startActivity (intent);
    }

    public void onClickItemAddFood (MenuItem item) {
        Intent intent = new Intent (this, add_food.class);
        startActivity (intent);
    }

    public void onClickItemImportCSV (MenuItem item) {
        Intent intent = new Intent (this, SearchFile.class);
        startActivity (intent);
    }
}
