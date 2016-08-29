package com.hadas.yotam.friendsapp;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Yotam on 23/08/2016.
 */
public class FriendsContract {

    interface FriendsColums{
        String FRIENDS_NAME="friends_name";
        String FRIENDS_EMAIL="friends_email";
        String FRIENDS_PHONE="friends_phone";
        String FRIENDS_ID="_id";
    }
    public static final String CONTENT_AUTHORTY="com.hadas.yotam.friendsapp.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORTY);

    private static final String PATH_FRIENDS ="friends";
    public static final Uri URI_TABLE=Uri.parse(BASE_CONTENT_URI.toString()+"/"+PATH_FRIENDS);

    public static final String[] TOP_LEVEL_PATHS={PATH_FRIENDS};

    public static class Friends implements  FriendsColums, BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_FRIENDS).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd"+CONTENT_AUTHORTY+".friends";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd"+CONTENT_AUTHORTY+".friends";

        public static Uri buildFrienUri(String friendId){
            return CONTENT_URI.buildUpon().appendEncodedPath(friendId).build();
        }
        public static String getFriendId(Uri uri){
            return uri.getPathSegments().get(1);
        }

    }

}
