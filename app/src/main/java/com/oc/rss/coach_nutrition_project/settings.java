package com.oc.rss.coach_nutrition_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class settings extends AppCompatActivity {

    EditText ed_min, ed_max;
    Button b_ok, b_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ed_min = (EditText) findViewById(R.id.ed_min);
        ed_max = (EditText) findViewById(R.id.ed_max);
        b_ok = (Button)findViewById(R.id.b_ok);
        b_cancel = (Button)findViewById(R.id.b_cancel);

        SharedPreferences pref = getSharedPreferences("OBJECTIVE", Context.MODE_PRIVATE);

        int min = pref.getInt ("min", getResources ().getInteger (R.integer.minCalorie));
        int max = pref.getInt ("max", getResources ().getInteger (R.integer.maxCalorie));

        ed_min.setText ("" + min);
        ed_max.setText ("" + max);
    }

    public void buttonCancel(View v){
        finish();
    }

    public void buttonOk(View v){

        String strMin = ed_min.getText().toString();
        String strMax = ed_max.getText().toString();

        if(TextUtils.isEmpty(strMin) || TextUtils.isEmpty(strMax)) {

            Toast.makeText(this, "Please fill a max AND a min", Toast.LENGTH_LONG).show();
            return;
        }

        int min = Integer.parseInt (strMin);
        int max = Integer.parseInt (strMax);

        if (max < min) {

            Toast.makeText(this, "Max must be greater than min", Toast.LENGTH_LONG).show();
            return;
        }

        // Store preferences
        SharedPreferences pref = getSharedPreferences("OBJECTIVE", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEdit=pref.edit();
        prefEdit.putInt("min", min);
        prefEdit.putInt("max", max);
        prefEdit.apply();

        CalorieManager.getInstance (this).minCalorie = min;
        CalorieManager.getInstance (this).maxCalorie = max;

        Intent i = new Intent();
        i.putExtra("min",strMin);
        i.putExtra("max",strMax);
        setResult(1,i);
        Toast.makeText(this,"Settings saved",Toast.LENGTH_LONG).show();
        finish();
    }

}
