package com.example.sdp_bfit.sleep;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.example.sdp_bfit.Database;
import com.example.sdp_bfit.R;

public class SleepFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {

    private SleepViewModel SleepViewModel;
    private Button ButtonSleep;
    private Button ButtonWake;
    private TextView txtSleepHours;
    private String SleepDuration;
    private DateTimeFormatter format;

    public static ListView list_item_sleep;
    public static ArrayList<String> arrayListSleep;
    public static ArrayAdapter adapterSleep;
    private Button btn_add_alarm;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Date currentTime = Calendar.getInstance().getTime();
        //Date curr_date = new Date(System.currentTimeMillis("MAD_TIMEZONE"));
        //ZonedDateTime klDateTime = ldt.atZone(ZoneId.of("Asia/Kuala_Lumpur"));

        Date currentTime = Calendar.getInstance(TimeZone.getDefault()).getTime();


        SleepViewModel = new ViewModelProvider(this).get(SleepViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sleep, container, false);
        final TextView textView = root.findViewById(R.id.txt_sleep);
        ButtonSleep = root.findViewById(R.id.buttonSleepNow);
        ButtonWake = root.findViewById(R.id.buttonWakeNow);
        txtSleepHours = root.findViewById(R.id.txtSleepHours);
        SleepViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        ButtonSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(getContext());
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                String sleeptime = dateFormat.format(currentTime);
//                db.recordSleepTime(sleeptime);
                System.out.println(sleeptime);
                boolean success = db.recordSleepTime(sleeptime);
                if (success = true) {
                    Toast.makeText(container.getContext(), sleeptime, Toast.LENGTH_SHORT).show();
                    System.out.println("successful inserted sleep time");
                }
            }

        });


        ButtonWake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(getContext());
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm");
                String waketime = dateFormat.format(currentTime);
//                String waketime = currentTime.toString();
                System.out.println(waketime);
//                db.updateWakeTime(waketime);
                boolean success = db.updateWakeTime(waketime);
                if (success = true) {
                    Toast.makeText(container.getContext(), waketime, Toast.LENGTH_SHORT).show();
                    System.out.println("successful inserted wake time");
                }


                txtSleepHours.setText(db.displaySleepHours().toString());

                db.updateHourDiff (db.displaySleepHours().toString());

            }

        });






        //ListView
        list_item_sleep = root.findViewById(R.id.list_item_sleep);
        btn_add_alarm = root.findViewById(R.id.btn_add_alarm);

        arrayListSleep = new ArrayList<String>();

        adapterSleep = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayListSleep);
        list_item_sleep.setAdapter(adapterSleep);


        btn_add_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerDialog = new TimePickerFragment();
                timePickerDialog.show(getFragmentManager(), "time picker");
//                int count = arrayList.size()+1;
//
//                arrayList.add("Item: "+count);
//                adapter.notifyDataSetChanged();

            }
        });
        list_item_sleep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;

                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this Item")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrayListSleep.remove(which_item);
                                adapterSleep.notifyDataSetChanged();
                                final Intent intent = new Intent(getActivity(), MyService.class);
                                getActivity().stopService(intent);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });


        return root;


    }
    public void addTime( int hourOfDay, int minute){

        arrayListSleep.add(( hourOfDay + ":" + minute ));
        adapterSleep.notifyDataSetChanged();



    }

    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

        arrayListSleep.add(( hourOfDay + ":" + minute ));
        adapterSleep.notifyDataSetChanged();


    }

    @Override
    public void onResume() {
        super.onResume();
        Database db = new Database(getActivity());
        txtSleepHours.setText(db.displaySleepHours().toString());
    }
}

