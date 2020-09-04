package com.huawei.huaweitestapp4;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

public class HmsMessagingService extends HmsMessageService {

    private static final String PUSH_TAG = "hms_Message";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Toast.makeText(this, "token" + s, Toast.LENGTH_LONG).show();
        Log.d(PUSH_TAG, "Message token: " + s);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("hms_token", s).apply();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().length() > 0) {
            Log.d(PUSH_TAG, "Message data payload: " + remoteMessage.getData());
            processCustomMessage(this, remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(PUSH_TAG, "Message Notification Body: " + remoteMessage.getNotification());
        }
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        Toast.makeText(this, "onMessageSent:" + s, Toast.LENGTH_LONG).show();
    }

    private void processCustomMessage(HmsMessagingService hmsMessagingService, String data) {
    }

    public static String getToken(Context context) {
        Log.d("Tagtoken","getToken()");
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("hms_token", "empty");
    }
}

