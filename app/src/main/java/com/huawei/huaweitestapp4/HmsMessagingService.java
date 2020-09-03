package com.huawei.huaweitestapp4;

import android.util.Log;
import android.widget.Toast;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

public class HmsMessagingService extends HmsMessageService {

    private static final String PUSH_TAG = "hms_Message";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Toast.makeText(this,"token" + s, Toast.LENGTH_LONG).show();
        Log.d(PUSH_TAG, "Message token: " + s);
        Log.d("Tag5","entering onNewToken");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData().length() > 0) {
            Log.d(PUSH_TAG, "Message data payload: " + remoteMessage.getData());
            processCustomMessage(this, remoteMessage.getData());
        }
        if(remoteMessage.getNotification() != null) {
            Log.d(PUSH_TAG, "Message Notification Body: " + remoteMessage.getNotification());
        }
        Log.d("Tag6","entering onMessageReceived");
    }

    @Override
    public void onMessageSent (String s) {
        super.onMessageSent(s);
        Toast.makeText(this,"onMessageSent:" + s, Toast.LENGTH_LONG).show();
        Log.d("Tag7","entering onMessageSent");
    }

    private void processCustomMessage(HmsMessagingService hmsMessagingService, String data) {
        Log.d("Tag8","entering processCustomMessage");
    }
}
