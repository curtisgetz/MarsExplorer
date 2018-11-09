package com.curtisgetz.marsexplorer.ui.widget;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.support.annotation.NonNull;

import com.curtisgetz.marsexplorer.data.MarsFact;
import com.curtisgetz.marsexplorer.utils.RealtimeDatabaseUtils;
import com.firebase.jobdispatcher.JobParameters;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.firebase.jobdispatcher.JobService;

import java.util.Calendar;



public class FactWidgetJobService extends JobService {

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        //get instance of Realtime Database
        FirebaseDatabase realtimeDatabase = RealtimeDatabaseUtils.getDatabase();
        //get reference to 'facts' node
        DatabaseReference factNode = realtimeDatabase.getReference("facts");
        //get current day
        String currentDay = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        //get child for the current day
        DatabaseReference factReference = factNode.child(currentDay);
        //extract Fact data through Listener
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() ) {
                    //extract Fact and send to widget.
                    setupFact(dataSnapshot.getValue(MarsFact.class));
                }
                // finish Job
                jobFinished(jobParameters, false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //finish Job if database error
                jobFinished(jobParameters, false);
            }
        };
        factReference.addListenerForSingleValueEvent(listener);
        //return true to allow work in background. Must finish job in 'onDataChanged' and 'onCancelled'
        return true;
    }

    private void setupFact(MarsFact fact) {
        if(fact == null) return;
        //get fact and update widgets with the shortDescription
        String factText =  fact.getShortDescription();
        ComponentName widget = new ComponentName(this, FactWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds =manager.getAppWidgetIds(widget);
        FactWidgetProvider.updateAllWidgets(this, manager, appWidgetIds, factText);
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}

