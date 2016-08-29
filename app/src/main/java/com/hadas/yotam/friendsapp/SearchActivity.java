package com.hadas.yotam.friendsapp;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Yotam on 28/08/2016.
 */
public class SearchActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<Friend>> {
    private List<Friend> mFriendList;
    private ContentResolver mContentResolver;
    private FriendsCostumeAdapter mFriendsCostumeAdapter;
    private FriendsDialog mFriendsDialog;
    private FriendsSearchListLoader mFriendsListLoader;
    private LoaderManager mLoaderManager;
    private FragmentManager mFragmentManager;
    private int ID_TAG=0;
    private ListView mListView;
    private EditText mEditText;
    private Button mButton;
    private String textFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        mListView=(ListView)findViewById(R.id.search_activity_list);
        mFriendsCostumeAdapter = new FriendsCostumeAdapter(getApplicationContext(),mFragmentManager);
        mListView.setAdapter(mFriendsCostumeAdapter);
        mContentResolver=getContentResolver();
        mFragmentManager=getSupportFragmentManager();
        mLoaderManager = getSupportLoaderManager();

        mEditText = (EditText)findViewById(R.id.search_activity_text);
        mButton=(Button)findViewById(R.id.search_activity_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textFilter=mEditText.getText().toString();
                mLoaderManager.initLoader(ID_TAG++,null,SearchActivity.this);
            }
        });
    }

    @Override
    public Loader<List<Friend>> onCreateLoader(int id, Bundle args) {
        Uri uri = FriendsContract.URI_TABLE;
        mFriendsListLoader=new FriendsSearchListLoader(getApplicationContext(),uri,mContentResolver,textFilter);
        return mFriendsListLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> data) {
        mFriendList=data;
        mFriendsCostumeAdapter.setData(mFriendList);
    }

    @Override
    public void onLoaderReset(Loader<List<Friend>> loader) {
    mFriendsCostumeAdapter.clear();
    }
}
