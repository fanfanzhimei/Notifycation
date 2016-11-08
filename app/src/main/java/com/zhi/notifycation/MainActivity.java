package com.zhi.notifycation;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText mEtOutline;
    private EditText mEtTitle;
    private EditText mEtContent;
    private Button mBtnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initEvents();
    }

    private void initEvents() {
        mBtnSend.setOnClickListener(this);
    }

    private void initViews() {
        mEtOutline = (EditText) findViewById(R.id.et_outline);
        mEtTitle = (EditText) findViewById(R.id.et_title);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mBtnSend = (Button) findViewById(R.id.btn_send);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                sendNotify();
                break;
        }
    }

    private void sendNotify() {
        Editable outlineEditable = mEtOutline.getText();
        Editable titleEditable = mEtTitle.getText();
        Editable contentEditable = mEtContent.getText();

        if (null == outlineEditable || "".equals(outlineEditable.toString().trim())) {
            Toast.makeText(MainActivity.this, R.string.str_outline_null, Toast.LENGTH_SHORT).show();
            return;
        }
        if (null == titleEditable || "".equals(titleEditable.toString().trim())) {
            Toast.makeText(MainActivity.this, R.string.str_title_null, Toast.LENGTH_SHORT).show();
            return;
        }
        if (null == contentEditable || "".equals(contentEditable.toString().trim())) {
            Toast.makeText(MainActivity.this, R.string.str_content_null, Toast.LENGTH_SHORT).show();
            return;
        }

        String outline = outlineEditable.toString();
        String title = titleEditable.toString();
        String content = contentEditable.toString();

        Notification.Builder notification = new Notification.Builder(MainActivity.this);
        notification.setTicker(outline);
        notification.setContentTitle(title);
        notification.setContentText(content);
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setDefaults(Notification.DEFAULT_SOUND);

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:18134567561"));
        Intent[] intents = new Intent[]{intent};
        PendingIntent pi = PendingIntent.getActivities(MainActivity.this, 10, intents, 0);
        notification.setContentIntent(pi);

        long[] vibrate = new long[]{2, 5, 2, 5};
        notification.setVibrate(vibrate);
        Notification notify = notification.getNotification();

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(100, notify);
    }
}
