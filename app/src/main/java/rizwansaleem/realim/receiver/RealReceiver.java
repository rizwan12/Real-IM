package rizwansaleem.realim.receiver;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by rizwansaleem on 11/09/15.
 */
public class RealReceiver extends ParsePushBroadcastReceiver {

    /**
     * Method overridden of class @ParsePushBroadcastReceiver to receive a Push Notification and handle it here.
     * @param context
     * @param intent
     */
    @Override
    protected void onPushReceive(Context context, Intent intent) {
        if(isAppForeground(context)) {
            try {
                super.onPushReceive(context, intent);
                JSONObject pushData = new JSONObject(intent.getStringExtra("com.parse.Data"));
                JSONObject myObject = pushData.getJSONObject("message");
                String name =  myObject.getString("name");
                String text =  myObject.getString("message");
                boolean isImage =  myObject.getBoolean("isImage");
                String imageUrl = "";
                if(isImage) {
                    imageUrl = myObject.getString("imageUrl");
                }
                Intent i = new Intent("broadCastName");
                // Data you need to pass to activity
                i.putExtra("name", name);
                i.putExtra("message", text);
                i.putExtra("isImage", isImage);
                if(isImage) {
                    i.putExtra("imageUrl", imageUrl);
                }

                context.sendBroadcast(i);
            } catch (Exception e) {

            }
         } else {
            super.onPushReceive(context, intent);
         }
    }

    /**
     * Method to check if the app is in foreground or not.
     * DISCLAIMER: WORKS WITH SOME ANDROID VERSIONS (OLD ONES)
     * @param mContext
     * @return
     */
    public boolean isAppForeground(Context mContext) {

        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mContext.getPackageName())) {
                return false;
            }
        }

        return true;
    }
}
