package com.oc.rss.coach_nutrition_project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;

public class DrawView extends View {
    Paint paint = new Paint();

    public DrawView(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {

        int pos = 0;

        // Balance the display on the screen
        int width = canvas.getWidth ();
        int height = canvas.getHeight ();

        int minCalorie = CalorieManager.getInstance (null).minCalorie;
        int maxCalorie = CalorieManager.getInstance (null).maxCalorie;
        int calories[] = CalorieManager.getInstance (null).calorie;

        int size = Math.min (width, height) - 50;
        int range = size / 7;

        // Find the best scale to display the calories
        int max = maxCalorie * 5/4;
        for (int i = 0 ; i < calories.length ; i++)
            max = Math.max (max, calories[i] * 5/4);

        float scale = (float) size / (float) max;

        int top = (height - size) / 2;
        int left = (width - size) / 2;

        // Draw background grid
        paint.setStrokeWidth(1);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        int gridLeft = left + 2;
        paint.setPathEffect(new DashPathEffect(new float[]{5, 5, 5, 5}, 0));
        for (int i = 0 ; i < calories.length ; i++) {

            canvas.drawRect (gridLeft, top, gridLeft + range, top + size, paint);
            gridLeft += range;
        }
        paint.reset ();

        // draw maximum calories value
        paint.setStrokeWidth(4);
        int calorieLeft = left + 3;

        for (int i = 0 ; i < calories.length ; i++) {

            int value = calories[calories.length - i - 1];
            int calorieHeight = (int) ((float) value * scale);

            //Display amount of calories per day
            paint.setTextSize(40);
            paint.setColor(Color.YELLOW);
            canvas.drawText(""+value,calorieLeft + 5,top + size- calorieHeight -5,paint);

            if (value > maxCalorie)
                paint.setColor(Color.RED);
            else if (value < minCalorie)
                paint.setColor(Color.BLUE);
            else
                paint.setColor(Color.GREEN);
            paint.setAlpha (128);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect (calorieLeft, top + size - calorieHeight, calorieLeft + range, top + size, paint);

            calorieLeft += range;
        }

        Log.d ("DEBUG", "HISTORY");
        calorieLeft = left + 3;
        paint.setAlpha (255);
        for (int i = 0 ; i < calories.length ; i++) {

            int value = calories[calories.length - i - 1];
            int calorieHeight = (int) ((float) value * scale);

            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect (calorieLeft, top + size - calorieHeight, calorieLeft + range, top + size, paint);

            calorieLeft += range;
        }
        paint.reset ();

        // Draw minimum and maximum calorie baseline
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{15, 15, 15, 15}, 0));

        Path path = new Path ();
        pos = top + size - (int) ((float) minCalorie * scale);
        path.moveTo (left, pos);
        path.lineTo (left + size, pos);
        paint.setColor(Color.BLUE);
        canvas.drawPath (path, paint);

        path.reset ();
        pos = top + size - (int) ((float) maxCalorie * scale);
        path.moveTo (left, pos);
        path.lineTo (left + size, pos);
        paint.setColor(Color.RED);
        canvas.drawPath (path, paint);

        paint.reset ();

        // Draw background border
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(6);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect (left, top, left + size, top + size, paint);
    }
}
