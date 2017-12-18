package com.oc.rss.coach_nutrition_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static MySQLiteHelper instance;

    private static final String DB_NAME = "food.db";
    public static final String FOOD = "food";

    public static final String NAME = "Name";
    public static final String CALORIE = "Calorie";
    public static final String LIPIDS = "Lipids";
    public static final String CARBOHYDRATES = "Carbohydrates"; //Glucides
    public static final String PROTEIN = "Protein";

    private static int VERSION = 2;
    private static final String CREATE_ADRESSE =
            "create table " + FOOD + "( " +
                    "_id Integer not null primary key Autoincrement, " +
                    NAME + " String not null, " +
                    CALORIE + " int not null," +
                    LIPIDS + " int," +
                    CARBOHYDRATES + " int," +
                    PROTEIN + " int);";

    public MySQLiteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ADRESSE);
    }

    public void refreshDataBase (SQLiteDatabase db) {

            db.execSQL("drop table if exists " + FOOD);
            onCreate(db);
    }

    public void refreshDataBase () {

        SQLiteDatabase db = instance.getWritableDatabase();
        refreshDataBase (db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion)
            refreshDataBase (db);
    }

    public static MySQLiteHelper getInstance(Context context){

        if (instance == null)
            instance = new MySQLiteHelper(context);

        return instance;
    }
}
