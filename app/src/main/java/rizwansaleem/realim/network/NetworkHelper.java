package rizwansaleem.realim.network;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;

import rizwansaleem.realim.objects.ChatObject;
import rizwansaleem.realim.utility.Constants;

/**
 * Created by rizwansaleem on 11/09/15.
 */
public class NetworkHelper {

    private static final String TAG = "NetworkHelper";
    private static NetworkHelper instance = null;
    public ArrayList<ChatObject> mChatList;
    protected NetworkHelper() {

    }

    /**
     * Singleton method to get an instance of the Network helper class.
     * @return Instance of NetworkHelper
     */
    public static NetworkHelper getInstance() {
        if(instance == null) {
            // making Thread Safe
            synchronized (NetworkHelper.class) {
                if(instance == null){
                    instance = new NetworkHelper();
                }
            }
        }
        return instance;
    }

    public void saveChatObject(ChatObject object) {
        ParseObject chat = new ParseObject("ChatObject");
        chat.put(Constants.CHAT_NAME, object.getChatName());
        chat.put(Constants.CHAT_TEXT, object.getChatText());
        chat.put(Constants.CHAT_IMAGE, object.getImageData());
        chat.put(Constants.CHAT_URL, object.getImageUrl());
        chat.put(Constants.CHAT_IS_IMAGE, object.isImage());
        chat.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.e(TAG, "Data Saved");
            }
        });
    }

}
