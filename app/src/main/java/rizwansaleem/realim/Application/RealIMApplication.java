package rizwansaleem.realim.Application;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import rizwansaleem.realim.utility.Constants;

/**
 * Created by rizwansaleem on 10/09/15.
 */
public class RealIMApplication extends Application {

    public static Context mContext;

    /*
     * Override onCreate Method of application which is called whenever the app ran.
     *
     */

    @Override
    public void onCreate() {
        super.onCreate();

        /*
         * Initialize Parse with Application ID and Client Key
         */
        Parse.initialize(this, Constants.APPLICATION_ID, Constants.CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground(Constants.CHANNEL_NAME);
        mContext = getApplicationContext();
    }
}
