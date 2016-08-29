package com.hadas.yotam.friendsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Yotam on 23/08/2016.
 */
public class FriendsDatabase extends SQLiteOpenHelper {
    private static final String dbName="friends.db";
    private static final int dbVersion=1;
    private static final String DATABASE_TAG=FriendsDatabase.class.getName();
    private final Context mContext;

    interface Tabels{
        String FRIENDS="friends";
    }

    public FriendsDatabase(Context context) {
        super(context, dbName, null, dbVersion);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     final String executeString = "CREATE TABLE "+Tabels.FRIENDS+" ("+
            BaseColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            FriendsContract.Friends.FRIENDS_NAME+" TEXT NOT NULL,"+
            FriendsContract.Friends.FRIENDS_EMAIL+" TEXT NOT NULL,"+
            FriendsContract.Friends.FRIENDS_PHONE+" INTEGER NOT NULL);";
        db.execSQL(executeString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    int version = oldVersion;
        if(version==1){
            version=2;
        }
        if(version!=dbVersion){
            db.execSQL("DROP TABLE IF EXISTS "+Tabels.FRIENDS);
            onCreate(db);
        }
    }

    public static void delete(Context context){
        context.deleteDatabase(dbName);
    }

}
