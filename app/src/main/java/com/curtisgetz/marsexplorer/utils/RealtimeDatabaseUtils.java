package com.curtisgetz.marsexplorer.utils;



import com.google.firebase.database.FirebaseDatabase;

public class RealtimeDatabaseUtils {
//https://stackoverflow.com/questions/37448186/setpersistenceenabledtrue-crashes-app
    private static FirebaseDatabase mDatabase;


    public static FirebaseDatabase getDatabase(){
        if(mDatabase == null){
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

}
