package com.oc.rss.coach_nutrition_project;

import android.content.Intent;
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
    }

    public void buttonCancel(View v){
        finish();
    }

    public void buttonOk(View v){
        String min = ed_min.getText().toString();
        String max = ed_max.getText().toString();
        if(TextUtils.isEmpty(min) || TextUtils.isEmpty(max))
            Toast.makeText(this,"Please fill a max AND a min",Toast.LENGTH_LONG).show();
        else{
            Intent i = new Intent();
            i.putExtra("min",min);
            i.putExtra("max",max);
            setResult(1,i);
            Toast.makeText(this,"Settings saved",Toast.LENGTH_LONG).show();
            finish();
        }

    }

}
