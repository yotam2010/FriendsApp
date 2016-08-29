package com.hadas.yotam.friendsapp;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

/**
 * Created by Yotam on 24/08/2016.
 */
public class FriendListFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<List<Friend>> {

    private static final String TAG_FRIEND_LIST_FRAGMENT=FriendListFragment.class.getName();
    private FriendsCostumeAdapter mFriendsCostumeAdapter;
    private static final int LOADER_ID=1;
    private ContentResolver mContentResolver;
    private List<Friend> mFriendList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        mContentResolver=getActivity().getContentResolver();
        mFriendsCostumeAdapter = new FriendsCostumeAdapter(getActivity(),getChildFragmentManager());
        setEmptyText("No friends");
        setListAdapter(mFriendsCostumeAdapter);
        setListShown(false);
        getLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @Override
    public Loader<List<Friend>> onCreateLoader(int id, Bundle args) {
        mContentResolver=getActivity().getContentResolver();
        return new FriendsListLoader(getActivity(), FriendsContract.URI_TABLE,mContentResolver);
    }

    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> data) {
        mFriendsCostumeAdapter.setData(data);
        mFriendList=data;
        if(isResumed())
            setListShown(true);
        else setListShownNoAnimation(true);
        setListShown(true);
    }

    @Override
    public void onLoaderReset(Loader<List<Friend>> loader) {
        mFriendsCostumeAdapter.setData(null);
    }
}
