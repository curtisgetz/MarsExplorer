/*
 * Created by Curtis Getz on 11/6/18 8:54 PM
 * Last modified 11/6/18 8:54 PM
 */

package com.curtisgetz.marsexplorer.utils;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.curtisgetz.marsexplorer.R;

public class EnterAnimations {

    private final static String TAG = EnterAnimations.class.getSimpleName();


    public static void setEnterAnimation(Context context, int layoutOrientation, ViewGroup viewGroup) {
        Log.d(TAG + " ********", String.valueOf(layoutOrientation));
        int count = viewGroup.getChildCount();
        float offset = context.getResources().getDimensionPixelSize(R.dimen.offset_y);
        Interpolator interpolator =
                AnimationUtils.loadInterpolator(context, android.R.interpolator.linear_out_slow_in);

        if (layoutOrientation == LinearLayoutManager.HORIZONTAL) {
            animateFromSide(viewGroup, count, offset, interpolator);
        } else {
            animateFromBottom(viewGroup, count, offset, interpolator);
        }
    }

    private static void animateFromBottom(ViewGroup viewGroup, int count, float offset, Interpolator interpolator) {
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.setVisibility(View.VISIBLE);
            view.setTranslationY(offset);
            view.setAlpha(0.85f);

            view.animate()
                    .translationY(0f)
                    .alpha(1f)
                    .setInterpolator(interpolator)
                    .setDuration(1000L)
                    .start();
            offset *= 1.5f;
        }
    }

    private static void animateFromSide(ViewGroup viewGroup, int count, float offset, Interpolator interpolator) {
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            view.setVisibility(View.VISIBLE);
            view.setTranslationX(offset);
            view.setAlpha(0.85f);

            view.animate()
                    .translationX(0f)
                    .alpha(1f)
                    .setInterpolator(interpolator)
                    .setDuration(1000L)
                    .start();
            offset *= 1.5f;
        }
    }


}
