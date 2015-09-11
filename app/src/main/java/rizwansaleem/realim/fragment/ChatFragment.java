package rizwansaleem.realim.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import rizwansaleem.realim.R;
import rizwansaleem.realim.adapter.ChatViewAdapter;
import rizwansaleem.realim.network.NetworkHelper;
import rizwansaleem.realim.objects.ChatObject;
import rizwansaleem.realim.utility.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements View.OnClickListener{

    public static final String NICKNAME = "nickname";
    private View mainView;
    private Context mContext;
    private ListView chatListView;
    private ChatViewAdapter chatAdapter;
    private List<ChatObject> mChatList = new ArrayList<ChatObject>();
    private EditText mText;
    private Button sendButton;

    String name = "";


    public ChatFragment() {

    }

    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link #onAttach(Activity)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     *
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_chat, container, false);
        name = this.getArguments().getString(ChatFragment.NICKNAME);
        initUIComponents();
        return mainView;
    }

    /**
     * Called when a fragment is first attached to its activity.
     * {@link #onCreate(Bundle)} will be called after this.
     * <p>Deprecated. See {@link #onAttach(Context)}.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mContext = activity.getApplicationContext();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement");
        }
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to {@link Activity#onStart() Activity.onStart} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to {@link Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Called when the Fragment is no longer started.  This is generally
     * tied to {@link Activity#onStop() Activity.onStop} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_send:
                if(mText.getText().toString().length() > 0) {
                    ChatObject object = new ChatObject(name, mText.getText().toString(), new byte[0], false);
                    mText.setText("");
                    mChatList.add(object);
                    NetworkHelper.getInstance().saveChatObject(object);
                    chatAdapter.setChatList(mChatList);
                    chatAdapter.notifyDataSetChanged();
                    hideKeyboard();
                    sendPushNotification();
                } else {
                    Toast.makeText(mContext, "Please Enter SOmething", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Creating our own Options menu in the action bar
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.global, menu);
        showGlobalContextActionBar();
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Called when user selects something from the options menu in action bar.
     * @param  item
     * @return true/false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                // Called to pop current fragment from the fragment manager and display the home screen.
                BackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Showing Global action bar in the fragment with our own modifications.
     * Here we have to enable Back button.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setTitle(getArguments().getString("label", "Categories"));
//        actionBar.setTitle("Categories");
    }

    /**
     * Getting Action bar from the activity
     * @return Action bar instance. (android.support.v7)
     */
    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    /**
     * Helper function used to enable back button functionality.
     * Checks in the backstack of fragment.
     * If the count > 0 then it pops the last fragment.
     */
    private void BackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager.getBackStackEntryCount()>0) {
            fragmentManager.popBackStack();
        }
    }

    /**
     * Class Method
     * Initialise All UI Components
     */
    private void initUIComponents() {
        chatListView = (ListView) mainView.findViewById(R.id.chat_list);
        chatAdapter = new ChatViewAdapter(mContext, mChatList);
        chatListView.setAdapter(chatAdapter);
        mText = (EditText) mainView.findViewById(R.id.chat_text);
        sendButton = (Button) mainView.findViewById(R.id.chat_send);
        sendButton.setOnClickListener(this);
        retreiveChatList();
    }

    public void retreiveChatList() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ChatObject");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (list != null) {
                    if (list.size() > 0) {
                        mChatList = createChatList(list);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                chatAdapter.setChatList(mChatList);
                                chatAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                } else {
                    Log.d("", "Error: " + e.getMessage());
                }
            }
        });
    }

    private ArrayList<ChatObject> createChatList(List<ParseObject> list) {
        ArrayList<ChatObject> chatList = new ArrayList<ChatObject>();
        for(int count = 0; count < list.size(); count++) {
            ParseObject object = list.get(count);
            String chatName = (String) object.get(Constants.CHAT_NAME);
            String chatText = (String) object.get(Constants.CHAT_TEXT);
            byte[] chatImage = new byte[10];//(byte[]) object.get(Constants.CHAT_IMAGE);
            boolean isImage = (boolean) object.get(Constants.CHAT_IS_IMAGE);
            // --
            ChatObject chatObject = new ChatObject(chatName, chatText, chatImage, isImage);
            chatList.add(chatObject);
        }
        return chatList;
    }

    private void sendPushNotification() {
        ParsePush push = new ParsePush();
        push.setChannel(Constants.CHANNEL_NAME);
        push.setMessage("The Message just scored! It's now 2-2 against the Mets.");
        push.sendInBackground();
        Log.e("xxxxxxx","Push Notification Sent");
    }

    private void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

}
