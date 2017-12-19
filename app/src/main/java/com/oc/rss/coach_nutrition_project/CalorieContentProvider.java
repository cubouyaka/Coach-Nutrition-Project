package com.oc.rss.coach_nutrition_project;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class CalorieContentProvider extends ContentProvider {

    private static final String LOG = "CalorieContentProvider";
    private String authority = "paris7.appduturfu.calorieprovider";
    private MySQLiteHelper helper;

    public static final int FOOD = 1;
    public static final int ONE_FOOD = 2;

    private final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    {
        matcher.addURI(authority, "food", FOOD);
        matcher.addURI(authority, "food/#", ONE_FOOD);
    }

    public CalorieContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int code = matcher.match(uri);
        int i;
        long id;
        Log.d(LOG, "delete uri=" + uri.toString());
        switch (code) {
            case ONE_FOOD:
                id = ContentUris.parseId(uri);
                i = db.delete("Food", "Name=" + id, null);
                break;
            default:
                throw new UnsupportedOperationException("This delete not yet implemented");
        }
        return i;
    }

    @Override
    public String getType(Uri uri) {
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int code = matcher.match(uri);
        Log.d(LOG, "Uri=" + uri.toString());
        long id = 0;
        String path;
        switch (code) {
            case FOOD:
                id = db.insert("food", null, values);
                path = "food";
                break;
            default:
                throw new UnsupportedOperationException("this insert not yet implemented");
        }
        Uri.Builder builder = (new Uri.Builder())
                .authority(authority)
                .appendPath(path);

        return ContentUris.appendId(builder, id).build();

    }

    @Override
    public boolean onCreate() {

        helper = MySQLiteHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = helper.getReadableDatabase();
        int code = matcher.match(uri);
        Cursor cursor;

        switch (code) {
            case FOOD:

                cursor = db.query("food", projection, selection,
                        selectionArgs, null, null, sortOrder);

                Log.d ("DEBUG", "Column: " + cursor.getColumnCount() + cursor.getCount());

                break;
            default:
                Log.d("Uri provider =", uri.toString());
                throw new UnsupportedOperationException("this query is not yet implemented  " +
                        uri.toString());
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
