package com.huawei.huaweitestapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.push.HmsMessaging;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView mTokenTv;
    private Button mGetTokenBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mTokenTv = findViewById(R.id.push_txt_token);
//        mGetTokenBtn = findViewById(R.id.get_txt_btn);
//        mGetTokenBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        HmsInstanceId inst = HmsInstanceId.getInstance(this);
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        getToken(inst);
        Log.d("Tag1",android_id);
    }


    private void getToken(final HmsInstanceId inst) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String HMS_APPID = AGConnectServicesConfig.fromContext(MainActivity.this).getString("client/app_id");
                    final String token = inst.getToken(HMS_APPID, "HCM");
                    String msg = getString(R.string.hms_token, token);
                    Log.d(TAG, msg);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTokenTv.setText("" + token);
                        }
                    });


                    Log.d("TAGToken", "get token" + token);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Log.d("Tag2","entering getToken");
    }

    public void subscribe(String topic) {
        try {
            HmsMessaging.getInstance(MainActivity.this).subscribe(topic).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "subscribe topic success", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "subscribe Complete");
                    } else {
                        Toast.makeText(MainActivity.this, "subscribe failed: ret = " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "subscribe failed: exception=" + e.getMessage());
        }
        Log.d("Tag3","entering subscribe");
    }

    public void unsubscribe (String topic) {
        try {
            HmsMessaging.getInstance(MainActivity.this).unsubscribe(topic).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "subscribe topic success", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "subscribe Complete");
                    } else {
                        Toast.makeText(MainActivity.this, "subscribe failed: ret = " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "subscribe failed: exception=" + e.getMessage());
        }
        Log.d("Tag4","entering unsubscribe");
    }
}