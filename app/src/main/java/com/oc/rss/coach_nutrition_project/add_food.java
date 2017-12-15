package com.oc.rss.coach_nutrition_project;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.view.View.INVISIBLE;

public class add_food extends AppCompatActivity {

    EditText ed_name;
    EditText ed_calorie;
    EditText ed_lipids;
    EditText ed_carbohydrates;
    EditText ed_protein;
    CheckBox full_opt;// (default=false) true if we want to enter everything (protein,lipids...)
    LinearLayout layout_opt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_add_food_land);
        else //if the screen is not in landscape orientation
            setContentView(R.layout.activity_add_food);
        layout_opt = (LinearLayout)findViewById(R.id.layout_opt);
        layout_opt.setVisibility(View.INVISIBLE); //invisible by default
        ed_name = (EditText)findViewById(R.id.ed_name);
        ed_calorie = (EditText)findViewById(R.id.ed_calorie);
        ed_lipids = (EditText)findViewById(R.id.ed_lipids);
        ed_carbohydrates = (EditText)findViewById(R.id.ed_carbohydrates);
        ed_protein = (EditText)findViewById(R.id.ed_protein);
        full_opt = (CheckBox)findViewById(R.id.cb_full_opt);
        full_opt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
                if(isChecked)
                    layout_opt.setVisibility(View.VISIBLE);
                else
                    layout_opt.setVisibility(View.INVISIBLE);
            }
        });
        if(savedInstanceState != null){
            ed_name.setText(savedInstanceState.getString("name"," "));
            ed_calorie.setText(savedInstanceState.getString("calorie"," "));
            ed_lipids.setText(savedInstanceState.getString("lipids"," "));
            ed_carbohydrates.setText(savedInstanceState.getString("carbohydrates"," "));
            ed_protein.setText(savedInstanceState.getString("protein"," "));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("name",ed_name.getText().toString());
        outState.putString("calorie",ed_calorie.getText().toString());
        outState.putString("lipids",ed_lipids.getText().toString());
        outState.putString("carbohydrates",ed_carbohydrates.getText().toString());
        outState.putString("protein",ed_protein.getText().toString());
    }

    public boolean addRow(String name, int calorie, int lipids, int carbohydrates, int protein){

        MySQLiteHelper helper = new MySQLiteHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = { MySQLiteHelper.NAME };
        String selection = MySQLiteHelper.NAME + " =?";
        String[] selectionArgs = { name };
        String limit = "1";

        Cursor cursor = db.query(MySQLiteHelper.FOOD, columns, selection, selectionArgs, null, null, null, limit);

        //check if the entry is already in the DB
        /* String Query = "Select * from " + MySQLiteHelper.FOOD + " where name=%s";
        Cursor cursor = db.rawQuery("Select 1 from " + MySQLiteHelper.FOOD + " where name=%s", new String[] {name});*/
        if(cursor == null)
            return false;
        if(cursor.getCount() > 0){
            makeToast(name+" is already in the DataBase.");
            cursor.close();
            return false;
        }
        cursor.close();
        ContentValues row = new ContentValues();
        row.put(MySQLiteHelper.NAME,name);
        row.put(MySQLiteHelper.CALORIE,calorie);
        row.put(MySQLiteHelper.LIPIDS,lipids);
        row.put(MySQLiteHelper.CARBOHYDRATES,carbohydrates);
        row.put(MySQLiteHelper.PROTEIN,protein);

        if(db.insert(MySQLiteHelper.FOOD, null, row) == -1)
            Log.e("ERROR ADD DB","Error while trying to add "+name+" in data base");
        else{
            makeToast(name+"("+calorie+","+lipids+","+carbohydrates+","+
                    protein+") add to "+MySQLiteHelper.FOOD);
            return true;
        }
        return false;
    }

    public boolean addRow(String name, int calorie){

        MySQLiteHelper helper = MySQLiteHelper.getInstance(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        //check if the entry is already in the DB
        String[] columns = { MySQLiteHelper.NAME };
        String selection = MySQLiteHelper.NAME + " =?";
        String[] selectionArgs = { name };
        String limit = "1";

        Cursor cursor = db.query(MySQLiteHelper.FOOD, columns, selection, selectionArgs,
                null, null, null, limit);
        if(cursor == null)
            return false;
        if(cursor.getCount() > 0){
            makeToast(name+" is already in the DataBase.");
            cursor.close();
            return false;
        }
        cursor.close();

        //insert in the DB
        ContentValues row = new ContentValues();
        row.put(MySQLiteHelper.NAME,name);
        row.put(MySQLiteHelper.CALORIE,calorie);

        if(db.insert(MySQLiteHelper.FOOD, null, row) == -1)
            Log.e("ERROR ADD DB","Error while trying to add "+name+" in data base");
        else{
            makeToast(name+"("+calorie+") add to "+MySQLiteHelper.FOOD);
            return true;
        }
        return false;
    }

    public void buttonAdd(View v){
        String name = ed_name.getText().toString();
        String calorie = ed_calorie.getText().toString();
        if(full_opt.isChecked()){
            String lipids = ed_lipids.getText().toString();
            String carbohydrates = ed_carbohydrates.getText().toString();
            String protein = ed_protein.getText().toString();
            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(calorie) ||
                    TextUtils.isEmpty(lipids) || TextUtils.isEmpty(carbohydrates) ||
                    TextUtils.isEmpty(protein))
                makeToast("Please fill all entries");
            else
                addRow(name,Integer.parseInt(calorie),Integer.parseInt(lipids),
                        Integer.parseInt(carbohydrates),Integer.parseInt(protein));
        }else{
            if(TextUtils.isEmpty(calorie) || TextUtils.isEmpty(name))
                makeToast("Please enter a name AND a calorie value at least");
            else
                addRow(name,Integer.parseInt(calorie));
        }
        finish();
    }


    public void makeToast(String s) {
        Toast t = Toast.makeText(this,s,Toast.LENGTH_LONG);
        t.show();
    }
}
