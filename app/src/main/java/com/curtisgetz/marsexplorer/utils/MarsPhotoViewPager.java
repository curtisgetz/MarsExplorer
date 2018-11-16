package com.curtisgetz.marsexplorer.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MarsPhotoViewPager extends ViewPager {

    public MarsPhotoViewPager(@NonNull Context context) {
        super(context);
    }

    public MarsPhotoViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        }catch (IllegalArgumentException e){
            return false;
        }
    }
}
