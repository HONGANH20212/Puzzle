package com.example.touches;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.content.res.Resources;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private int STATUS_BAR_HEIGHT = 24;
    private int ACTION_BAR_HEIGHT = 56;
    private PuzzleView puzzleView;
    private Puzzle puzzle;
    private int statusBarHeight;
    private int actionBarHeight;
    private GestureDetector detector;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        puzzle = new Puzzle();

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        int screenHeight = size.y;
        int puzzleWidth = size.x;

        Resources res = getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();

        float pixelDensity = metrics.density;
        TypedValue tv = new TypedValue();

        actionBarHeight = (int) (pixelDensity * ACTION_BAR_HEIGHT);

        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, metrics);

        statusBarHeight = (int) (pixelDensity * STATUS_BAR_HEIGHT);
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId != 0) // found resource for status bar height
            statusBarHeight = res.getDimensionPixelSize(resourceId);

        int puzzleHeight = screenHeight - statusBarHeight - actionBarHeight;
        puzzleView = new PuzzleView(this, puzzleWidth, puzzleHeight, puzzle.getNumberParts());

        String[] scrambled = puzzle.scramble();
        puzzleView.fillGui(scrambled);
        puzzleView.enableListener(this);

        setContentView(puzzleView);

        DoubleTapHandler dth = new DoubleTapHandler();
        detector = new GestureDetector(this, dth);
        detector.setOnDoubleTapListener(dth);
    }

    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }

    private class DoubleTapHandler extends GestureDetector.SimpleOnGestureListener {
        public boolean onDoubleTapEvent(MotionEvent event) {
            int touchy = (int) event.getRawY();
            int index = puzzleView.indexOfTextView(touchy - actionBarHeight - statusBarHeight);
            // Sửa từ `puzzle.wordToChange()` thành `puzzle.getRandomWord()`
            if (puzzleView.getTextViewText(index).equals(puzzle.getRandomWord()))
                puzzleView.setTextViewText(index, puzzle.replacementWord());
            return true;
        }
    }
}
