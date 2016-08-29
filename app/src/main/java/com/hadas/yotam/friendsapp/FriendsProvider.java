package com.hadas.yotam.friendsapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Yotam on 23/08/2016.
 */
public class FriendsProvider extends ContentProvider {
    private FriendsDatabase mFriendsDatabase;
    private static String PROVIDER_TAG = FriendsProvider.class.getName();
    private static final UriMatcher mUriMatcher = buildUriMatcher();

    private static final int FRIENDS = 100;
    private static final int FRIENDS_ID=101;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FriendsContract.CONTENT_AUTHORTY;
        matcher.addURI(authority,"friends",FRIENDS);
        matcher.addURI(authority,"friends/*",FRIENDS_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mFriendsDatabase = new FriendsDatabase(getContext());
        return true;
    }

    private void deleteDatabase(){
        mFriendsDatabase.close();
        FriendsDatabase.delete(getContext());
        mFriendsDatabase = new FriendsDatabase(getContext());

    }
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mFriendsDatabase.getReadableDatabase();
        final int match = mUriMatcher.match(uri);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(FriendsDatabase.Tabels.FRIENDS);

        switch (match){
            case FRIENDS:

                break;
            case FRIENDS_ID:
                String id = FriendsContract.Friends.getFriendId(uri);
                queryBuilder.appendWhere(BaseColumns._ID+"="+id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: "+uri);
        }
        Cursor cursor = queryBuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = mUriMatcher.match(uri);
        switch (match){
            case FRIENDS:
                return FriendsContract.Friends.CONTENT_TYPE;
            case FRIENDS_ID:
                return FriendsContract.Friends.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: "+uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v(PROVIDER_TAG,"insert(uri="+uri+", values="+values.toString());
        final SQLiteDatabase db = mFriendsDatabase.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        switch (match){
            case FRIENDS:
                long recordId = db.insertOrThrow(FriendsDatabase.Tabels.FRIENDS,null,values);
                return FriendsContract.Friends.buildFrienUri(String.valueOf(recordId));
            default:
                throw new IllegalArgumentException("Unknown uri: "+uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.v(PROVIDER_TAG,"insert(uri="+uri);
        if(uri.equals(FriendsContract.BASE_CONTENT_URI)){
            deleteDatabase();
            return 0;
        }
        SQLiteDatabase db = mFriendsDatabase.getWritableDatabase();
        int match = mUriMatcher.match(uri);
        String selectionCirteria=selection;
        switch (match){
            case FRIENDS_ID:
                String id = FriendsContract.Friends.getFriendId(uri);
                 selectionCirteria = BaseColumns._ID+"="+id+(!TextUtils.isEmpty(selection)?" AND ("
                +selection+")":"");
                break;

            default:
                break;

        }
        return db.delete(FriendsDatabase.Tabels.FRIENDS,selectionCirteria,selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.v(PROVIDER_TAG,"update(uri="+uri+", values="+values.toString());
        final SQLiteDatabase db = mFriendsDatabase.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        String selectionCrieteria = selection;
        switch (match) {
            case FRIENDS_ID:
                String id = FriendsContract.Friends.getFriendId(uri);
                selectionCrieteria=BaseColumns._ID+"="+id+
                        (!TextUtils.isEmpty(selection)?" AND ("+selection+")":"");
                break;
                default:
                    throw new IllegalArgumentException("URI error: "+uri);
        }
        return db.update(FriendsDatabase.Tabels.FRIENDS,values,selectionCrieteria,selectionArgs);
    }
}
