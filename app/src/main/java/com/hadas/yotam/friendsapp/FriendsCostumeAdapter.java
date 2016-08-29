package com.hadas.yotam.friendsapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Yotam on 24/08/2016.
 */
public class FriendsCostumeAdapter extends ArrayAdapter<Friend> {
    private LayoutInflater mInflater;
    private static FragmentManager fragmentManager;

    public FriendsCostumeAdapter(Context context, FragmentManager tFragmentManager){
        super(context, android.R.layout.simple_list_item_2);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fragmentManager = tFragmentManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        if(convertView==null){
            view=mInflater.inflate(R.layout.custom_friend,parent,false);
        }else{
            view=convertView;
        }
        final Friend friend = getItem(position);
        final int _id = friend.getID();
        final String name = friend.getName();
        final String phone = friend.getPhone();
        final String email = friend.getEmail();
        ((TextView)view.findViewById(R.id.friend_name)).setText(name);
        ((TextView)view.findViewById(R.id.friend_email)).setText(email);
        ((TextView)view.findViewById(R.id.friend_phone)).setText(phone);

        Button editButton = (Button)view.findViewById(R.id.edit_btn);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),EditActivity.class);
                intent.putExtra(FriendsContract.FriendsColums.FRIENDS_ID,String.valueOf(_id));
                intent.putExtra(FriendsContract.FriendsColums.FRIENDS_NAME,String.valueOf(name));
                intent.putExtra(FriendsContract.FriendsColums.FRIENDS_EMAIL,String.valueOf(email));
                intent.putExtra(FriendsContract.FriendsColums.FRIENDS_PHONE,String.valueOf(phone));
                getContext().startActivity(intent);
            }
        });
        Button deleteButton = (Button)view.findViewById(R.id.delete_btn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendsDialog dialog = new FriendsDialog();
                Bundle args = new Bundle();
                args.putString(FriendsDialog.DIALOG_TYPE,FriendsDialog.DELETE_RECORD);
                args.putInt(FriendsContract.FriendsColums.FRIENDS_ID,_id);
                args.putString(FriendsContract.Friends.FRIENDS_NAME,name);
                dialog.setArguments(args);
                dialog.show(fragmentManager,"delete-record");
            }
        });
        return view;
    }
    public void setData(List<Friend> friends){
        clear();
        if(friends!=null){
            for(Friend friend:friends)
                add(friend);
        }
    }
}






























