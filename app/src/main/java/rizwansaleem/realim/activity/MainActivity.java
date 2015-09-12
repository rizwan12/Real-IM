package rizwansaleem.realim.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseFile;

import rizwansaleem.realim.R;
import rizwansaleem.realim.fragment.ChatFragment;
import rizwansaleem.realim.fragment.MainActivityFragment;
import rizwansaleem.realim.objects.ChatObject;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.OnNickNameEnteredListener {

    /**
     * A broadcast receiver to receive message broadcast from RealReceiver.
     */
    BroadcastReceiver broadcastReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle b = intent.getExtras();

            String name = b.getString("name");
            String text = b.getString("message");
            boolean isImage = b.getBoolean("isImage");
            String imageUrl = "";
            if(isImage) {
                imageUrl = b.getString("imageUrl");
            }
            ParseFile file = (ParseFile) b.get("");
            ChatObject object = new ChatObject(name, text, file, imageUrl, isImage);

            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
            if (currentFragment instanceof ChatFragment) {
                ChatFragment fragment = (ChatFragment) currentFragment;
                fragment.addObjectAndUpdate(object);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        MainActivityFragment mainFragment = new MainActivityFragment();
        moveToNextFragment(mainFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter("broadCastName"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (currentFragment instanceof ChatFragment) {
            ChatFragment fragment = (ChatFragment) currentFragment;
            fragment.createAndShowAlertDialog();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNameEntered(String nickName) {
        Bundle args = new Bundle();
        args.putString(ChatFragment.NICKNAME, nickName);
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(args);
        moveToNextFragment(chatFragment);
    }

    private void moveToNextFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Add whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
