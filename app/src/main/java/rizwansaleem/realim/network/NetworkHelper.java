package rizwansaleem.realim.network;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

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
        chat.put(Constants.CHAT_IMAGE, "");
        chat.put(Constants.CHAT_IS_IMAGE, object.isImage());
        chat.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.e(TAG, "Data Saved");
            }
        });
    }

    public ArrayList<ChatObject> retreiveChatList() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ChatObject");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (list != null) {
                    Log.e(TAG, "********************************* Size OF LIST : " + list.size());
                    if (list.size() > 0) {
                        mChatList = createChatList(list);
                        Log.e(TAG, "********************************* Size OF MCHATLIST : " + mChatList.size());
                    }

                } else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
        return mChatList;
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


}
