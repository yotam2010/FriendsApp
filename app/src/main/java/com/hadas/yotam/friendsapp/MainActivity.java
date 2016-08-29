package com.hadas.yotam.friendsapp;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.json.JSONObject;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager=getSupportFragmentManager();
        if(fragmentManager.findFragmentById(android.R.id.content)==null){
            FriendListFragment friendListFragment = new FriendListFragment();
            fragmentManager.beginTransaction().add(android.R.id.content,friendListFragment).commit();
        }

        //setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_option:
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
                break;
            case R.id.delete_option:
                FriendsDialog friendsDialog = new FriendsDialog();
                Bundle args = new Bundle();
                args.putString(FriendsDialog.DIALOG_TYPE,FriendsDialog.DELETE_DATABASE);
                friendsDialog.setArguments(args);
                friendsDialog.show(getSupportFragmentManager(),"delete-database");
                break;
            case R.id.search_option:
                Intent searchIntent = new Intent(this,SearchActivity.class);
                startActivity(searchIntent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
