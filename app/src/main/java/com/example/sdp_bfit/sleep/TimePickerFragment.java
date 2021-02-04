package com.example.sdp_bfit.sleep;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.sdp_bfit.R;
import com.example.sdp_bfit.workout.WorkoutFragment;

import static android.text.format.DateFormat.is24HourFormat;

public class TimePickerFragment extends DialogFragment{
    public static ListView list_item;

    public static ArrayList<String> arrayListSleep;
    public static ArrayAdapter adapterSleep;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        arrayListSleep = new ArrayList<String>();

        adapterSleep = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayListSleep);

        //return super.onCreateDialog(savedInstanceState);
        //< start with actual Time >
        Calendar cal=Calendar.getInstance();
        int intHour=cal.get(Calendar.HOUR);
        int intMinute=cal.get(Calendar.MINUTE);
        //</ start with actual Time >

        return new TimePickerDialog( getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SleepFragment sf=new SleepFragment();
                sf.addTime(hourOfDay,minute);

                final Intent intent = new Intent(getActivity(), MyService.class);
                getActivity().stopService(intent);

                Integer alarmHour = hourOfDay;
                Integer alarmMinute = minute;
                intent.putExtra("alarmHour", alarmHour);
                intent.putExtra("alarmMinute", alarmMinute);
                getActivity().startService(intent);


            }
        },intHour,intMinute,is24HourFormat(getActivity()));
    }}
