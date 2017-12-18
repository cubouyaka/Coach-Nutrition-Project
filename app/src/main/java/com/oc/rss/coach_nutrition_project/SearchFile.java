package com.oc.rss.coach_nutrition_project;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import static com.oc.rss.coach_nutrition_project.R.id.text;

public class SearchFile extends AppCompatActivity {

    private FileDialog fileDialog;
    private File csvFile;
    private TextView path;
    private String authority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        authority = getResources().getString(R.string.authority);
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setContentView(R.layout.activity_search_land);
        else //if the screen is not in landscape orientation
            setContentView (R.layout.activity_search_file);
        path = (TextView) findViewById (R.id.pathFile);
    }

    // Load the csv File in the SQL base
    public void loadFile (View v) {

        if (csvFile == null) {

            Toast.makeText(this, "You need to select a file before", Toast.LENGTH_LONG).show();
            return;
        }

        //Read text from file
        StringBuilder text = new StringBuilder();

        try {

            BufferedReader br = new BufferedReader (new FileReader(csvFile));
            String line;

            while ((line = br.readLine()) != null) {

                // Load csv in database ([0] : Name, [1] : Calories)
                String datas[] = line.split (",");

                ContentValues values = new ContentValues();
                values.put("Name",   datas[0]);
                values.put("Calorie",datas[1]);


                ContentResolver resolver = getContentResolver();
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("content").authority(authority).appendPath("food");
                Uri uri = builder.build();
                resolver.insert(uri,values);

                text.append(line);
                text.append('\n');
            }

            br.close();

            Toast.makeText(this, "Database filled with new datas", Toast.LENGTH_LONG).show();

        } catch (IOException e) {}
    }

    // Open the file dialog to let the user select the .csv file
    public void searchFile (View v) {

        File mPath = new File(Environment.getExternalStorageDirectory() + "//DIR//");

        // Search a csv file in the dialog
        fileDialog = new FileDialog(this, mPath, ".csv");

        fileDialog.addFileListener(new FileDialog.FileSelectedListener() {

            public void fileSelected(File file) {

                csvFile = file;
                path.setText (file.toString ());
            }
        });

        fileDialog.showDialog();
    }
}
