package com.example.sdp_bfit.workout;

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

import static android.text.format.DateFormat.is24HourFormat;

public class TimePickerFragment extends DialogFragment{

    public static ListView list_item;

    public static ArrayList<String> arrayList;
    public static ArrayAdapter adapter;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);

        //return super.onCreateDialog(savedInstanceState);
        //< start with actual Time >
        Calendar cal=Calendar.getInstance();
        int intHour=cal.get(Calendar.HOUR);
        int intMinute=cal.get(Calendar.MINUTE);
        //</ start with actual Time >

        return new TimePickerDialog( getActivity(), new TimePickerDialog.OnTimeSetListener() {

    @Override
    public void onTimeSet(TimePicker view, int intHourOfDay, int intMinute) {
        WorkoutFragment wf=new WorkoutFragment();
        wf.bello(intHourOfDay,intMinute);
        
        final Intent intent = new Intent(getActivity(), MyService.class);
        getActivity().stopService(intent);

        Integer alarmHour = intHourOfDay;
        Integer alarmMinute = intMinute;
        intent.putExtra("alarmHour", alarmHour);
        intent.putExtra("alarmMinute", alarmMinute);
        getActivity().startService(intent);


    }
},intHour,intMinute,is24HourFormat(getActivity()));
    }}

