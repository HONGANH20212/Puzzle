package com.example.touches;

import android.app.Activity;
import android.graphics.Color;
import android.text.DynamicLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class PuzzleView extends RelativeLayout {
    private TextView[] tvs;
    private RelativeLayout.LayoutParams[] params;
    private int[] colors;
    private int labelHeight;
    private int startY; // start y coordinate of TextView being moved
    private int startTouchY; // start y coordinate of current touch
    private int emptyPosition;
    private int[] positions;
    private int minFontSize; // Thêm biến minFontSize

    public PuzzleView(Activity activity, int width, int height, int numberOfPieces) {
        super(activity);
        buildGuiByCode(activity, width, height, numberOfPieces);
    }

    public void buildGuiByCode(Activity activity, int width, int height, int numberOfPieces) {
        positions = new int[numberOfPieces];
        tvs = new TextView[numberOfPieces];
        params = new RelativeLayout.LayoutParams[numberOfPieces]; // Khởi tạo mảng params

        colors = new int[tvs.length];
        Random random = new Random();
        labelHeight = height / numberOfPieces;

        for (int i = 0; i < tvs.length; i++) {
            tvs[i] = new TextView(activity);
            tvs[i].setGravity(Gravity.CENTER);
            colors[i] = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            tvs[i].setBackgroundColor(colors[i]);

            params[i] = new RelativeLayout.LayoutParams(width, labelHeight);
            params[i].leftMargin = 0;
            params[i].topMargin = labelHeight * i;

            addView(tvs[i], params[i]);
        }
    }





    public void enableListener(View.OnTouchListener listener) {
        for (int i = 0; i < tvs.length; i++) {
            tvs[i].setOnTouchListener(listener);
        }
    }
    public int indexOfTextView(int y) {
        int position = y / labelHeight;
        return positions[position];
    }
    public String getTextViewText(int tvIndex) {
        return tvs[tvIndex].getText().toString();
    }

    public void setTextViewText(int tvIndex, String s) {
        tvs[tvIndex].setText(s);
    }

    public void fillGui(String [] scrambledText) {
        minFontSize = DynamicSizing.MAX_FONT_SIZE; // Khởi tạo minFontSize
        for (int i = 0; i < tvs.length; i++) {
            tvs[i].setText(scrambledText[i]);
            positions[i] = i;

            tvs[i].setWidth(params[i].width);
            tvs[i].setPadding(20,5,20,5);

            //find font size
            int fontSize = DynamicSizing.setFontSizeToFitInView( tvs[i]);
            if(minFontSize > fontSize)
                minFontSize = fontSize;
        }
        for(int i = 0; i < tvs.length; i++)
            tvs[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, minFontSize);
    }
}
