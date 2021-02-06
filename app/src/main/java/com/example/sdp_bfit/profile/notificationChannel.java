package com.example.sdp_bfit.profile;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class notificationChannel extends Application {
    public static final String SLEEPCHANNEL_ID = "sleepnotificationchannel";
    public static final String WORKOUTCHANNEL_ID = "workoutnotificationchannel";
    public static final String PERIODCHANNEL_ID = "periodnotificationchannel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel sleepnotificationchannel = new NotificationChannel(
                    SLEEPCHANNEL_ID,
                    "Sleep Notification Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            sleepnotificationchannel.setDescription("This is Sleep Notification Channel");

            NotificationChannel workoutnotificationchannel = new NotificationChannel(
                    WORKOUTCHANNEL_ID,
                    "Workout Notification Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            workoutnotificationchannel.setDescription("This is Workout Notification Channel");

            NotificationChannel periodnotificationchannel = new NotificationChannel(
                    PERIODCHANNEL_ID,
                    "Period Notification Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            periodnotificationchannel.setDescription("This is Period Notification Channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(sleepnotificationchannel);
            manager.createNotificationChannel(workoutnotificationchannel);
            manager.createNotificationChannel(periodnotificationchannel);
        }
    }
}
