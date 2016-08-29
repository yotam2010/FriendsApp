package com.hadas.yotam.friendsapp;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yotam on 23/08/2016.
 */
public class FriendsSearchListLoader extends AsyncTaskLoader<List<Friend>> {
    private List<Friend> mFriendList;
    private static final String TAG_FRIENDS_LIADER = FriendsSearchListLoader.class.getName();
    private ContentResolver mContentResolver;
    private Cursor mCursor;
    private String mTextFilter;

    public FriendsSearchListLoader(Context context, Uri uri, ContentResolver contentResolver,String filter) {
        super(context);
        mContentResolver=contentResolver;
        mTextFilter=filter;
    }

    @Override
    public List<Friend> loadInBackground() {
        String selection = FriendsContract.FriendsColums.FRIENDS_NAME+" LIKE '"+mTextFilter+"%'";
        String[] projection = {BaseColumns._ID,
        FriendsContract.FriendsColums.FRIENDS_NAME,
                FriendsContract.FriendsColums.FRIENDS_PHONE,
                FriendsContract.FriendsColums.FRIENDS_EMAIL};
        List<Friend> enteries = new ArrayList<Friend>();
        mCursor=mContentResolver.query(FriendsContract.URI_TABLE,projection,selection,null,null);
        if(mCursor!=null){
            if(mCursor.moveToFirst()){
                do{
                    int _id =mCursor.getInt(mCursor.getColumnIndex(BaseColumns._ID));
                    String name = mCursor.getString(
                            mCursor.getColumnIndex(FriendsContract.FriendsColums.FRIENDS_NAME));
                    String email =mCursor.getString(
                            mCursor.getColumnIndex(FriendsContract.FriendsColums.FRIENDS_EMAIL));
                    String phone = mCursor.getString(
                            mCursor.getColumnIndex(FriendsContract.FriendsColums.FRIENDS_PHONE));
                    Friend friend = new Friend(_id,name,email,phone);
                    enteries.add(friend);
                }while (mCursor.moveToNext());
            }
        }
        return enteries;
    }

    @Override
    public void deliverResult(List<Friend> data) {
        if(isReset()){
            if(data!=null) {
                mCursor.close();

            }
        }
        List<Friend> oldFriendList = mFriendList;
        if(mFriendList==null||mFriendList.size()==0){
            Log.d(TAG_FRIENDS_LIADER,"+++++++++++++ NO DATA RETURNED");
        }
        mFriendList=data;
        if(isStarted())
            super.deliverResult(data);
        if(oldFriendList!=null && oldFriendList!=mFriendList)
            mCursor.close();
    }

    @Override
    protected void onStartLoading() {
        if(mFriendList!=null)
            deliverResult(mFriendList);
        if(takeContentChanged() || mFriendList==null)
            forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if(mCursor!=null)
            mCursor.close();
        mFriendList=null;

    }

    @Override
    public void onCanceled(List<Friend> data) {
        super.onCanceled(data);
        if(mCursor!=null)
            mCursor.close();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
