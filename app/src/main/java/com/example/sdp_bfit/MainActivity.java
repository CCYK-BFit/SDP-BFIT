package com.example.sdp_bfit;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import static com.example.sdp_bfit.profile.notificationChannel.PERIODCHANNEL_ID;
import static com.example.sdp_bfit.profile.notificationChannel.SLEEPCHANNEL_ID;
import static com.example.sdp_bfit.profile.notificationChannel.WORKOUTCHANNEL_ID;


public class MainActivity extends AppCompatActivity {

    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;
    private double MagnitudePrevious = 0;
    public static Integer stepCount = 0;
    private NotificationManagerCompat notificatonManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_workout, R.id.navigation_calories, R.id.navigation_dashboard, R.id.navigation_sleep, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        requestPermission();
        //TO-DO : Get intent from camera activity
        Intent intent = getIntent();
        View view = getLayoutInflater().inflate(R.layout.fragment_calories_main_unused, null);
        String calories = intent.getStringExtra("calories");
        TextView textView = view.findViewById(R.id.calories);
        textView.setText("hi");

        //stepcounter
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent != null) {
                    float x_acceleration = sensorEvent.values[0];
                    float y_acceleration = sensorEvent.values[1];
                    float z_acceleration = sensorEvent.values[2];

                    double Magnitude = Math.sqrt(x_acceleration * x_acceleration + y_acceleration * y_acceleration + z_acceleration * z_acceleration);
                    double MagnitudeDelta = Magnitude - MagnitudePrevious;
                    MagnitudePrevious = Magnitude;

                    if (MagnitudeDelta > 6) {
                        stepCount++;

                    }


                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        //Notification
        notificatonManager = NotificationManagerCompat.from(this);
    }


    public boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(
                this, CAMERA_PERMISSION, CAMERA_REQUEST_CODE);
    }

    public void sendOnSleepChannel(View v) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification sleepNotification = new NotificationCompat.Builder(this, SLEEPCHANNEL_ID)
                .setSmallIcon(R.drawable.ic_icon_sleep)
                .setContentTitle("GOOD NIGHT")
                .setContentText("Your bedtime is starting soon.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setSound(alarmSound)
                .build();
        notificatonManager.notify(1, sleepNotification);
    }

    public void sendOnWorkoutChannel(View v) {
        Notification workoutNotification = new NotificationCompat.Builder(this, WORKOUTCHANNEL_ID)
                .setSmallIcon(R.drawable.ic_workout)
                .setContentTitle("WORKOUT REMINDER")
                .setContentText("Make your body the sexiest outfit you own")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificatonManager.notify(2, workoutNotification);
    }

    public void sendOnPeriodChannel(View v) {
        Notification periodNotification = new NotificationCompat.Builder(this, PERIODCHANNEL_ID)
                .setSmallIcon(R.drawable.ic_period)
                .setContentTitle("PERIOD TRACKER")
                .setContentText("Period is coming in 2 days.")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificatonManager.notify(3, periodNotification);
    }
}
