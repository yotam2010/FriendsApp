package com.hadas.yotam.friendsapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Yotam on 26/08/2016.
 */
public class AddActivity extends FragmentActivity {
    private final String LOG_TAG=AddActivity.class.getSimpleName();
    private EditText mNameTextView, mEmailTextView,mPhoneTextVew;
    private Button mButton;
    private ContentResolver mContentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mNameTextView= (EditText) findViewById(R.id.friendsName);
        mEmailTextView= (EditText) findViewById(R.id.friendsEmail);
        mPhoneTextVew= (EditText) findViewById(R.id.frindsPhone);
        mButton = (Button)findViewById(R.id.saveButton);
        mContentResolver = this.getContentResolver();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    ContentValues values = new ContentValues();
                    values.put(FriendsContract.Friends.FRIENDS_NAME,mNameTextView.getText().toString());
                    values.put(FriendsContract.FriendsColums.FRIENDS_EMAIL,mEmailTextView.getText().toString());
                    values.put(FriendsContract.FriendsColums.FRIENDS_PHONE,mPhoneTextVew.getText().toString());
                    Uri returned = mContentResolver.insert(FriendsContract.URI_TABLE,values);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please ensure you have enterd valid data",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public Boolean isValid(){
        if(mNameTextView.getText().toString().length()==0||mEmailTextView.getText().toString().length()==0||
                mPhoneTextVew.getText().toString().length()==0)
            return false;

        return true;
    }
    private boolean someDataEntered(){
        if(mNameTextView.getText().toString().length()>0||mEmailTextView.getText().toString().length()>0||
                mPhoneTextVew.getText().toString().length()>0)
            return true;
        return false;
    }

    @Override
    public void onBackPressed() {
        if(someDataEntered()){
            FriendsDialog dialog = new FriendsDialog();
            Bundle args = new Bundle();
            args.putString(FriendsDialog.DIALOG_TYPE, FriendsDialog.CONFIRM_EXIT);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(),"confirm exit");
        }
        else
            super.onBackPressed();
    }
}
