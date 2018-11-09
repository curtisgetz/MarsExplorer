package com.curtisgetz.marsexplorer.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.ui.explore.MarsExploreActivity;
import com.curtisgetz.marsexplorer.ui.explore_detail.ExploreDetailActivity;
import com.curtisgetz.marsexplorer.ui.main.MainActivity;
import com.curtisgetz.marsexplorer.utils.HelperUtils;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;


/**
 * Implementation of App Widget functionality.
 */
public class FactWidgetProvider extends AppWidgetProvider {

    private FirebaseJobDispatcher mJobDispatcher;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String fact) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.fact_widget);
        //set textview with fact text
        views.setTextViewText(R.id.widget_fact_body, fact);

        //Intents to allow up navigation
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent marsExploreIntent = new Intent(context, MarsExploreActivity.class);
        Intent intent = new Intent(context, ExploreDetailActivity.class);
        intent.putExtra(context.getString(R.string.explore_index_extra_key), HelperUtils.MARS_FACTS_CAT_INDEX);
        intent.putExtra(context.getString(R.string.parent_activity_tag_extra), MainActivity.class.getSimpleName());
        //create pending intent with getActivities to allow up navigation
        PendingIntent pendingIntent = PendingIntent.getActivities(context,
                0, new Intent[]{mainIntent, marsExploreIntent, intent}, PendingIntent.FLAG_UPDATE_CURRENT);
        //set click listener for intent
        views.setOnClickPendingIntent(R.id.widget_click_box, pendingIntent);
        //tell manager to update widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateAllWidgets(Context context, AppWidgetManager appWidgetManager,
                                        int[] appWidgetIds, String fact) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, fact);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //schedule job to get Fact. Schedule for NOW.
        mJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job factJob = mJobDispatcher.newJobBuilder()
                .setService(FactWidgetJobService.class)
                .setTag(FactWidgetJobService.class.getSimpleName())
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setRecurring(false)
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setReplaceCurrent(true)
                .setTrigger(Trigger.NOW)
                .build();
        mJobDispatcher.mustSchedule(factJob);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

