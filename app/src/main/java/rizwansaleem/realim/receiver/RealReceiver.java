package rizwansaleem.realim.receiver;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONObject;

import java.util.List;

import rizwansaleem.realim.objects.ChatObject;

/**
 * Created by rizwansaleem on 11/09/15.
 */
public class RealReceiver extends ParsePushBroadcastReceiver {

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        if(isAppForground(context)) {
            try {
                JSONObject pushData = new JSONObject(intent.getStringExtra("com.parse.Data"));
                JSONObject myObject = pushData.getJSONObject("message");
                String name = (String) myObject.getString("name");
                String text = (String) myObject.getString("message");
                boolean isImage = (boolean) myObject.getBoolean("isImage");
                ChatObject object = new ChatObject(name, text, new byte[0], isImage);
            } catch (Exception e) {

            }
         } else {
            super.onPushReceive(context, intent);
         }
    }

    public boolean isAppForground(Context mContext) {

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
