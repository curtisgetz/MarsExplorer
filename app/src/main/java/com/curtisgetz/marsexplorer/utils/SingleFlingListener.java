package com.curtisgetz.marsexplorer.utils;

import android.util.Log;
import android.view.MotionEvent;

import com.github.chrisbanes.photoview.OnSingleFlingListener;

public class SingleFlingListener implements OnSingleFlingListener {

    static final String FLING_LOG_STRING = "Fling velocityX: %.2f, velocityY: %.2f";

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("PhotoView", String.format(FLING_LOG_STRING, velocityX, velocityY));
        return true;
    }
}
