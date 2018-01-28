package com.bryanbeale.ulrichmetronome;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by bryanbeale on 1/28/18.
 */

public class MyNotification extends Notification
{

    private Context context;
    private NotificationManager mNotificationManager;

    public MyNotification(Context context){
        super();
        this.context = context;

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        CharSequence tickerText = "Shortcuts";

    }


}
