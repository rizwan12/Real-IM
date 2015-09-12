package rizwansaleem.realim.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rizwansaleem.realim.R;
import rizwansaleem.realim.utility.Constants;

/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainActivityFragment.OnNickNameEnteredListener} interface
 * to handle interaction events.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener{

    private View mainView;
    private Dialog dialog;
    private Button mStartChatButton;
    private Button mDialogConfirmButton, mDialogCancelButton;
    private EditText mNameText;
    private Context mContext;
    private String mNameString;
    // -- Object of an interface to give a callback back to activity.
    OnNickNameEnteredListener mCallBack;

    /**
     * Default Constructor
     */
    public MainActivityFragment() {
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
        mainView = inflater.inflate(R.layout.fragment_main, container, false);
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
            mCallBack = (OnNickNameEnteredListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnNickNameEnteredListener");
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
    public void onDestroyView() {
        ViewGroup mContainer = (ViewGroup) mainView;
        ((ViewGroup) mainView).removeAllViews();
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.join_button:
                /**
                 * Open the dialog box to get username of the user and to join chat room
                 */
                openNickDialog();
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
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.MY_PREFERENCES, mContext.MODE_PRIVATE);
        String name = prefs.getString(Constants.USERNAME, null);
        if(name == null) {
            mStartChatButton = (Button) mainView.findViewById(R.id.join_button);
            mStartChatButton.setOnClickListener(this);
        } else {
            mCallBack.onNameEntered(name);
        }
    }

    /**
     * Open Custom Dialog to get the nick name of the user.
     */
    private void openNickDialog() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog);
        mNameText = (EditText) dialog.findViewById(R.id.name_text);
        mDialogConfirmButton = (Button) dialog.findViewById(R.id.dialog_confirm);
        mDialogCancelButton = (Button) dialog.findViewById(R.id.dialog_cancel);
        mDialogConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNameText.getText().toString().length() > 0) {
                    mNameString = mNameText.getText().toString();
                    saveInSharedPrefs();
                    moveToChatScreen();
                } else {
                    showToastMessage("Please Enter Name");
                }

            }
        });

        mDialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // Show the dialog
        dialog.show();
    }

    private void showToastMessage(String message) {
        if(message.length() > 0) {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }

    private void moveToChatScreen() {
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if(mCallBack != null) {
            mCallBack.onNameEntered(mNameString);
        }
    }

    private void saveInSharedPrefs() {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(Constants.MY_PREFERENCES, mContext.MODE_PRIVATE).edit();
        editor.putString(Constants.USERNAME, mNameString);
        editor.commit();
    }

    /**
     * Creating an interface to communicate with the activity and move to next fragment.
     * In Android ll Fragment-to-Fragment communication is done through the associated Activity.
     * Two Fragments should never communicate directly.
     */
    public interface OnNickNameEnteredListener {
        public void onNameEntered(String nickName);
    }
}
