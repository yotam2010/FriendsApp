package com.hadas.yotam.friendsapp;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Yotam on 24/08/2016.
 */
public class FriendsDialog extends DialogFragment {
    private LayoutInflater mLayoutInflater;
    public static final String DIALOG_TYPE="command";
    public static final String DELETE_RECORD="deleteRecord";
    public static final String DELETE_DATABASE="deleteDatabase";
    public static final String CONFIRM_EXIT="confirm exit";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.v("tag","DIALOG - "+getArguments().getString(DIALOG_TYPE));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mLayoutInflater = getActivity().getLayoutInflater();
        final View view = mLayoutInflater.inflate(R.layout.friend_dialog,null);

        String command = getArguments().getString(DIALOG_TYPE);
        if(command.equals(DELETE_RECORD)){
            final int _id = getArguments().getInt(FriendsContract.FriendsColums.FRIENDS_ID);
            String name = getArguments().getString(FriendsContract.FriendsColums.FRIENDS_NAME);
            TextView popUpMessage = (TextView)view.findViewById(R.id.popup_message);
            popUpMessage.setText("Are you sure you want to delete "+name+" from your friends list?");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ContentResolver contentResolver = getActivity().getContentResolver();
                    Uri uri = FriendsContract.Friends.buildFrienUri(String.valueOf(_id));
                    contentResolver.delete(uri,null,null);
                    Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        else
        if(command.equals(DELETE_DATABASE)){

            TextView popUpMessage = (TextView)view.findViewById(R.id.popup_message);
            popUpMessage.setText("Are you sure you want to delete the entire database?");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ContentResolver contentResolver = getActivity().getContentResolver();
                    Uri uri = FriendsContract.URI_TABLE;
                    contentResolver.delete(uri,null,null);
                    Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }else if(command.equals(CONFIRM_EXIT)){
            TextView popUpMessage = (TextView)view.findViewById(R.id.popup_message);
            popUpMessage.setText("Are you sure you want to exit without saving?");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        Log.v("tag","DIALOG - "+getArguments().getString(DIALOG_TYPE)+" bug");
        return builder.create();
    }
}
